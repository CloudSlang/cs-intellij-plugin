package com.intellij.lang.cloudslang.parsing;


import com.intellij.lang.cloudslang.CloudSlangLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;


public class CloudSlangElementType extends IElementType {

    public CloudSlangElementType(@NonNls String debugName) {
        super(debugName, CloudSlangLanguage.INSTANCE);
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        return "Properties:" + super.toString();
    }
}
