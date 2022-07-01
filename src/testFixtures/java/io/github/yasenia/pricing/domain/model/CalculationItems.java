package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.common.Randoms;
import io.github.yasenia.pricing.domain.model.calculation.CalculationItem;

import java.math.BigDecimal;

import static io.github.yasenia.pricing.common.Texts.aProductId;

public class CalculationItems {

    private String productId;
    private int quantity;
    private BigDecimal subtotalPrice;

    public static CalculationItem aCalculationItem() {
        return newCalculationItem().build();
    }

    public static CalculationItems newCalculationItem() {
        return new CalculationItems()
            .withProductId(aProductId())
            .withQuantity(Randoms.RANDOM.nextInt(10) + 1)
            .withSubtotalPrice(BigDecimal.valueOf(Randoms.RANDOM.nextDouble(100)));
    }

    public CalculationItems withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public CalculationItems withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CalculationItems withSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
        return this;
    }

    public CalculationItem build() {
        return new CalculationItem(this.productId, this.quantity, this.subtotalPrice);
    }
}
