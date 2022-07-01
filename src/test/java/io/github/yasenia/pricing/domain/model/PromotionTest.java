package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.yasenia.core.tool.Times.currentTime;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.domain.model.CalculationContexts.aCalculationContext;
import static io.github.yasenia.pricing.domain.model.CalculationResults.aCalculationResult;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;
import static io.github.yasenia.pricing.domain.model.Promotions.aPromotion;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PromotionTest {

    @Test
    void should_update_promotion_info_when_edit_info() {
        // given
        var promotion = aPromotion();
        var editorId = aUserId();
        var promotionInfo = aPromotionInfo();
        var currentTime = currentTime();
        // then
        assertDoesNotThrow(() -> {
            // when
            promotion.editInfo(editorId, promotionInfo);
        });
        // and then
        assertThat(promotion.getInfo(), equalTo(promotionInfo));
        assertThat(promotion.getLastEditorId(), equalTo(editorId));
        assertThat(promotion.getLastEditTime(), greaterThanOrEqualTo(currentTime));
    }

    @Test
    void should_throw_exception_when_edit_info_given_invalid_promotion_info() {
        // given
        var promotion = aPromotion();
        var promotionInfo = mock(PromotionInfo.class);
        given(promotionInfo.isValid()).willReturn(false);
        // then
        assertThrows(InvalidPromotionInfoException.class, () -> {
            // when
            promotion.editInfo(aUserId(), promotionInfo);
        });
    }

    @Test
    void should_delegate_to_the_rule_when_apply_the_promotion_to_a_calculation_context() {
        // given
        var promotionRule = mock(PromotionRule.class);
        var originalContext = aCalculationContext();
        var calculationResult = aCalculationResult();
        var promotion = spy(aPromotion());
        given(promotionRule.applyTo(originalContext)).willReturn(calculationResult);
        given(promotion.getRule()).willReturn(promotionRule);
        // when
        var result = promotion.applyTo(originalContext);
        // then
        verify(promotionRule, only()).applyTo(originalContext);
        assertThat(result, equalTo(calculationResult));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_delegate_to_the_constraint_when_judging_if_the_promotion_satisfied_a_calculation_context(boolean isSatisfied) {
        // given
        var promotionConstraint = mock(PromotionConstraint.class);
        var originalContext = aCalculationContext();
        var promotion = spy(aPromotion());
        given(promotionConstraint.isSatisfied(originalContext)).willReturn(isSatisfied);
        given(promotion.getConstraint()).willReturn(promotionConstraint);
        // when
        var result = promotion.isSatisfied(originalContext);
        // then
        verify(promotionConstraint, only()).isSatisfied(originalContext);
        assertThat(result, equalTo(isSatisfied));
    }
}
