package io.github.yasenia.core.rest.exception;

import io.github.yasenia.core.exception.BusinessException;
import io.github.yasenia.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    public static final String DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE = "No error message found under error code <%s> for locale <%s>.";
    public static final String SYSTEM_EXCEPTION_MESSAGE = "A system exception occurred.";

    private final MessageSource messageSource;

    public ExceptionControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception, WebRequest webRequest) {
        var errorMessage = messageSource.getMessage(
            exception.errorCode,
            exception.args.toArray(),
            DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE.formatted(exception.errorCode, webRequest.getLocale()), webRequest.getLocale()
        );
        var errorResponse = new ErrorResponse(exception.errorCode, errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> systemExceptionHandler(SystemException exception) {
        LOGGER.error(
            "A system exception occurred. Error code: %s; Description: %s.".formatted(exception.errorCode, exception.description),
            exception
        );
        var errorResponse = new ErrorResponse(exception.errorCode, SYSTEM_EXCEPTION_MESSAGE);
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
