package io.github.yasenia.pricing.domain.model.calculation;

import io.github.yasenia.pricing.common.Randoms;
import io.github.yasenia.pricing.domain.exception.UnsatisfiedPromotionConstraintException;
import io.github.yasenia.pricing.domain.model.CalculationResults;
import io.github.yasenia.pricing.domain.model.Promotion;
import org.junit.jupiter.api.Test;

import static io.github.yasenia.pricing.common.Generators.aListOf;
import static io.github.yasenia.pricing.common.Generators.aNotEmptyListOf;
import static io.github.yasenia.pricing.common.Texts.aPromotionCode;
import static io.github.yasenia.pricing.domain.model.CalculationContexts.aCalculationContext;
import static io.github.yasenia.pricing.domain.model.CalculationContexts.newCalculationContext;
import static io.github.yasenia.pricing.domain.model.CalculationSteps.aCalculationStep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CalculationContextTest {

    @Test
    void should_return_initial_result_when_get_latest_result_given_no_steps_existed() {
        var context = newCalculationContext()
            .withoutSteps()
            .build();
        assertThat(context.latestResult().items(), equalTo(context.initialItems()));
    }

    @Test
    void should_return_result_of_the_last_step_when_get_latest_result_given_the_context_contains_steps() {
        var lastStep = aCalculationStep();
        var context = newCalculationContext()
            .withStep(lastStep)
            .build();
        assertThat(context.latestResult(), equalTo(lastStep.result()));
    }

    @Test
    void should_apply_promotions_one_by_one_when_apply_promotions_given_all_promotions_are_satisfied_the_context() {
        // given
        var initialContext = aCalculationContext();
        var promotions = aListOf(() -> mock(Promotion.class));
        var results = aListOf(promotions.size(), CalculationResults::aCalculationResult);
        for (int i = 0; i < promotions.size(); i++) {
            given(promotions.get(i).getCode()).willReturn(aPromotionCode());
            given(promotions.get(i).applyTo(any())).willReturn(results.get(i));
            given(promotions.get(i).isSatisfied(any())).willReturn(true);
        }
        // then
        var finalContext = assertDoesNotThrow(() -> {
            // when
            return initialContext.applyPromotions(promotions);
        });
        // and then
        assertThat(finalContext.steps(), hasSize(promotions.size()));
        for (int i = 0; i < finalContext.steps().size(); i++) {
            var calculationStep = finalContext.steps().get(i);
            assertThat(calculationStep.promotionCode(), equalTo(promotions.get(i).getCode()));
            assertThat(calculationStep.result(), equalTo(results.get(i)));
        }
    }

    @Test
    void should_throw_exception_when_apply_promotions_given_any_promotion_is_not_satisfied() {
        // given
        var initialContext = aCalculationContext();
        var promotions = aNotEmptyListOf(() -> mock(Promotion.class));
        var results = aListOf(promotions.size(), CalculationResults::aCalculationResult);
        for (int i = 0; i < promotions.size(); i++) {
            given(promotions.get(i).getCode()).willReturn(aPromotionCode());
            given(promotions.get(i).applyTo(any())).willReturn(results.get(i));
            given(promotions.get(i).isSatisfied(any())).willReturn(true);
        }
        var unsatisfiedPromotion = promotions.get(Randoms.RANDOM.nextInt(promotions.size()));
        given(unsatisfiedPromotion.isSatisfied(any())).willReturn(false);
        // then
        var exception = assertThrows(UnsatisfiedPromotionConstraintException.class, () -> {
            // when
            initialContext.applyPromotions(promotions);
        });
        // and then
        assertThat(exception.promotionCode, equalTo(unsatisfiedPromotion.getCode()));
    }
}
