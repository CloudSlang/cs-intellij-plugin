package com.intellij.lang.cloudslang;

import com.intellij.openapi.fileTypes.LanguageFileType;

import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;

public class CloudSlangFileType extends LanguageFileType {

    public static final CloudSlangFileType INSTANCE = new CloudSlangFileType();

    private CloudSlangFileType() {
        super(YAMLLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return "CloudSlang file";
    }

    @NotNull
    public String getDescription() {
        return "CloudSlang language file";
    }


    @NotNull
    public String getDefaultExtension() {
        return "sl";
    }

    public Icon getIcon() {
        return CloudSlangIcons.FILE;
    }
}

