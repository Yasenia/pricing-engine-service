package io.github.yasenia.pricing.query;

import io.github.yasenia.pricing.domain.repository.PromotionRepository;
import io.github.yasenia.pricing.query.exception.PromotionNotFoundException;
import io.github.yasenia.pricing.query.view.PromotionBriefView;
import io.github.yasenia.testcore.QueryServiceTest;
import io.github.yasenia.testcore.TestTransactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.yasenia.pricing.common.Randoms.RANDOM;
import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.domain.model.Promotions.aPromotion;
import static io.github.yasenia.pricing.query.criteria.QueryPromotionCriterias.aQueryPromotionCriteria;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QueryServiceTest
class PromotionQueryServiceTest {

    @Autowired
    private PromotionQueryService promotionQueryService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Test
    void should_return_promotion_view_when_query_promotion_by_id_given_promotion_is_existed() {
        // given
        var promotion = aPromotion();
        promotionRepository.save(promotion);
        TestTransactions.commitAndRestart();
        // when
        var promotionView = assertDoesNotThrow(() -> promotionQueryService.queryPromotionById(promotion.getId()));
        // then
        assertThat(promotionView.id(), equalTo(promotion.getId()));
        assertThat(promotionView.code(), equalTo(promotion.getCode()));
        assertThat(promotionView.title(), equalTo(promotion.getInfo().title()));
        assertThat(promotionView.description(), equalTo(promotion.getInfo().description()));
        assertThat(promotionView.constraint(), is(notNullValue()));
        assertThat(promotionView.rule(), is(notNullValue()));
        assertThat(promotionView.creatorId(), equalTo(promotion.getCreatorId()));
        assertThat(promotionView.createTime(), equalTo(promotion.getCreateTime()));
        assertThat(promotionView.lastEditorId(), equalTo(promotion.getLastEditorId()));
        assertThat(promotionView.lastEditTime(), equalTo(promotion.getLastEditTime()));
    }

    @Test
    void should_throw_exception_when_query_promotion_by_id_given_promotion_is_not_existed() {
        // given
        var promotionId = aPromotionId();
        // then
        var exception = assertThrows(PromotionNotFoundException.class, () ->
            // when
            promotionQueryService.queryPromotionById(promotionId)
        );
        // and then
        assertThat(exception.promotionId, equalTo(promotionId));
    }

    @Test
    void should_return_promotion_count_when_count_promotions() {
        // given
        var criteria = aQueryPromotionCriteria();
        // when
        var countBeforeSaved = promotionQueryService.countPromotions(criteria);
        // then
        assertThat(countBeforeSaved, is(greaterThanOrEqualTo(0)));
        // and given
        var createTimes = 100;
        for (int i = 0; i < createTimes; i++) promotionRepository.save(aPromotion());
        TestTransactions.commitAndRestart();
        // and when
        var countAfterSaved = promotionQueryService.countPromotions(criteria);
        // and then
        assertThat(countAfterSaved, is(equalTo(countBeforeSaved + createTimes)));
    }

    @Test
    void should_return_promotions_when_query_promotions() {
        // given
        var createTimes = 100;
        for (int i = 0; i < createTimes; i++) promotionRepository.save(aPromotion());
        TestTransactions.commitAndRestart();
        var criteria = aQueryPromotionCriteria();
        // when
        var promotionViews = promotionQueryService.queryPromotions(criteria, 0, createTimes);
        // then
        assertThat(promotionViews, hasSize(createTimes));
        // should return the data window correctly when query with offset/limit
        for (int i = 0; i < 10; i++) {
            // given
            var offset = RANDOM.nextInt(createTimes);
            var limit = RANDOM.nextInt(createTimes - offset);
            // when
            var promotionViewsWindow = promotionQueryService.queryPromotions(criteria, offset, limit);
            // then
            assertThat(promotionViews.subList(offset, offset + limit), hasItems(promotionViewsWindow.toArray(new PromotionBriefView[limit])));
        }
    }
}
