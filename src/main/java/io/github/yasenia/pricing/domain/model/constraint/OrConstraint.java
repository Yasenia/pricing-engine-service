package io.github.yasenia.pricing.domain.model.constraint;

import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;

import java.util.List;

public record OrConstraint(
    List<PromotionConstraint> subConstraints
) implements PromotionConstraint {

    @Override
    public boolean isSatisfied(CalculationContext calculationContext) {
        return subConstraints.stream().anyMatch(subConstraint -> subConstraint.isSatisfied(calculationContext));
    }
}
