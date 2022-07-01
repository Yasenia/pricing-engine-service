package io.github.yasenia.pricing.query.view;

import io.github.yasenia.pricing.common.Texts;

import java.util.List;

public class ProductSetViews {

    public static ProductSetView aProductSetView() {
        return new ProductSetView(ProductSetView.ProductSetType.ID_ALLOW_LIST, List.of(Texts.aProductId()), null);
    }
}
