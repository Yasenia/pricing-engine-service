package io.github.yasenia.pricing.domain.model.constraint;

import io.github.yasenia.pricing.domain.model.product.ProductSet;
import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;

public record MustIncludedProductsConstraint(
    ProductSet mustIncludedProductSet
) implements PromotionConstraint {

    @Override
    public boolean isSatisfied(CalculationContext calculationContext) {
        return calculationContext.latestResult().items().stream()
            .anyMatch(item -> mustIncludedProductSet.include(item.productId()));
    }
}
