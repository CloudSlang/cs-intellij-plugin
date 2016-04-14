// This is a generated file. Not intended for manual editing.
package com.intellij.lang.cloudslang.plugin.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.cloudslang.parsing.CloudSlangTokenType;

public interface CloudSlangTypes {


  IElementType COMMENT = new CloudSlangTokenType("COMMENT");
  IElementType CRLF = new CloudSlangTokenType("CRLF");
  IElementType NAMESPACE_KEYWORD = new CloudSlangTokenType("NAMESPACE_KEYWORD");
  IElementType NAMESPACE_SEPARATOR = new CloudSlangTokenType("NAMESPACE_SEPARATOR");
  IElementType NAMESPACE_VALUE = new CloudSlangTokenType("NAMESPACE_VALUE");
  IElementType SPACE = new CloudSlangTokenType("SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
