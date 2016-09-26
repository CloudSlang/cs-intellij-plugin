package com.intellij.lang.cloudslang;


import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.YAMLFileType;

import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;

public class CloudSlangFileUtils {

    private static final String[] CLOUD_SLANG_EXTENSIONS = {".sl", ".sl.yaml", ".sl.yml", ".prop.sl"};

    private CloudSlangFileUtils() {
    }

    public static boolean isCloudSlangFile(PsiFile psiFile) {
        return (psiFile.getFileType() == YAMLFileType.YML) && isCloudSlangFile(psiFile.getName());
    }

    public static boolean isCloudSlangFile(String fileName) {
        for (String extension : CLOUD_SLANG_EXTENSIONS) {
            if (endsWithIgnoreCase(fileName, extension)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCloudSlangSystemPropertiesFile(String fileName) {
        return endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[3]);
    }

    public static String getNameWithoutExtension(final String fileName) {
        // This code is to prevent order of array to affect this algorithm
        if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[3])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[3]));
        } else if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[0])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[0]));
        } else if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[1])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[1]));
        } else if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[2])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[2]));
        } else {
            return removeExtension(fileName);
        }
    }

}
