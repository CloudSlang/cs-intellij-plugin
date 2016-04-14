package com.intellij.lang.cloudslang.plugin;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class CloudSlangLexerAdapter extends FlexAdapter {

    public CloudSlangLexerAdapter() {
        super(new CloudSlangLexer((Reader) null));
    }

}

