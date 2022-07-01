package io.github.yasenia.pricing.domain.model.calculation;

import io.github.yasenia.pricing.domain.exception.UnsatisfiedPromotionConstraintException;
import io.github.yasenia.pricing.domain.model.Promotion;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public record CalculationContext(
    List<CalculationItem> initialItems,
    List<CalculationStep> steps
) {

    public CalculationContext(List<CalculationItem> initialItems) {
        this(initialItems, emptyList());
    }

    public CalculationResult latestResult() {
        return steps.isEmpty() ? new CalculationResult(initialItems) : steps.get(steps.size() - 1).result();
    }

    public CalculationContext applyPromotions(List<Promotion> promotions) throws UnsatisfiedPromotionConstraintException {
        if (promotions.isEmpty()) return this;
        var promotion = promotions.get(0);
        if (!promotion.isSatisfied(this)) throw new UnsatisfiedPromotionConstraintException(promotion.getCode());
        var calculationStep = new CalculationStep(promotion.getCode(), promotion.applyTo(this));
        return new CalculationContext(
            this.initialItems,
            Stream.concat(this.steps.stream(), Stream.of(calculationStep)).toList()
        ).applyPromotions(promotions.subList(1, promotions.size()));
    }
}
