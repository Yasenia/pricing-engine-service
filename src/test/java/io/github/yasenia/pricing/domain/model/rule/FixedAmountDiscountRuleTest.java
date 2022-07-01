package io.github.yasenia.pricing.domain.model.rule;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.github.yasenia.pricing.common.Numbers.DELTA;
import static io.github.yasenia.pricing.domain.model.CalculationContexts.newCalculationContext;
import static io.github.yasenia.pricing.domain.model.CalculationItems.newCalculationItem;
import static io.github.yasenia.pricing.domain.model.CalculationResults.newCalculationResult;
import static io.github.yasenia.pricing.domain.model.CalculationSteps.newCalculationStep;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;

class FixedAmountDiscountRuleTest {

    @Test
    void should_discount_with_fixed_amount() {
        // given
        var promotionRule = new FixedAmountDiscountRule(BigDecimal.valueOf(100));
        var calculationContext = newCalculationContext()
            .withStep(newCalculationStep()
                .withResult(newCalculationResult()
                    .withItem(newCalculationItem().withSubtotalPrice(BigDecimal.valueOf(100)).build())
                    .withItem(newCalculationItem().withSubtotalPrice(BigDecimal.valueOf(200)).build())
                    .withItem(newCalculationItem().withSubtotalPrice(BigDecimal.valueOf(300)).build())
                    .withItem(newCalculationItem().withSubtotalPrice(BigDecimal.valueOf(400)).build())
                    .build()
                )
                .build()
            )
            .build();
        // when
        var calculationResult = promotionRule.applyTo(calculationContext);
        // then
        assertThat(calculationResult.totalPrice(), closeTo(BigDecimal.valueOf(900), DELTA));
        assertThat(calculationResult.items(), hasSize(calculationResult.items().size()));
        assertThat(calculationResult.items().get(0).subtotalPrice(), closeTo(BigDecimal.valueOf(90), DELTA));
        assertThat(calculationResult.items().get(1).subtotalPrice(), closeTo(BigDecimal.valueOf(180), DELTA));
        assertThat(calculationResult.items().get(2).subtotalPrice(), closeTo(BigDecimal.valueOf(270), DELTA));
        assertThat(calculationResult.items().get(3).subtotalPrice(), closeTo(BigDecimal.valueOf(360), DELTA));
    }

    @Test
    void should_discount_to_zero_given_discount_amount_exceed_the_total_price() {
        // given
        var promotionRule = new FixedAmountDiscountRule(BigDecimal.valueOf(100));
        var calculationContext = newCalculationContext()
            .withStep(newCalculationStep()
                .withResult(newCalculationResult()
                    .withItem(newCalculationItem().withSubtotalPrice(BigDecimal.valueOf(10)).build())
                    .withItem(newCalculationItem().withSubtotalPrice(BigDecimal.valueOf(20)).build())
                    .build()
                )
                .build()
            )
            .build();
        // when
        var calculationResult = promotionRule.applyTo(calculationContext);
        // then
        assertThat(calculationResult.totalPrice(), closeTo(ZERO, DELTA));
        assertThat(calculationResult.items(), hasSize(calculationResult.items().size()));
        calculationResult.items().forEach(item -> assertThat(item.subtotalPrice(), closeTo(ZERO, DELTA)));
    }

    @Test
    void should_discount_to_zero_given_total_price_already_be_zero() {
        // given
        var promotionRule = new FixedAmountDiscountRule(BigDecimal.valueOf(100));
        var calculationContext = newCalculationContext()
            .withStep(newCalculationStep()
                .withResult(newCalculationResult()
                    .withItem(newCalculationItem().withSubtotalPrice(ZERO).build())
                    .withItem(newCalculationItem().withSubtotalPrice(ZERO).build())
                    .build()
                )
                .build()
            )
            .build();
        // when
        var calculationResult = promotionRule.applyTo(calculationContext);
        // then
        assertThat(calculationResult.totalPrice(), closeTo(ZERO, DELTA));
        assertThat(calculationResult.items(), hasSize(calculationResult.items().size()));
        calculationResult.items().forEach(item -> assertThat(item.subtotalPrice(), closeTo(ZERO, DELTA)));
    }
}
