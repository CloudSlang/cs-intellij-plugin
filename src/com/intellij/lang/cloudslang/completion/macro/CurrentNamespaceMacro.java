package com.intellij.lang.cloudslang.completion.macro;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Macro;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Ligia Centea
 * Date: 9/22/2016.
 */
public class CurrentNamespaceMacro extends Macro {
    @Override
    public String getName() {
        return "currentNamespace";
    }

    @Override
    public String getPresentableName() {
        return "currentNamespace";
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
//        Project project = context.getProject();
//        PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
//        file.getVirtualFile().getParent().getPath()
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
