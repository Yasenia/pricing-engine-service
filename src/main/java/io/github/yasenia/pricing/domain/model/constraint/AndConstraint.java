package io.github.yasenia.pricing.domain.model.constraint;

import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;

import java.util.List;

public record AndConstraint(
    List<PromotionConstraint> subConstraints
) implements PromotionConstraint {

    @Override
    public boolean isSatisfied(CalculationContext calculationContext) {
        return subConstraints.stream().allMatch(subConstraint -> subConstraint.isSatisfied(calculationContext));
    }
}
