package io.github.yasenia.pricing.query.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ProductSetView(
    ProductSetType type,
    List<String> allowedProductIds,
    List<String> blockedProductIds
) {
    public enum ProductSetType {
        ID_ALLOW_LIST, ID_BLOCK_LIST
    }
}
