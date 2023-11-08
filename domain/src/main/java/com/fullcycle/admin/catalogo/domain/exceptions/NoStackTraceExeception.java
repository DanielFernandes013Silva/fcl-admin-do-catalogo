package com.fullcycle.admin.catalogo.domain.exceptions;

public class NoStackTraceExeception extends RuntimeException{
    public NoStackTraceExeception(final String message) {
        this(message, null);
    }

    public NoStackTraceExeception(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
