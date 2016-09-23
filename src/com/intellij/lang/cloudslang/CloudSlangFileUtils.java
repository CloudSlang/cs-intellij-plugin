package com.intellij.lang.cloudslang;


import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.YAMLFileType;

public class CloudSlangFileUtils {

    private static final String[] CLOUD_SLANG_EXTENSIONS = {".sl", ".sl.yaml", ".sl.yml", ".prop.sl"};

    private CloudSlangFileUtils() {
    }

    public static boolean isCloudSlangFile(PsiFile psiFile) {
        if (psiFile.getFileType() != YAMLFileType.YML) {
            return false;
        }
        String fileName = psiFile.getName();
        for (String extension : CLOUD_SLANG_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}
