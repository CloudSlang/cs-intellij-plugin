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
OPERATION_KEYWORD=operation
OPERATION_NAME_KEYWORD=name
OPERATION_INPUTS_KEYWORD=inputs
OPERATION_ACTION_KEYWORD=action
OPERATION_JAVA_ACTION_KEYWORD=java_action
OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD=className
OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD=methodName
OPERATION_RESULTS_KEYWORD=results
NAMESPACE_VALUE=[^:=\ \n\r\t\f\\]
NAMESPACE_SEPARATOR=[:]
SPACE=[ ]
MINUS=[-]

%state WAITING_VALUE

%%
<YYINITIAL> {MINUS}                                         { yybegin(YYINITIAL); return CloudSlangTypes.MINUS; }
<YYINITIAL> {NAMESPACE_KEYWORD}                             { yybegin(YYINITIAL); return CloudSlangTypes.NAMESPACE_KEYWORD; }
<YYINITIAL> {OPERATION_KEYWORD}                             { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_KEYWORD; }
<YYINITIAL> {OPERATION_NAME_KEYWORD}                        { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_NAME_KEYWORD; }
<YYINITIAL> {OPERATION_INPUTS_KEYWORD}                      { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_INPUTS_KEYWORD; }
<YYINITIAL> {OPERATION_ACTION_KEYWORD}                      { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_ACTION_KEYWORD; }
<YYINITIAL> {OPERATION_JAVA_ACTION_KEYWORD}                 { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_JAVA_ACTION_KEYWORD; }
<YYINITIAL> {OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD}      { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD; }
<YYINITIAL> {OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD}     { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD; }
//<YYINITIAL> {OPERATION_RESULTS_KEYWORD}                     { yybegin(YYINITIAL); return CloudSlangTypes.OPERATION_RESULTS_KEYWORD; }


<YYINITIAL> {NAMESPACE_SEPARATOR}                           { yybegin(YYINITIAL); return CloudSlangTypes.NAMESPACE_SEPARATOR; }
<YYINITIAL> {NAMESPACE_VALUE}+                              { yybegin(YYINITIAL); return CloudSlangTypes.NAMESPACE_VALUE; }
<YYINITIAL> {SPACE}                                         { yybegin(YYINITIAL); return CloudSlangTypes.SPACE; }
<YYINITIAL> {CRLF}                                          { yybegin(YYINITIAL); return CloudSlangTypes.CRLF; }

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return CloudSlangTypes.COMMENT; }
<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

//<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return CloudSlangTypes.VALUE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

{WHITE_SPACE}+                                              { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

.                                                           { return TokenType.BAD_CHARACTER; }
