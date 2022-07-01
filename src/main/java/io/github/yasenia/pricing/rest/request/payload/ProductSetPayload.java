package io.github.yasenia.pricing.rest.request.payload;

import io.github.yasenia.pricing.domain.model.product.IdAllowListProductSet;
import io.github.yasenia.pricing.domain.model.product.IdBlockListProductSet;
import io.github.yasenia.pricing.domain.model.product.ProductSet;

import java.util.List;

public record ProductSetPayload(
    ProductSetType type,
    List<String> allowedProductIds,
    List<String> blockedProductIds
) {
    public enum ProductSetType {
        ID_ALLOW_LIST, ID_BLOCK_LIST
    }

    public ProductSet toProductSet() {
        return switch (type) {
            case ID_ALLOW_LIST -> new IdAllowListProductSet(allowedProductIds);
            case ID_BLOCK_LIST -> new IdBlockListProductSet(blockedProductIds);
        };
    }
}
