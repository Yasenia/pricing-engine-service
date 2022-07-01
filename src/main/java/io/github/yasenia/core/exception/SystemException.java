package io.github.yasenia.core.exception;

public abstract class SystemException extends RuntimeException {

    public final Throwable cause;
    public final String errorCode;
    public final String description;

    public SystemException(Throwable cause, String errorCode, String description) {
        this.cause = cause;
        this.errorCode = errorCode;
        this.description = description;
    }

    @Override
    public final synchronized Throwable getCause() {
        return this.cause;
    }

}
