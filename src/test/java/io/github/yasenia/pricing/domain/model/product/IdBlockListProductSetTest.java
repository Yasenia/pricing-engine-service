package io.github.yasenia.pricing.domain.model.product;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static io.github.yasenia.pricing.common.Texts.aProductId;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdBlockListProductSetTest {

    @Test
    void should_exclude_blocked_product_id() {
        // given
        var blockedProductIds = IntStream.range(0, 10).mapToObj((i) -> aProductId()).toList();
        var productSet = new IdBlockListProductSet(blockedProductIds);
        // then
        blockedProductIds.forEach(allowedProductId -> assertFalse(productSet.include(allowedProductId)));
    }

    @RepeatedTest(5)
    void should_include_not_blocked_product_id() {
        // given
        var productSet = new IdBlockListProductSet(emptyList());
        // then
        assertTrue(productSet.include(aProductId()));
    }
}
