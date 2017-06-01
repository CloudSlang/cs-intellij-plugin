/*******************************************************************************
 * (c) Copyright 2016-2017 Hewlett-Packard Enterprise Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.intellij.lang;

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
        return CloudSlangIcons.STANDARD;
    }
}

