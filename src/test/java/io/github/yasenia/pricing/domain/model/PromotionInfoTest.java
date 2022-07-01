package io.github.yasenia.pricing.domain.model;

import org.junit.jupiter.api.Test;

import static io.github.yasenia.pricing.common.Randoms.aRandomText;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.newPromotionInfo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PromotionInfoTest {

    @Test
    void should_be_valid_promotion_info() {
        var info = aPromotionInfo();
        assertTrue(info.isValid());
    }

    @Test
    void should_be_valid_promotion_info_given_description_is_null() {
        var info = newPromotionInfo().withDescription(null).build();
        assertTrue(info.isValid());
    }

    @Test
    void should_be_invalid_promotion_info_given_title_is_null() {
        var info = newPromotionInfo().withTitle(null).build();
        assertFalse(info.isValid());
    }

    @Test
    void should_be_invalid_promotion_info_given_title_is_empty() {
        var info = newPromotionInfo().withTitle("").build();
        assertFalse(info.isValid());
    }

    @Test
    void should_be_invalid_promotion_info_given_title_length_exceed_100() {
        var info = newPromotionInfo().withTitle(aRandomText(101)).build();
        assertFalse(info.isValid());
    }

    @Test
    void should_be_invalid_promotion_info_given_description_length_exceed_500() {
        var info = newPromotionInfo().withDescription(aRandomText(501)).build();
        assertFalse(info.isValid());
    }
}
