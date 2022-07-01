package io.github.yasenia.pricing.query.exception;

import io.github.yasenia.core.exception.BusinessException;

import static io.github.yasenia.pricing.query.exception.QueryErrorCodes.PROMOTION_NOT_FOUND_EXCEPTION;

public class PromotionNotFoundException extends BusinessException {

    public final String promotionId;

    public PromotionNotFoundException(String promotionId) {
        super(PROMOTION_NOT_FOUND_EXCEPTION, promotionId);
        this.promotionId = promotionId;
    }
}
