package io.github.yasenia.pricing.domain.adapter;

import io.github.yasenia.pricing.domain.model.price.PriceTable;

import java.util.Set;

public interface ProductServiceAdapter {

    PriceTable priceTableOf(Set<String> productIds);
}
