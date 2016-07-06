package com.intellij.lang.cloudslang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLFileTypeLoader;


public class CloudSlangFileTypeFactory extends YAMLFileTypeLoader {

    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        super.createFileTypes(fileTypeConsumer);
        fileTypeConsumer.consume(CloudSlangFileType.INSTANCE, "sl");
    }
}
