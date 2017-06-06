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

import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLFileTypeLoader;


public class CloudSlangFileTypeFactory extends YAMLFileTypeLoader {

    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        super.createFileTypes(fileTypeConsumer);
        fileTypeConsumer.consume(CloudSlangFileType.INSTANCE, new FileNameMatcher() {
            @Override
            public boolean accept(@NonNls @NotNull String s) {
                return CloudSlangFileUtils.isCloudSlangFile(s);
            }

            @NotNull
            @Override
            public String getPresentableString() {
                return "sl;sl.yaml;sl.yml;prop.sl";
            }
        });
    }
}
