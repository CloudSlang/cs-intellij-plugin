package com.intellij.lang.cloudslang.completion.macro;

import com.intellij.codeInsight.completion.proc.VariablesProcessor;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Macro;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.util.PsiScopesUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.containers.ContainerUtil;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CompleteInputMacro extends Macro {

    @Override
    public String getName() {
        return "completeInput";
    }

    @Override
    public String getPresentableName() {
        return "completeInput";
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, final ExpressionContext context) {
        if (params.length != 0) return null;

        final Project project = context.getProject();
        final int offset = context.getStartOffset();

        PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
        PsiElement place = file.findElementAt(offset);
        PsiVariable[] vars = MacroUtil.getVariablesVisibleAt(place, "");
        ChooseLetterLoop:
        for (char letter = 'i'; letter <= 'z'; letter++) {
            for (PsiVariable var : vars) {
                PsiIdentifier identifier = var.getNameIdentifier();
                if (identifier == null || place.equals(identifier)) continue;
                if (var instanceof PsiLocalVariable) {
                    PsiElement parent = var.getParent();
                    if (parent instanceof PsiDeclarationStatement) {
                        if (PsiTreeUtil.isAncestor(parent, place, false) &&
                                var.getTextRange().getStartOffset() > place.getTextRange().getStartOffset()) {
                            continue;
                        }
                    }
                }
                String name = identifier.getText();
                if (name.length() == 1 && name.charAt(0) == letter) {
                    continue ChooseLetterLoop;
                }
            }
            return new TextResult("" + letter);
        }

        return null;
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        return true;
    }

    @NotNull
    public static PsiVariable[] getVariablesVisibleAt(@Nullable final PsiElement place, String prefix) {
        if (place == null) {
            return new PsiVariable[0];
        }

        final Set<String> usedNames = ContainerUtil.newHashSet();
        final List<PsiVariable> list = new ArrayList<>();
        VariablesProcessor varproc = new VariablesProcessor(prefix, true, list) {
            @Override
            public boolean execute(@NotNull PsiElement pe, @NotNull ResolveState state) {
                if (pe instanceof PsiVariable) {
                    if (!usedNames.add(((PsiVariable) pe).getName())) {
                        return false;
                    }
                    //exclude variables that are initialized in 'place'
                    final PsiExpression initializer = ((PsiVariable) pe).getInitializer();
                    if (initializer != null && PsiTreeUtil.isAncestor(initializer, place, false)) return true;
                }
                return pe instanceof PsiField && !PsiUtil.isAccessible((PsiField) pe, place, null) || super.execute(pe, state);
            }
        };
        PsiScopesUtil.treeWalkUp(varproc, place, null);
        return varproc.getResultsAsArray();
    }
}
