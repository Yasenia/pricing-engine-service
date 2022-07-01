package io.github.yasenia.pricing.rest.request.payload;

import io.github.yasenia.pricing.domain.model.rule.FixedAmountDiscountRule;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;

import java.math.BigDecimal;

public record PromotionRulePayload(
    RuleType type,
    BigDecimal discountAmount
) {

    public enum RuleType {
        FIXED_AMOUNT_DISCOUNT
    }

    public PromotionRule toRule() {
        return switch (type) {
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountDiscountRule(discountAmount);
        };
    }
}
