package io.github.yasenia.pricing.domain.model.calculation;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public record CalculationResult(
    List<CalculationItem> items
) {

    public BigDecimal totalPrice() {
        return items().stream().map(CalculationItem::subtotalPrice).reduce(BigDecimal::add).orElse(ZERO);
    }
}
