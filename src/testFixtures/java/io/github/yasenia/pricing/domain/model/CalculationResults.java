package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.model.calculation.CalculationItem;
import io.github.yasenia.pricing.domain.model.calculation.CalculationResult;

import java.util.ArrayList;
import java.util.List;

import static io.github.yasenia.pricing.common.Generators.aListOf;

public class CalculationResults {

    private final List<CalculationItem> items = new ArrayList<>();

    public static CalculationResult aCalculationResult() {
        return newCalculationResult()
            .withItems(aListOf(CalculationItems::aCalculationItem))
            .build();
    }

    public static CalculationResults newCalculationResult() {
        return new CalculationResults();
    }

    public CalculationResults withItem(CalculationItem item) {
        this.items.add(item);
        return this;
    }

    public CalculationResults withItems(List<CalculationItem> items) {
        this.items.addAll(items);
        return this;
    }

    public CalculationResult build() {
        return new CalculationResult(items);
    }
}
