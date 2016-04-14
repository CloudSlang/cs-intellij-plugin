package com.intellij.lang.cloudslang.parsing;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.cloudslang.CloudSlangLanguage;
import com.intellij.lang.cloudslang.parsing.CloudSlangTokenTypes;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class CloudSlangCompletionContributor extends CompletionContributor {

    public CloudSlangCompletionContributor() throws Exception{
        CloudSlangTokenTypes cloudSlangTokenTypes = new CloudSlangTokenTypes();

        extend(CompletionType.BASIC,

                PlatformPatterns.psiElement(cloudSlangTokenTypes.VALUE_CHARACTERS).withLanguage(CloudSlangLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("White Space Completion Hint"));
                    }
                }
        );
    }
}