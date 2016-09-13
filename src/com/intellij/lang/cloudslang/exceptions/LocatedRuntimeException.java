package com.intellij.lang.cloudslang.exceptions;

/**
 * Created by Ligia Centea
 * Date: 9/12/2016.
 */
public class LocatedRuntimeException extends RuntimeException {

    public LocatedRuntimeException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    private int lineNumber;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
