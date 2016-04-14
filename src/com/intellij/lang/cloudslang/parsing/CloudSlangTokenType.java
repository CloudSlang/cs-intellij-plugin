package com.intellij.lang.cloudslang.parsing;


import com.intellij.lang.cloudslang.CloudSlangLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;


public class CloudSlangTokenType extends IElementType {

    public CloudSlangTokenType(@NonNls String debugName) {
        super(debugName, CloudSlangLanguage.INSTANCE);
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        return "Cloud Slang:" + super.toString();
    }
}
