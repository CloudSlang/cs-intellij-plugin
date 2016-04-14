// This is a generated file. Not intended for manual editing.
package com.intellij.lang.cloudslang.plugin.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.cloudslang.parsing.CloudSlangElementType;
import com.intellij.lang.cloudslang.parsing.CloudSlangTokenType;
import com.intellij.lang.cloudslang.psi.impl.*;

public interface CloudSlangTypes {

  IElementType PROPERTY = new CloudSlangElementType("PROPERTY");

  IElementType COMMENT = new CloudSlangTokenType("COMMENT");
  IElementType CRLF = new CloudSlangTokenType("CRLF");
  IElementType KEY = new CloudSlangTokenType("KEY");
  IElementType SEPARATOR = new CloudSlangTokenType("SEPARATOR");
  IElementType VALUE = new CloudSlangTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == PROPERTY) {
        return new CloudSlangPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
