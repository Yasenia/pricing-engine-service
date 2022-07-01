package io.github.yasenia.pricing.domain.exception;

public interface DomainErrorCodes {
    String NO_SUCH_PROMOTION_EXCEPTION = "BIZ_000001";
    String INVALID_PROMOTION_INFO_EXCEPTION = "BIZ_000002";
    String NO_PRICE_FETCHED_EXCEPTION = "BIZ_000003";
    String UNSATISFIED_PROMOTION_CONSTRAINT_EXCEPTION = "BIZ_000004";
}
