/*******************************************************************************
 * (c) Copyright 2016 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
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
