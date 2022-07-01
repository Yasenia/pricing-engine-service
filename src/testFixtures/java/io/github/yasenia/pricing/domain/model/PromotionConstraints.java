package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.model.constraint.MustIncludedProductsConstraint;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;

import static io.github.yasenia.pricing.domain.model.ProductSets.aProductSet;

public class PromotionConstraints {

    public static PromotionConstraint aPromotionConstraint() {
        return new MustIncludedProductsConstraint(aProductSet());
    }
}
