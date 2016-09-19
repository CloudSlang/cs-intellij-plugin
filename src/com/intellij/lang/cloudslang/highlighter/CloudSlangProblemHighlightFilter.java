package com.intellij.lang.cloudslang.highlighter;

import com.intellij.codeInsight.daemon.ProblemHighlightFilter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Ligia Centea
 * Date: 9/12/2016.
 */
public class CloudSlangProblemHighlightFilter extends ProblemHighlightFilter {
    @Override
    public boolean shouldHighlight(@NotNull PsiFile psiFile) {
        return true;
    }
}
