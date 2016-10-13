package io.cloudslang.intellij.lang.highlighter;

import io.cloudslang.intellij.lang.CloudSlangFileType;
import io.cloudslang.intellij.lang.CloudSlangFileUtils;
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
