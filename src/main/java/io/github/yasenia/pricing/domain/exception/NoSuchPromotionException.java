package io.github.yasenia.pricing.domain.exception;

import io.github.yasenia.core.exception.BusinessException;

import static io.github.yasenia.pricing.domain.exception.DomainErrorCodes.NO_SUCH_PROMOTION_EXCEPTION;
import static io.github.yasenia.pricing.domain.exception.NoSuchPromotionException.KeyType.CODE;
import static io.github.yasenia.pricing.domain.exception.NoSuchPromotionException.KeyType.ID;

public class NoSuchPromotionException extends BusinessException {

    public final KeyType keyType;
    public final String key;

    private NoSuchPromotionException(KeyType keyType, String key) {
        super(NO_SUCH_PROMOTION_EXCEPTION, keyType, key);
        this.keyType = keyType;
        this.key = key;
    }

    public static NoSuchPromotionException ofId(String id) {
        return new NoSuchPromotionException(ID, id);
    }

    public static NoSuchPromotionException ofCode(String code) {
        return new NoSuchPromotionException(CODE, code);
    }

    public enum KeyType {
        ID, CODE
    }
}
