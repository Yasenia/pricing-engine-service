package io.github.yasenia.pricing.domain.exception;

import io.github.yasenia.core.exception.BusinessException;

import static io.github.yasenia.pricing.domain.exception.DomainErrorCodes.UNSATISFIED_PROMOTION_CONSTRAINT_EXCEPTION;

public class UnsatisfiedPromotionConstraintException extends BusinessException {

    public final String promotionCode;

    public UnsatisfiedPromotionConstraintException(String promotionCode) {
        super(UNSATISFIED_PROMOTION_CONSTRAINT_EXCEPTION, promotionCode);
        this.promotionCode = promotionCode;
    }
}
