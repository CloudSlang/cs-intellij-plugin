package com.intellij.lang.cloudslang.completion;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Ligia Centea
 * Date: 9/21/2016.
 */
public class CloudSlangLiveTemplatesProvider implements DefaultLiveTemplatesProvider {
    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return new String[]{
                "com/intellij/lang/cloudslang/templates/csTemplates",
        };
    }

    @Nullable
    @Override
    public String[] getHiddenLiveTemplateFiles() {
        return new String[0];
    }
}
