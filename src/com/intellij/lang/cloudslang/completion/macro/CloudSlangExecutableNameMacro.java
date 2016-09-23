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


public class CloudSlangExecutableNameMacro extends Macro {
    @Override
    public String getName() {
        return "cloudSlangExecutableName";
    }

    @Override
    public String getPresentableName() {
        return "cloudSlangExecutableName()";
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
        Project project = context.getProject();
        PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
        String fileName = file.getVirtualFile().getNameWithoutExtension();
        return new TextResult(fileName);
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
