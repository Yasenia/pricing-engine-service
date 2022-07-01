package io.github.yasenia.pricing.domain.model.price;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceTable {

    Optional<BigDecimal> priceOf(String productId);
}
