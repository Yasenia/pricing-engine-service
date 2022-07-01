package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.model.rule.FixedAmountDiscountRule;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;

import java.math.BigDecimal;

public class PromotionRules {

    public static PromotionRule aPromotionRule() {
        return new FixedAmountDiscountRule(BigDecimal.ONE);
    }
}
