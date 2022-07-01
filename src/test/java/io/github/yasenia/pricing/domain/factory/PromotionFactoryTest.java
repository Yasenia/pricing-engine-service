package io.github.yasenia.pricing.domain.factory;

import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.model.PromotionInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.github.yasenia.core.tool.Times.currentTime;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.domain.model.PromotionConstraints.aPromotionConstraint;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;
import static io.github.yasenia.pricing.domain.model.PromotionRules.aPromotionRule;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class PromotionFactoryTest {

    private final PromotionFactory promotionFactory = new PromotionFactory();

    @Test
    void should_create_a_promotion() {
        // given
        var creatorId = aUserId();
        var promotionInfo = aPromotionInfo();
        var promotionConstraint = aPromotionConstraint();
        var promotionRule = aPromotionRule();
        var currentTime = currentTime();
        // then
        var createdPromotion = assertDoesNotThrow(() -> {
            // when
            return promotionFactory.createPromotion(creatorId, promotionInfo, promotionConstraint, promotionRule);
        });
        // and then
        assertThat(createdPromotion.getCode(), is(notNullValue()));
        assertThat(createdPromotion.getCreatorId(), equalTo(creatorId));
        assertThat(createdPromotion.getCreateTime(), greaterThanOrEqualTo(currentTime));
        assertThat(createdPromotion.getCreateTime(), lessThanOrEqualTo(currentTime()));
        assertThat(createdPromotion.getLastEditorId(), equalTo(creatorId));
        assertThat(createdPromotion.getLastEditTime(), equalTo(createdPromotion.getCreateTime()));
        assertThat(createdPromotion.getInfo(), equalTo(promotionInfo));
        assertThat(createdPromotion.getConstraint(), equalTo(promotionConstraint));
        assertThat(createdPromotion.getRule(), equalTo(promotionRule));
    }

    @Test
    void should_throw_exception_when_create_promotion_given_invalid_promotion_info() {
        // given
        var promotionInfo = Mockito.mock(PromotionInfo.class);
        given(promotionInfo.isValid()).willReturn(false);
        // then
        assertThrows(InvalidPromotionInfoException.class, () -> {
            // when
            promotionFactory.createPromotion(aUserId(), promotionInfo, aPromotionConstraint(), aPromotionRule());
        });
    }
}
