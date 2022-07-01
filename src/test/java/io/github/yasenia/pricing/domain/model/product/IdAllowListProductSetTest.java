package io.github.yasenia.pricing.domain.model.product;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static io.github.yasenia.pricing.common.Texts.aProductId;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdAllowListProductSetTest {

    @Test
    void should_include_allowed_product_id() {
        // given
        var allowedProductIds = IntStream.range(0, 10).mapToObj((i) -> aProductId()).toList();
        var productSet = new IdAllowListProductSet(allowedProductIds);
        // then
        allowedProductIds.forEach(allowedProductId -> assertTrue(productSet.include(allowedProductId)));
    }

    @RepeatedTest(5)
    void should_exclude_not_allowed_product_id() {
        // given
        var productSet = new IdAllowListProductSet(emptyList());
        // then
        assertFalse(productSet.include(aProductId()));
    }
}
