package com.intellij.lang.cloudslang.plugin.psi;



import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.cloudslang.CloudSlangFileType;
import com.intellij.lang.cloudslang.CloudSlangLanguage;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class CloudSlangFile extends PsiFileBase {

    public CloudSlangFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CloudSlangLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CloudSlangFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Cloud Slang File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}

