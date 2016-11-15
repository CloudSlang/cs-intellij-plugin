/*******************************************************************************
 * (c) Copyright 2016 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.intellij.lang.completion.macro;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Macro;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

import static java.nio.file.Paths.get;
import static java.util.Locale.ENGLISH;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


public class CurrentNamespaceMacro extends Macro {

    private static final String DEFAULT_NAMESPACE_TO_USE = "io.cloudslang.content";
    private static final String RELATIVE_PATH_TO_PROJECT = "relativePathToProject";
    private static final String NAMESPACE_SEPARATOR = ".";

    @Override
    public String getName() {
        return RELATIVE_PATH_TO_PROJECT;
    }

    @Override
    public String getPresentableName() {
        return getName();
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
        Project project = context.getProject();
        if (context.getEditor() == null) {
            return new TextResult(DEFAULT_NAMESPACE_TO_USE);
        }

        PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
        if ((file == null) || (file.getVirtualFile() == null)) {
            return new TextResult(DEFAULT_NAMESPACE_TO_USE);
        }
        VirtualFile virtualFile = file.getVirtualFile();
        String editorFilePath = virtualFile.getPath();
        String projectPath = project.getBasePath();

        return new TextResult(fixNamespace(projectPath, editorFilePath));
    }

    @Override
    public Result calculateQuickResult(@NotNull Expression[] params, ExpressionContext context) {
        return calculateResult(params, context);
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        return true;
    }

    private String fixNamespace(final String projectPath, final String filePath) {
        // Exclude file name from namespace value
        Path relativePath = get(projectPath).relativize(get(new File(filePath).getParent()));
        int nameCount = relativePath.getNameCount();
        if ((nameCount <= 0) || StringUtils.isEmpty(relativePath.toString())) {
            return DEFAULT_NAMESPACE_TO_USE;
        }
        StringBuilder strBuilder = new StringBuilder(relativePath.toString().length());
        int nameCountMinusOne = nameCount - 1;
        for (int index = 0; index < nameCount; index++) {
            String cleanPart = fixPathPart(relativePath.getName(index).toString());
            if (isNotEmpty(cleanPart)) {
                strBuilder.append(cleanPart.toLowerCase(ENGLISH)); // namespace should be lowercase
                if (index < nameCountMinusOne) {
                    strBuilder.append(NAMESPACE_SEPARATOR);
                }
            }
        }
        return strBuilder.toString();
    }

    private String fixPathPart(final String pathPart) {
        // We don't support dashes and spaces in files only letters, digits and underscores
        return pathPart.replaceAll("\\s+", "_").replaceAll("[-]+", "_").replaceAll("[_]+", "_").replaceAll("\\W", "");
    }

}
