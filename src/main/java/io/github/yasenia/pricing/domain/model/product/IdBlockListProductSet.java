package io.github.yasenia.pricing.domain.model.product;

import java.util.List;

public record IdBlockListProductSet(
    List<String> blockedProductIds
) implements ProductSet {

    @Override
    public boolean include(String productId) {
        return !blockedProductIds.contains(productId);
    }
}
