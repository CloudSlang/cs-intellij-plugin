/*******************************************************************************
 * (c) Copyright 2016 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.intellij.lang.annotator;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.problems.ProblemImpl;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.editor.Document;
import com.intellij.problems.Problem;
import com.intellij.problems.WolfTheProblemSolver;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import io.cloudslang.intellij.lang.dependencies.CloudSlangDependenciesProvider;
import io.cloudslang.intellij.lang.exceptions.LocatedRuntimeException;
import io.cloudslang.lang.compiler.MetadataExtractor;
import io.cloudslang.lang.compiler.SlangCompiler;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.result.ExecutableModellingResult;
import io.cloudslang.lang.compiler.modeller.result.MetadataModellingResult;
import io.cloudslang.lang.compiler.modeller.result.ModellingResult;
import io.cloudslang.lang.compiler.modeller.result.ParseModellingResult;
import io.cloudslang.lang.compiler.modeller.result.SystemPropertyModellingResult;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.model.ParsedSlang;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.YAMLValue;
import org.jetbrains.yaml.psi.impl.YAMLBlockMappingImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.cloudslang.intellij.lang.CloudSlangFileUtils.isCloudSlangFile;
import static io.cloudslang.intellij.lang.CloudSlangFileUtils.isCloudSlangSystemPropertiesFile;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;


public class ExecutableAnnotator extends ExternalAnnotator<ModellingResult, List<RuntimeException>> {



    private static final String MESSAGE_DELIMITER_STRING = "(?=in \'.*\', line (\\d+), column \\d+)";
    private static final String INPUT_KEY = "@input";
    private static final String PRIVATE = "private";
    private static Pattern linePattern = compile("line (\\d+), column \\d+");
    private static final Pattern keyInListPattern = compile("\\s*-\\s+([\\w]+):?.*");
    private static final String[] keysForDocumentation = new String[]{"inputs", "outputs", "results"};
    private static final String[] keysForDescription = new String[]{"@input", "@output", "@result"};
    private static final String MISSING_DOCUMENTATION_FOR_NAME_PATTERN = "Missing description for '%s'";

    private CloudSlangDependenciesProvider provider = new CloudSlangDependenciesProvider();

    @Nullable
    @Override
    public ModellingResult collectInformation(@NotNull PsiFile file) {
        MetadataExtractor metadataExtractor = provider.metadataExtractor();
        if (isCloudSlangSystemPropertiesFile(file.getName())) {
            SlangCompiler slangCompiler = provider.slangCompiler();

            YAMLFile yamlFile = (YAMLFile) file;
            SlangSource slangSource = new SlangSource(yamlFile.getText(), yamlFile.getName());
            MetadataModellingResult metadataModellingResult = metadataExtractor.extractMetadataModellingResult(slangSource, true);
            SystemPropertyModellingResult modellingResult = slangCompiler.loadSystemPropertiesFromSource(slangSource);
            List<RuntimeException> runtimeExceptions = mergeModellingResults(metadataModellingResult, modellingResult);
            return new ExecutableModellingResult(null, runtimeExceptions);
        } else if (isCloudSlangFile(file)) {
            YamlParser yamlParser = provider.yamlParser();
            SlangModeller slangModeller = provider.slangModeller();
            YAMLFile yamlFile = (YAMLFile) file;

            Path yamlFilePath = Paths.get(yamlFile.getVirtualFile().getPath()).toAbsolutePath();
            SlangSource slangSource = SlangSource.fromFile(yamlFilePath.toFile());
//            SlangSource slangSource = new SlangSource(yamlFile.getText(), yamlFile.getName());
            try {
                MetadataModellingResult metadataModellingResult = metadataExtractor.extractMetadataModellingResult(slangSource, true);
                ParsedSlang parsedSlang = yamlParser.parse(slangSource);
                ParseModellingResult parseModellingResult = yamlParser.validate(parsedSlang);
                ExecutableModellingResult modellingResult = slangModeller.createModel(parseModellingResult);
                List<RuntimeException> runtimeExceptions = mergeModellingResults(metadataModellingResult, modellingResult);
                return new ExecutableModellingResult(null, runtimeExceptions);
            } catch (RuntimeException e) {
                return processExceptionToModellingResult(e);
            }
        }
        return null;
    }

    private List<RuntimeException> mergeModellingResults(ModellingResult result1, ModellingResult result2) {
        List<RuntimeException> runtimeExceptions = new ArrayList<>();

        runtimeExceptions.addAll(getExceptionsFromResult(result2));
        runtimeExceptions.addAll(getExceptionsFromResult(result1));

        return runtimeExceptions;
    }

    private List<RuntimeException> getExceptionsFromResult(ModellingResult result1) {
        return result1.getErrors()
                .stream()
                .map(this::transformMessageToExceptionList)
                .flatMap(List::stream)
                .collect(toList());
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
                createWarningsForMissingElementsInDescription(yamlDocument, yamlFile, holder);
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

    private void createWarningsForMissingElementsInDescription(YAMLDocument yamlDocument, YAMLFile yamlFile, AnnotationHolder holder) {
        List<PsiElement> commentsList = Arrays.stream(yamlFile.getChildren()).filter(e -> e instanceof PsiComment).collect(toList());
        for (int index = 0; index < keysForDocumentation.length; index++) {
            createWarningsIfNecessary(keysForDescription[index], commentsList, holder, getElementNamePairs(yamlDocument, keysForDocumentation[index]));
        }
    }

    private void createWarningsIfNecessary(final String keyForLookup, final List<PsiElement> commentList, AnnotationHolder holder, final List<Pair<PsiElement, String>> pairElementNameList) {
        for (Pair<PsiElement, String> pairElementName : pairElementNameList) {
            final Optional isPresentInDescription = commentList.stream().filter(e -> containsDescriptionForElement(keyForLookup, e.getText(), pairElementName.getRight())).findAny();
            boolean isPrivate = isPrivateInput(pairElementName.getLeft(), keyForLookup);
            if (!isPresentInDescription.isPresent() && !isPrivate) {
                holder.createWeakWarningAnnotation(pairElementName.getLeft(), format(MISSING_DOCUMENTATION_FOR_NAME_PATTERN, pairElementName.getRight()));
            }
        }
    }

    private boolean isPrivateInput(PsiElement element, String keyForLookup) {
        if (element instanceof YAMLKeyValue && keyForLookup.equals(INPUT_KEY)) {
            YAMLValue value = ((YAMLKeyValue) element).getValue();
            if (value instanceof YAMLBlockMappingImpl) {
                return isPrivatePropertyTrue(value);
            }
        }
        return false;
    }

    private boolean isPrivatePropertyTrue(YAMLValue value) {
        List<YAMLPsiElement> yamlElements = value.getYAMLElements();
        for (YAMLPsiElement e : yamlElements) {
            if (e instanceof YAMLKeyValue) {
                YAMLKeyValue property = (YAMLKeyValue) e;
                if (property.getKeyText().equals(PRIVATE) && property.getValueText().equals(TRUE.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsDescriptionForElement(final String keyForLookup, final String comment, final String name) {
        if (StringUtils.isEmpty(comment)) {
            return false;
        }
        Pattern pattern = compile("\\s*#!\\s*" + keyForLookup + "\\s*" + name + "\\s*:.+");
        return pattern.matcher(comment.trim()).matches();
    }

    private List<Pair<PsiElement, String>> getElementNamePairs(YAMLDocument yamlDocument, String elementName) {
        final PsiElement psiElement = findChildRecursively(yamlDocument, new String[]{elementName});
        if (psiElement == null) {
            return Collections.emptyList();
        }

        List<Pair<PsiElement, String>> elementStringPairs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(psiElement.getText()))) {
            for (String line; (line = reader.readLine()) != null;) {
                final Matcher matcher = keyInListPattern.matcher(line);
                if (matcher.find()) {
                    String elementNameGroup = matcher.group(1);
                    YAMLPsiElement childElement = findChildRecursively((YAMLPsiElement) psiElement, new String[]{elementNameGroup});
                    PsiElement elementToHighlight = (childElement != null) ? childElement : ((psiElement instanceof YAMLKeyValue) ? ((YAMLKeyValue) psiElement).getKey() : psiElement);
                    elementStringPairs.add(new ImmutablePair<>(elementToHighlight, elementNameGroup));
                }
            }
        } catch (IOException ignore) {  // this code is never reached because the reader reads from memory
        }
        return elementStringPairs;
    }

}
