package io.github.yasenia.pricing.domain.repository;

import io.github.yasenia.testcore.JpaRepositoryTest;
import io.github.yasenia.testcore.TestTransactions;
import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.yasenia.pricing.domain.assertions.PromotionMatchers.sameAs;
import static io.github.yasenia.pricing.domain.assertions.PromotionMatchers.saved;
import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;
import static io.github.yasenia.pricing.domain.model.Promotions.aPromotion;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JpaRepositoryTest
class PromotionRepositoryTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @Test
    void should_generate_id_when_save_promotion_given_promotion_is_not_existed() {
        // given
        var promotion = aPromotion();
        // when
        var savedPromotion = promotionRepository.save(promotion);
        // then
        assertThat(savedPromotion, is(saved()));
        assertThat(savedPromotion, is(sameAs(promotion)));
    }

    @Test
    void should_save_updated_promotion_when_save_promotion_given_promotion_is_existed() throws InvalidPromotionInfoException {
        // given
        var promotion = aPromotion();
        var savedPromotion = promotionRepository.save(promotion);
        // when
        savedPromotion.editInfo(aPromotionId(), aPromotionInfo());
        var updatedPromotion = promotionRepository.save(savedPromotion);
        // then
        assertThat(updatedPromotion, is(sameAs(savedPromotion)));
    }

    @Test
    void should_find_promotion_successfully_when_find_promotion_by_id_given_promotion_is_existed() {
        // given
        var promotion = aPromotion();
        var savedPromotion = promotionRepository.save(promotion);
        TestTransactions.commitAndRestart();
        // when
        var foundPromotion = promotionRepository.findById(savedPromotion.getId()).orElseThrow(AssertionFailedError::new);
        // then
        assertThat(foundPromotion, is(sameAs(savedPromotion)));
    }

    @Test
    void should_remove_promotion_successfully_when_delete_promotion_given_promotion_is_existed() {
        // given
        var promotion = aPromotion();
        var savedPromotion = promotionRepository.save(promotion);
        TestTransactions.commitAndRestart();
        // when
        promotionRepository.delete(savedPromotion);
        TestTransactions.commitAndRestart();
        // then
        var exists = promotionRepository.existsById(savedPromotion.getId());
        assertFalse(exists);
    }

    @Test
    void should_find_promotion_successfully_when_find_promotion_by_code_given_promotion_is_existed() {
        // given
        var promotion = aPromotion();
        var savedPromotion = promotionRepository.save(promotion);
        TestTransactions.commitAndRestart();
        // when
        var foundPromotion = promotionRepository.findByCode(savedPromotion.getCode()).orElseThrow(AssertionFailedError::new);
        // then
        assertThat(foundPromotion, is(sameAs(savedPromotion)));
    }
}
