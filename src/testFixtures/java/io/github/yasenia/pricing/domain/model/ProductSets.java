package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.model.product.IdAllowListProductSet;
import io.github.yasenia.pricing.domain.model.product.IdBlockListProductSet;
import io.github.yasenia.pricing.domain.model.product.ProductSet;

import java.util.stream.IntStream;

import static io.github.yasenia.pricing.common.Randoms.RANDOM;
import static io.github.yasenia.pricing.common.Texts.aProductId;

public class ProductSets {

    public static ProductSet aProductSet() {
        return RANDOM.nextBoolean() ? aIdAllowListProductSet() : aIdBlockListProductSet();
    }

    public static IdAllowListProductSet aIdAllowListProductSet() {
        return new IdAllowListProductSet(
            IntStream.range(0, RANDOM.nextInt(10)).mapToObj(i -> aProductId()).toList()
        );
    }

    public static IdBlockListProductSet aIdBlockListProductSet() {
        return new IdBlockListProductSet(
            IntStream.range(0, RANDOM.nextInt(10)).mapToObj(i -> aProductId()).toList()
        );
    }
}
