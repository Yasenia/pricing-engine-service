package io.github.yasenia.pricing.domain.model.rule;

import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;
import io.github.yasenia.pricing.domain.model.calculation.CalculationItem;
import io.github.yasenia.pricing.domain.model.calculation.CalculationResult;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public record FixedAmountDiscountRule(
    BigDecimal discountAmount
) implements PromotionRule {

    @Override
    public CalculationResult applyTo(CalculationContext calculationContext) {
        var latestTotalPrice = calculationContext.latestResult().totalPrice();
        if (latestTotalPrice.compareTo(ZERO) == 0) return new CalculationResult(calculationContext.latestResult().items());
        var calculatedTotalPrice = latestTotalPrice.subtract(discountAmount).max(ZERO);
        var discountRate = calculatedTotalPrice.divide(latestTotalPrice, 4, HALF_EVEN);
        return new CalculationResult(
            calculationContext.latestResult().items().stream().map(calculationItem -> new CalculationItem(
                calculationItem.productId(),
                calculationItem.quantity(),
                calculationItem.subtotalPrice().multiply(discountRate)
            )).toList()
        );
    }
}
