package com.intellij.lang.cloudslang.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.psi.YAMLScalar;

/**
 * Created by Ligia Centea
 * Date: 9/6/2016.
 */
public class CloudSlangCompletionContributor extends CompletionContributor {

    public CloudSlangCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(YAMLScalar.class).withLanguage(YAMLLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Hello"));
                    }
                }
        );
    }
}
