package io.cloudslang.intellij.lang.highlighter;

import com.intellij.codeInsight.daemon.ProblemHighlightFilter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;


public class CloudSlangProblemHighlightFilter extends ProblemHighlightFilter {
    @Override
    public boolean shouldHighlight(@NotNull PsiFile psiFile) {
        return true;
    }
}
