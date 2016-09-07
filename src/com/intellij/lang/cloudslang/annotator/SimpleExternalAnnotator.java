package com.intellij.lang.cloudslang.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.cloudslang.configuration.SlangCompilerSpringConfigIntelliJ;
import com.intellij.lang.cloudslang.configuration.SpringConfiguration;
import com.intellij.psi.PsiFile;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.utils.SpringModelSearchers;
import io.cloudslang.lang.compiler.SlangCompiler;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.lang.compiler.configuration.SlangCompilerSpringConfig;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.model.Executable;
import io.cloudslang.lang.compiler.modeller.result.ExecutableModellingResult;
import io.cloudslang.lang.compiler.modeller.result.ModellingResult;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.model.ParsedSlang;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
                YAMLPsiElement found = findChildRecursively(yamlDocument, new String[]{"flow", "operation", "decision"});
                if (found == null) {
                    found = yamlDocument;
                }
                createErrorAnnotations(found, holder, annotationResult);
            }
//            holder.createErrorAnnotation(file, "File contains " + annotationResult.size() + " errors");
        }
    }

    private void createErrorAnnotations(YAMLPsiElement element, AnnotationHolder holder, List<RuntimeException> annotationResult) {
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
