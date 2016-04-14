package com.intellij.lang.cloudslang.plugin;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class CloudSlanLexerAdapter extends FlexAdapter {

    public CloudSlanLexerAdapter() {
        super(new CloudSlangLexer((Reader) null));
    }

}

