package com.intellij.lang.cloudslang.plugin;

import com.intellij.lang.cloudslang.parsing.CloudSlangTokenType;
import com.intellij.lang.cloudslang.plugin.psi.CloudSlangTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class CloudSlangSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey SEPARATOR =
            createTextAttributesKey("NAMESPACE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KEY =
            createTextAttributesKey("NAMESPACE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE =
            createTextAttributesKey("NAMESPACE_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new CloudSlangLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(CloudSlangTypes.NAMESPACE_SEPARATOR)) {
            return SEPARATOR_KEYS;
        } else if (tokenType.equals(CloudSlangTypes.NAMESPACE_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_ACTION_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_INPUTS_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_JAVA_ACTION_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD)
                || tokenType.equals(CloudSlangTypes.OPERATION_NAME_KEYWORD)
                ) {
            return KEY_KEYS;
        } else if (tokenType.equals(CloudSlangTypes.NAMESPACE_VALUE)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(CloudSlangTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}