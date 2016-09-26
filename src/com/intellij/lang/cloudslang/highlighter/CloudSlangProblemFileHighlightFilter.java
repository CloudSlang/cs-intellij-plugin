package com.intellij.lang.cloudslang.highlighter;

import com.intellij.lang.cloudslang.CloudSlangFileType;
import com.intellij.lang.cloudslang.CloudSlangFileUtils;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;


public class CloudSlangProblemFileHighlightFilter implements Condition<VirtualFile> {
    @Override
    public boolean value(VirtualFile virtualFile) {
        if (virtualFile.getFileType() == CloudSlangFileType.INSTANCE) {
            return true;
        }
        return CloudSlangFileUtils.isCloudSlangFile(virtualFile.getName());
    }
}
