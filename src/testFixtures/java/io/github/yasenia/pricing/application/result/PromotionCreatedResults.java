package io.github.yasenia.pricing.application.result;

import static io.github.yasenia.pricing.common.Texts.aPromotionId;

public class PromotionCreatedResults {

    public static PromotionCreatedResult aPromotionCreatedResult() {
        return new PromotionCreatedResult(aPromotionId());
    }
}
