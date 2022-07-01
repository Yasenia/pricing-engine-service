package io.github.yasenia.pricing.domain.model.calculation;

public record CalculationStep(
    String promotionCode,
    CalculationResult result
) {
}
