package io.github.yasenia.core.rest.exception;

public record ErrorResponse(
    String errorCode,
    String errorMessage
) {
}
