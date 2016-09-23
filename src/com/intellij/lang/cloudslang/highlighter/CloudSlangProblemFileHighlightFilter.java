package com.intellij.lang.cloudslang.highlighter;

import com.intellij.lang.cloudslang.CloudSlangFileType;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;


public class CloudSlangProblemFileHighlightFilter implements Condition<VirtualFile> {
    @Override
    public boolean value(VirtualFile virtualFile) {
        return virtualFile.getFileType() == CloudSlangFileType.INSTANCE;
    }
}
