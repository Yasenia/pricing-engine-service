package io.github.yasenia.pricing.domain.model.calculation;

import java.math.BigDecimal;

public record CalculationItem(
    String productId,
    int quantity,
    BigDecimal subtotalPrice
) {
}
