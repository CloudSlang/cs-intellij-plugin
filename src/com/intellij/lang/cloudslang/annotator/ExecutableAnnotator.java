package com.intellij.lang.cloudslang.annotator;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.problems.ProblemImpl;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.cloudslang.exceptions.LocatedRuntimeException;
import com.intellij.openapi.editor.Document;
import com.intellij.problems.Problem;
import com.intellij.problems.WolfTheProblemSolver;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import io.cloudslang.lang.compiler.SlangCompiler;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.result.ExecutableModellingResult;
import io.cloudslang.lang.compiler.modeller.result.ModellingResult;
import io.cloudslang.lang.compiler.modeller.result.ParseModellingResult;
import io.cloudslang.lang.compiler.modeller.result.SystemPropertyModellingResult;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.model.ParsedSlang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.intellij.lang.cloudslang.CloudSlangFileUtils.isCloudSlangFile;
import static com.intellij.lang.cloudslang.CloudSlangFileUtils.isCloudSlangSystemPropertiesFile;
import static com.intellij.lang.cloudslang.dependencies.CloudSlangDependenciesProvider.getSlangModeller;
import static com.intellij.lang.cloudslang.dependencies.CloudSlangDependenciesProvider.getYamlParser;
import static com.intellij.lang.cloudslang.dependencies.CloudSlangDependenciesProvider.slangCompiler;
import static java.util.stream.Collectors.toList;


public class ExecutableAnnotator extends ExternalAnnotator<ModellingResult, List<RuntimeException>> {

    private static Pattern linePattern = Pattern.compile("line (\\d+), column \\d+");
    private static final String MESSAGE_DELIMITER_STRING = "(?=in \'.*\', line (\\d+), column \\d+)";

    @Nullable
    @Override
    public ModellingResult collectInformation(@NotNull PsiFile file) {
        if (isCloudSlangSystemPropertiesFile(file.getName())) {
            SlangCompiler slangCompiler = slangCompiler();

            YAMLFile yamlFile = (YAMLFile) file;
            SlangSource slangSource = new SlangSource(yamlFile.getText(), yamlFile.getName());
            SystemPropertyModellingResult modellingResult = slangCompiler.loadSystemPropertiesFromSource(slangSource);
            if (!modellingResult.getErrors().isEmpty()) {
                List<RuntimeException> runtimeExceptions = modellingResult.getErrors()
                        .stream()
                        .map(this::transformMessageToExceptionList)
                        .flatMap(List::stream)
                        .collect(toList());
                return new ExecutableModellingResult(null, runtimeExceptions);
            }

            return null;

        } else if (isCloudSlangFile(file)) {
            YamlParser yamlParser = getYamlParser();
            SlangModeller slangModeller = getSlangModeller();
            YAMLFile yamlFile = (YAMLFile) file;

            SlangSource slangSource = new SlangSource(yamlFile.getText(), yamlFile.getName());
            try {
                ParsedSlang parsedSlang = yamlParser.parse(slangSource);
                ParseModellingResult parseModellingResult = yamlParser.validate(parsedSlang);
                return slangModeller.createModel(parseModellingResult);
            } catch (RuntimeException e) {
                return processExceptionToModellingResult(e);
            }
        }
        return null;
    }

    @NotNull
    private ModellingResult processExceptionToModellingResult(RuntimeException e) {
        final List<RuntimeException> runtimeExceptions = transformMessageToExceptionList(e);
        return new ExecutableModellingResult(null, runtimeExceptions);
    }

    @NotNull
    private List<RuntimeException> transformMessageToExceptionList(RuntimeException e) {
        String message = e.getMessage();
        if (message == null) {
            return Collections.emptyList();
        }
        String[] errorMessages = message.split(MESSAGE_DELIMITER_STRING);
        List<RuntimeException> runtimeExceptions = new ArrayList<>();
        for (String errorMsg : errorMessages) {
            Matcher matcher = linePattern.matcher(errorMsg);
            //try to extract the line number from stack trace
            while (matcher.find()) {
                try {
                    runtimeExceptions.add(new LocatedRuntimeException(errorMsg, Integer.parseInt(matcher.group(1))));
                } catch (Exception ignore) { // We don't want to fail if we parse a weird number or group is not an integer because regex changed
                }
            }
        }
        //if no line number could be found, simply add the exception
        if (runtimeExceptions.isEmpty()) {
            runtimeExceptions.add(e);
        }
        return runtimeExceptions;
    }

    @Nullable
    @Override
    public List<RuntimeException> doAnnotate(ModellingResult collectedInfo) {
        return collectedInfo.getErrors();
    }

    @Override
    public void apply(@NotNull PsiFile file, List<RuntimeException> annotationResult, @NotNull AnnotationHolder holder) {
        if (file instanceof YAMLFile) {
            YAMLFile yamlFile = (YAMLFile) file;
            if (!yamlFile.getDocuments().isEmpty()) {
                YAMLDocument yamlDocument = yamlFile.getDocuments().get(0);
                PsiElement found = findChildRecursively(yamlDocument, new String[]{"flow", "operation", "decision"});
                if (found instanceof YAMLKeyValue) {
                    YAMLKeyValue keyValue = (YAMLKeyValue) found;
                    found = keyValue.getKey();
                } else {
                    found = yamlDocument;
                }
                createErrorAnnotations(found, yamlFile, holder, annotationResult);
            }
            if (!annotationResult.isEmpty()) {
                HighlightInfo highlightInfo = HighlightInfo.newHighlightInfo(HighlightInfoType.ERROR)
                        .descriptionAndTooltip("Found " + annotationResult.size() + " errors")
                        .range(file).create();
                if (highlightInfo != null) {
                    Problem problem = new ProblemImpl(file.getVirtualFile(), highlightInfo, true);
                    WolfTheProblemSolver theProblemSolver = WolfTheProblemSolver.getInstance(file.getProject());
                    theProblemSolver.reportProblems(file.getVirtualFile(), Collections.singletonList(problem));
                }
            }
        }
    }

    private void createErrorAnnotations(PsiElement element, PsiFile file, AnnotationHolder holder, List<RuntimeException> annotationResult) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        if (document == null) {
            return;
        }
        PsiElement psiElementWithError = element;
        for (RuntimeException exception : annotationResult) {
            if (exception instanceof LocatedRuntimeException) {
                LocatedRuntimeException locatedException = (LocatedRuntimeException) exception;
                PsiElement childAtLine = file.findElementAt(document.getLineStartOffset(locatedException.getLineNumber() - 1));
                if (childAtLine != null) {
                    psiElementWithError = childAtLine;
                }
            }
            holder.createErrorAnnotation(psiElementWithError, exception.getMessage());
        }
    }

    private YAMLPsiElement findChildRecursively(YAMLPsiElement element, String[] possibleName) {
        List<YAMLPsiElement> yamlElements = element.getYAMLElements();
        if (yamlElements.isEmpty()) {
            return null;
        }
        Optional<YAMLPsiElement> matchingNode = yamlElements.stream().filter(e -> hasAcceptedName(e, possibleName)).findFirst();
        if (matchingNode.isPresent()) {
            return matchingNode.get();
        } else {
            return yamlElements.stream()
                    .map(e -> findChildRecursively(e, possibleName))
                    .filter(e -> e != null)
                    .findFirst()
                    .orElse(null);
        }
    }

    private boolean hasAcceptedName(YAMLPsiElement e, String[] possibleName) {
        return Stream.of(possibleName).anyMatch(n -> n.equals(e.getName()));
    }

}
