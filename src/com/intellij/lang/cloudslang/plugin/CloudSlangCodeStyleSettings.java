package com.intellij.lang.cloudslang.plugin;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

public class CloudSlangCodeStyleSettings extends CustomCodeStyleSettings {

    protected CloudSlangCodeStyleSettings(CodeStyleSettings settings) {
        super("CloudSlangCodeStyleSettings", settings);
    }
}
