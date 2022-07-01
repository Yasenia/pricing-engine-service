package io.github.yasenia.pricing.domain.exception;

import io.github.yasenia.core.exception.BusinessException;

import static io.github.yasenia.pricing.domain.exception.DomainErrorCodes.INVALID_PROMOTION_INFO_EXCEPTION;

public class InvalidPromotionInfoException extends BusinessException {

    public InvalidPromotionInfoException() {
        super(INVALID_PROMOTION_INFO_EXCEPTION);
    }
}
