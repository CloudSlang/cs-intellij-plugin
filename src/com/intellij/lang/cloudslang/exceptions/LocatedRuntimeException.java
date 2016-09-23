package com.intellij.lang.cloudslang.exceptions;


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
