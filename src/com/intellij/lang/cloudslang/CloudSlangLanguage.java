package com.intellij.lang.cloudslang;

import com.intellij.lang.Language;


public class CloudSlangLanguage extends Language {

    public static final CloudSlangLanguage INSTANCE = new CloudSlangLanguage();

    protected CloudSlangLanguage() {
        super("CloudSlang");
    }
}
