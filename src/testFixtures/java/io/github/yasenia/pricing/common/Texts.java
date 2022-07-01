package io.github.yasenia.pricing.common;

import io.github.yasenia.pricing.domain.factory.PromotionFactory;

import static io.github.yasenia.pricing.common.Randoms.aRandomSuffix;
import static io.github.yasenia.pricing.common.Randoms.aRandomText;

public class Texts {

    public static String aUserId() {
        return aRandomText(8);
    }

    public static String aProductId() {
        return aRandomText(8);
    }

    public static String aPromotionId() {
        return aRandomText(8);
    }

    public static String aPromotionCode() {
        return aRandomText(PromotionFactory.PROMOTION_CODE_LENGTH);
    }

    public static String aPromotionTitle() {
        return "A promotion title for test - %s".formatted(aRandomSuffix());
    }

    public static String aPromotionDescription() {
        return "A promotion description for test - %s".formatted(aRandomSuffix());
    }
}
