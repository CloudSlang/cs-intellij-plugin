package com.intellij.lang.cloudslang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;


public class CloudSlangFileTypeFactory extends FileTypeFactory {

    public void createFileTypes(FileTypeConsumer fileTypeConsumer) {
       fileTypeConsumer.consume(CloudSlangFileType.INSTANCE, "slang");
    }
}
