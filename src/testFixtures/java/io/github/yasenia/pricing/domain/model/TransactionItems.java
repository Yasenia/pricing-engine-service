package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.common.Randoms;
import io.github.yasenia.pricing.domain.model.transaction.TransactionItem;

import static io.github.yasenia.pricing.common.Texts.aProductId;

public class TransactionItems {

    private String productId;
    private int quantity;

    public static TransactionItem aTransactionItem() {
        return newTransactionItem().build();
    }

    public static TransactionItems newTransactionItem() {
        return new TransactionItems()
            .withProductId(aProductId())
            .withQuantity(Randoms.RANDOM.nextInt(10));
    }

    public TransactionItems withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public TransactionItems withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public TransactionItem build() {
        return new TransactionItem(productId, quantity);
    }
}
