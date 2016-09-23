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
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;

import static com.intellij.lang.cloudslang.CloudSlangIcons.STANDARD;

public class CloudSlangCompletionContributor extends CompletionContributor {

    public CloudSlangCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(YAMLLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                        addSlangKeywords(resultSet);
                        addYamlKeywordsUsedByCloudSlang(resultSet);
                    }
                }
        );
    }

    private void addSlangKeywords(CompletionResultSet resultSet) {
        for (Field field : SlangTextualKeys.class.getFields()) {
            try {
                String value = field.get(null).toString();
                addCompletion(resultSet, value, value + ":");
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    private void addCompletion(CompletionResultSet resultSet, String name, String template) {
        LookupElementBuilder templateBuilder = LookupElementBuilder.create(template)
                .withPresentableText(name)
                .withIcon(STANDARD);
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
