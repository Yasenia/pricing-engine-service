package io.github.yasenia.pricing.query.view;

import static io.github.yasenia.pricing.query.view.ProductSetViews.aProductSetView;
import static io.github.yasenia.pricing.query.view.PromotionConstraintView.ConstraintType.MUST_EXCLUDED_PRODUCTS;

public class PromotionConstraintViews {

    public static PromotionConstraintView aPromotionConstraintView() {
        return new PromotionConstraintView(MUST_EXCLUDED_PRODUCTS, null, aProductSetView(), null);
    }
}
