package io.github.yasenia.pricing.query.view;

import java.math.BigDecimal;

import static io.github.yasenia.pricing.query.view.PromotionRuleView.RuleType.FIXED_AMOUNT_DISCOUNT;

public class PromotionRuleViews {
    public static PromotionRuleView aPromotionRuleView() {
        return new PromotionRuleView(FIXED_AMOUNT_DISCOUNT, BigDecimal.ONE);
    }
}
