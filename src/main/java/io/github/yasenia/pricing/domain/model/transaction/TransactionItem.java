package io.github.yasenia.pricing.domain.model.transaction;

public record TransactionItem(
    String productId,
    int quantity
) {
}
