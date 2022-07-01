package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.common.Texts;
import io.github.yasenia.pricing.domain.model.transaction.Transaction;
import io.github.yasenia.pricing.domain.model.transaction.TransactionItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.github.yasenia.pricing.common.Generators.aListOf;
import static io.github.yasenia.pricing.common.Generators.aNotEmptyListOf;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

public class Transactions {

    private final List<TransactionItem> items = new ArrayList<>();
    private final Set<String> appliedPromotionCodes = new HashSet<>();

    public static Transaction aTransaction() {
        return new Transactions()
            .withItems(aNotEmptyListOf(TransactionItems::aTransactionItem))
            .withAppliedPromotionCodes(aListOf(Texts::aPromotionCode))
            .build();
    }

    public static Transactions newTransaction() {
        return new Transactions();
    }

    public Transactions withItem(TransactionItem item) {
        this.items.add(item);
        return this;
    }

    public Transactions withItems(List<TransactionItem> items) {
        this.items.addAll(items);
        return this;
    }

    public Transactions withAppliedPromotionCode(String promotionCode) {
        this.appliedPromotionCodes.add(promotionCode);
        return this;
    }

    public Transactions withAppliedPromotionCodes(List<String> promotionCodes) {
        this.appliedPromotionCodes.addAll(promotionCodes);
        return this;
    }

    public Transaction build() {
        return new Transaction(unmodifiableList(this.items), unmodifiableSet(this.appliedPromotionCodes));
    }
}
