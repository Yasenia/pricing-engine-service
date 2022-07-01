package io.github.yasenia.pricing.application.command;

import io.github.yasenia.pricing.domain.model.transaction.Transaction;

public record CalculateTransactionCommand(
    Transaction transaction
) {
}
