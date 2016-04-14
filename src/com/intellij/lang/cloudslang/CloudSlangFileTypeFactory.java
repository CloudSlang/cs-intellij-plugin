package com.intellij.lang.cloudslang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

/**
 * Created by utiud on 4/14/2016.
 */
public class CloudSlangFileTypeFactory extends FileTypeFactory {

    public void createFileTypes(FileTypeConsumer fileTypeConsumer) {
       fileTypeConsumer.consume(CloudSlangFileType.INSTANCE, "slang");
    }
}
