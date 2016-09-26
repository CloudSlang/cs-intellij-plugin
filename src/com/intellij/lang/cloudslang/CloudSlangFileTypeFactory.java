package com.intellij.lang.cloudslang;

import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLFileTypeLoader;

import static com.intellij.lang.cloudslang.CloudSlangFileUtils.isCloudSlangFile;


public class CloudSlangFileTypeFactory extends YAMLFileTypeLoader {

    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        super.createFileTypes(fileTypeConsumer);
        fileTypeConsumer.consume(CloudSlangFileType.INSTANCE, new FileNameMatcher() {
            @Override
            public boolean accept(@NonNls @NotNull String s) {
                return isCloudSlangFile(s);
            }

            @NotNull
            @Override
            public String getPresentableString() {
                return "sl;sl.yaml;sl.yml;prop.sl";
            }
        });
    }
}
