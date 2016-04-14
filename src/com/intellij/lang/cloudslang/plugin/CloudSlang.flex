package com.intellij.lang.cloudslang.plugin;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.cloudslang.parsing.CloudSlangElementType;
import com.intellij.psi.TokenType;
import com.intellij.lang.cloudslang.plugin.psi.CloudSlangTypes;

%%

%class CloudSlangLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF= \n|\r|\r\n
WHITE_SPACE=[\ \t\f]
//FIRST_VALUE_CHARACTER=[^ \n\r\f\\] | "\\"{CRLF} | "\\".
//VALUE_CHARACTER=[^\n\r\f\\] | "\\"{CRLF} | "\\".
END_OF_LINE_COMMENT=("#"|"!")[^\r\n]*

KEY_CHARACTER=[^:=\ \n\r\t\f\\] | "\\ "

// Namespace handling
NAMESPACE_KEYWORD=namespace
NAMESPACE_VALUE=[^:=\ \n\r\t\f\\]
NAMESPACE_SEPARATOR=[:]
SPACE=[ ]

%state WAITING_VALUE

%%

<YYINITIAL> {NAMESPACE_KEYWORD}                             { yybegin(YYINITIAL); return CloudSlangTypes.NAMESPACE_KEYWORD; }
<YYINITIAL> {NAMESPACE_SEPARATOR}                           { yybegin(YYINITIAL); return CloudSlangTypes.NAMESPACE_SEPARATOR; }
<YYINITIAL> {NAMESPACE_VALUE}+                           { yybegin(YYINITIAL); return CloudSlangTypes.NAMESPACE_VALUE; }
<YYINITIAL> {SPACE}                                     { yybegin(YYINITIAL); return CloudSlangTypes.SPACE; }

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return CloudSlangTypes.COMMENT; }
<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

//<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return CloudSlangTypes.VALUE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

{WHITE_SPACE}+                                              { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

.                                                           { return TokenType.BAD_CHARACTER; }
