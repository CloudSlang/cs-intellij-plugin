// This is a generated file. Not intended for manual editing.
package com.intellij.lang.cloudslang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.intellij.lang.cloudslang.plugin.psi.CloudSlangTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.cloudslang.plugin.psi.*;

public class CloudSlangPropertyImpl extends ASTWrapperPsiElement implements CloudSlangProperty {

  public CloudSlangPropertyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CloudSlangVisitor) ((CloudSlangVisitor)visitor).visitProperty(this);
    else super.accept(visitor);
  }

}
