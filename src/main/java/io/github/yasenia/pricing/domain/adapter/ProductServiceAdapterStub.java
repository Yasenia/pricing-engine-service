package io.github.yasenia.pricing.domain.adapter;

import io.github.yasenia.pricing.domain.model.price.PriceTable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class ProductServiceAdapterStub implements ProductServiceAdapter {

    public static final Pattern PRICE_EXTRACT_PATTERN = Pattern.compile("^.*(\\d{6})$");

    @Override
    public PriceTable priceTableOf(Set<String> productIds) {
        return productId -> {
            var matcher = PRICE_EXTRACT_PATTERN.matcher(productId);
            return matcher.matches() ? Optional.of(new BigDecimal(matcher.group(0))) : Optional.empty();
        };
    }
}
