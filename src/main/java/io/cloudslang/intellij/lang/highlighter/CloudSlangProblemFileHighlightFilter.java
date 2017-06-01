/*******************************************************************************
 * (c) Copyright 2016-2017 Hewlett-Packard Enterprise Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.intellij.lang.highlighter;

import io.cloudslang.intellij.lang.CloudSlangFileType;
import io.cloudslang.intellij.lang.CloudSlangFileUtils;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;


public class CloudSlangProblemFileHighlightFilter implements Condition<VirtualFile> {
    @Override
    public boolean value(VirtualFile virtualFile) {
        return (virtualFile.getFileType() == CloudSlangFileType.INSTANCE)
                || (CloudSlangFileUtils.isCloudSlangFile(virtualFile.getName()));
    }
}
