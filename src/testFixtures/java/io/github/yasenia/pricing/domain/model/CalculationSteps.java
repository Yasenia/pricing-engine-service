package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.model.calculation.CalculationResult;
import io.github.yasenia.pricing.domain.model.calculation.CalculationStep;

import static io.github.yasenia.pricing.common.Texts.aPromotionCode;
import static io.github.yasenia.pricing.domain.model.CalculationResults.aCalculationResult;

public class CalculationSteps {

    private String promotionCode;
    private CalculationResult result;

    public static CalculationStep aCalculationStep() {
        return newCalculationStep().build();
    }

    public static CalculationSteps newCalculationStep() {
        return new CalculationSteps()
            .withPromotionCode(aPromotionCode())
            .withResult(aCalculationResult());
    }

    public CalculationSteps withPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
        return this;
    }

    public CalculationSteps withResult(CalculationResult result) {
        this.result = result;
        return this;
    }

    public CalculationStep build() {
        return new CalculationStep(promotionCode, result);
    }
}
