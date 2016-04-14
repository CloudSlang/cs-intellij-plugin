package com.intellij.lang.cloudslang;

import com.intellij.openapi.fileTypes.LanguageFileType;

import javax.swing.Icon;

public class CloudSlangFileType extends LanguageFileType {

    public static final CloudSlangFileType INSTANCE = new CloudSlangFileType();

    private CloudSlangFileType() {
        super(CloudSlangLanguage.INSTANCE);
    }

    public String getName() {
        return "Slang file";
    }

    public String getDescription() {
        return "Slang language file";
    }


    public String getDefaultExtension() {
        return "slang";
    }

    public Icon getIcon() {
        return CloudSlangIconLoader.FILE;
    }
}

