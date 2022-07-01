package io.github.yasenia.pricing.domain.exception;

import io.github.yasenia.core.exception.BusinessException;

import static io.github.yasenia.pricing.domain.exception.DomainErrorCodes.NO_PRICE_FETCHED_EXCEPTION;

public class PriceNotFetchedException extends BusinessException {

    public final String productId;

    public PriceNotFetchedException(String productId) {
        super(NO_PRICE_FETCHED_EXCEPTION, productId);
        this.productId = productId;
    }
}
