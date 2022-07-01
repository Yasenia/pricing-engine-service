package io.github.yasenia.pricing.domain.model.product;

import java.util.List;

public record IdAllowListProductSet(
    List<String> allowedProductIds
) implements ProductSet {

    @Override
    public boolean include(String productId) {
        return allowedProductIds.contains(productId);
    }
}
