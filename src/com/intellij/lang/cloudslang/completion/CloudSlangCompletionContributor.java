package com.intellij.lang.cloudslang.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import io.cloudslang.lang.compiler.SlangTextualKeys;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CloudSlangCompletionContributor extends CompletionContributor {

    private static final String TEMPLATES = "com/intellij/lang/cloudslang/templates";
    private static final String WINDOWS_LINE_ENDINGS = "\r\n";
    private static final String UNIX_LINE_ENDINGS = "\n";
    private static final String SLASH = "/";

    public CloudSlangCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(YAMLLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,  ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                        addSlangKeywords(resultSet);
                        try {
                            addTemplates(resultSet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        addYamlKeywordsUsedByCloudSlang(resultSet);
                    }
                }
        );
    }

    private void addSlangKeywords(CompletionResultSet resultSet) {
        for (Field field : SlangTextualKeys.class.getFields()) {
            try {
                String value = field.get(null).toString();
                LookupElementBuilder builder = LookupElementBuilder.create(value + ":")
                        .withPresentableText(value);
                resultSet.addElement(builder);
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    private void addTemplates(CompletionResultSet resultSet) throws IOException, URISyntaxException {
        ClassLoader classLoader = CloudSlangCompletionContributor.class.getClassLoader();
        List<String> allTemplateNames = CloudSlangCompletionTemplates.getAllTemplateNames();
        for (String templateName : allTemplateNames) {
            InputStream templateResourceStream = classLoader.getResourceAsStream(TEMPLATES + SLASH + templateName);
            String templateString = IOUtils.toString(templateResourceStream, StandardCharsets.UTF_8);
            // This step is mandatory because of the way templates are handled in IntelliJ
            String replacedTemplateString = templateString.replace(WINDOWS_LINE_ENDINGS, UNIX_LINE_ENDINGS);
            addCompletion(resultSet, templateName, replacedTemplateString);
        }
    }

    private void addCompletion(CompletionResultSet resultSet, String name, String template) {
        LookupElementBuilder templateBuilder = LookupElementBuilder.create(template)
                .withPresentableText(name);
        resultSet.addElement(templateBuilder);
    }

    private void addYamlKeywordsUsedByCloudSlang(CompletionResultSet resultSet) {
        String namespace = "namespace";
        addCompletion(resultSet, namespace, namespace + ":");

        String imports = "imports";
        addCompletion(resultSet, imports, imports + ":");

        String extensions = "extensions";
        addCompletion(resultSet, extensions, extensions + ":");
    }

}
