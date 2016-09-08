package com.intellij.lang.cloudslang.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.cloudslang.configuration.SpringConfiguration;
import com.intellij.openapi.fileChooser.FileSystemTree;
import com.intellij.openapi.vfs.VirtualFileCopyEvent;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileMoveEvent;
import com.intellij.openapi.vfs.VirtualFilePropertyEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.file.impl.FileManagerImpl;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.result.ModellingResult;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.model.ParsedSlang;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Ligia Centea
 * Date: 9/7/2016.
 */

public class SimpleExternalAnnotator extends ExternalAnnotator<ModellingResult, List<RuntimeException>> {

    @Nullable
    @Override
    public ModellingResult collectInformation(@NotNull PsiFile file) {
//        new SlangCompilerSpringConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
//        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/ideaPlugin.xml");
        YamlParser yamlParser = ac.getBean(YamlParser.class);
        SlangModeller slangModeller = ac.getBean(SlangModeller.class);

        if (file instanceof YAMLFile) {
            YAMLFile yamlFile = (YAMLFile) file;

            SlangSource slangSource = new SlangSource(yamlFile.getText(), yamlFile.getName());
            ParsedSlang parsedSlang = yamlParser.parse(slangSource);
            return slangModeller.createModel(parsedSlang);
        }
        return null;
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
                createErrorAnnotations(found, holder, annotationResult);
            }
        }
    }

    private void createErrorAnnotations(PsiElement element, AnnotationHolder holder, List<RuntimeException> annotationResult) {
        annotationResult.forEach(r -> holder.createErrorAnnotation(element, r.getMessage()));
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
