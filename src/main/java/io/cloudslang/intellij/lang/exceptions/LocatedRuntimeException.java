/*******************************************************************************
 * (c) Copyright 2016-2017 Hewlett-Packard Enterprise Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.intellij.lang.exceptions;


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
