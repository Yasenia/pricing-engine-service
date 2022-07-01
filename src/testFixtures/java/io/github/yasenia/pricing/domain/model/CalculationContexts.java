package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;
import io.github.yasenia.pricing.domain.model.calculation.CalculationItem;
import io.github.yasenia.pricing.domain.model.calculation.CalculationStep;

import java.util.ArrayList;
import java.util.List;

import static io.github.yasenia.pricing.domain.model.CalculationResults.aCalculationResult;
import static java.util.Collections.unmodifiableList;

public class CalculationContexts {

    private final List<CalculationItem> initialItems = new ArrayList<>();
    private final List<CalculationStep> step = new ArrayList<>();

    public static CalculationContext aCalculationContext() {
        return newCalculationContext().withInitialItems(aCalculationResult().items())
            .build();
    }

    public static CalculationContexts newCalculationContext() {
        return new CalculationContexts();
    }

    public CalculationContexts withInitialItems(List<CalculationItem> items) {
        this.initialItems.addAll(items);
        return this;
    }

    public CalculationContexts withInitialItem(CalculationItem item) {
        this.initialItems.add(item);
        return this;
    }

    public CalculationContexts withStep(CalculationStep step) {
        this.step.add(step);
        return this;
    }

    public CalculationContexts withoutSteps() {
        this.step.clear();
        return this;
    }

    public CalculationContext build() {
        return new CalculationContext(this.initialItems, unmodifiableList(this.step));
    }
}
