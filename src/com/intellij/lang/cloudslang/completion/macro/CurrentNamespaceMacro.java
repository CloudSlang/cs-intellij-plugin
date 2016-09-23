package com.intellij.lang.cloudslang.completion.macro;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Macro;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;


public class CurrentNamespaceMacro extends Macro {

    private static final String DEFAULT_NAMESPACE_TO_USE = "io.cloudslang.content";
    private static final String RELATIVE_PATH_TO_PROJECT = "relativePathToProject";

    @Override
    public String getName() {
        return RELATIVE_PATH_TO_PROJECT;
    }

    @Override
    public String getPresentableName() {
        return RELATIVE_PATH_TO_PROJECT;
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
        Project project = context.getProject();
        if (context.getEditor() == null) {
            return new TextResult(DEFAULT_NAMESPACE_TO_USE);
        }
        String basePath = project.getBasePath();
        PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
        String path = file.getVirtualFile().getPath();

        return null;
    }

    @Override
    public Result calculateQuickResult(@NotNull Expression[] params, ExpressionContext context) {
        return calculateResult(params, context);
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        return true;
    }
}
