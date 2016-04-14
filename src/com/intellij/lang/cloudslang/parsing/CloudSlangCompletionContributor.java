package com.intellij.lang.cloudslang.parsing;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.cloudslang.CloudSlangLanguage;
import com.intellij.lang.cloudslang.parsing.CloudSlangTokenTypes;
import com.intellij.lang.cloudslang.plugin.psi.CloudSlangTypes;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class CloudSlangCompletionContributor extends CompletionContributor {

    public CloudSlangCompletionContributor() throws Exception{
        CloudSlangTokenTypes cloudSlangTokenTypes = new CloudSlangTokenTypes();

        CompletionProvider<CompletionParameters> provider = new CompletionProvider<CompletionParameters>() {
            public void addCompletions(@NotNull CompletionParameters parameters,
                                       ProcessingContext context,
                                       @NotNull CompletionResultSet resultSet) {
                String[] words = {"namespace", "operation", "action", "name", "java_action", "inputs", "className", "methodName"};
                for (String word : words) {
                    resultSet.addElement(LookupElementBuilder.create(word));
                }
            }
        };
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.NAMESPACE_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_ACTION_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_INPUTS_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_JAVA_ACTION_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CloudSlangTypes.OPERATION_NAME_KEYWORD).withLanguage(CloudSlangLanguage.INSTANCE),
                provider
        );
    }
}