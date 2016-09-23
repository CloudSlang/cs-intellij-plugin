package com.intellij.lang.cloudslang.completion;

import com.intellij.codeInsight.template.FileTypeBasedContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLFileType;

import static com.intellij.lang.cloudslang.CloudSlangFileUtils.isCloudSlangFile;


public class CloudSlangTemplateContextType extends FileTypeBasedContextType {

    private static final String CLOUD_SLANG = "CloudSlang";

    public CloudSlangTemplateContextType() {
        super(CLOUD_SLANG, CLOUD_SLANG, YAMLFileType.YML);
    }

    @Override
    public boolean isInContext(@NotNull PsiFile psiFile, int i) {
        return isCloudSlangFile(psiFile);
    }

}
