package com.intellij.lang.cloudslang.parsing;


import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public class CloudSlangTokenTypes {

    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    IElementType END_OF_LINE_COMMENT = new CloudSlangElementType("END_OF_LINE_COMMENT");
    IElementType KEY_CHARACTERS = new CloudSlangElementType("KEY_CHARACTERS");
    IElementType VALUE_CHARACTERS = new CloudSlangElementType("VALUE_CHARACTERS");
    IElementType KEY_VALUE_SEPARATOR = new CloudSlangElementType("KEY_VALUE_SEPARATOR");

    TokenSet COMMENTS = TokenSet.create(END_OF_LINE_COMMENT);
    TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);
}
