package io.github.yasenia.pricing.common.exception;

import io.github.yasenia.core.exception.SystemException;

import static io.github.yasenia.pricing.common.exception.CommonErrorCodes.DATA_DESERIALIZE_EXCEPTION;

public class DataDeserializeException extends SystemException {

    public DataDeserializeException(Throwable cause, Object rawValue, Class<?> targetClass) {
        super(
            cause,
            DATA_DESERIALIZE_EXCEPTION,
            "The system can not deserialize the data<%s> to the target class<%s>.".formatted(rawValue, targetClass)
        );
    }
}
