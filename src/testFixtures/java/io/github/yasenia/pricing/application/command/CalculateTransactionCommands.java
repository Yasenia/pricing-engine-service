package io.github.yasenia.pricing.application.command;

import io.github.yasenia.pricing.domain.model.transaction.Transaction;

import static io.github.yasenia.pricing.domain.model.Transactions.aTransaction;

public class CalculateTransactionCommands {

    private Transaction transaction;

    public static CalculateTransactionCommand aCalculateTransactionCommand() {
        return newCalculateTransactionCommand().build();
    }

    public static CalculateTransactionCommands newCalculateTransactionCommand() {
        return new CalculateTransactionCommands().withTransaction(aTransaction());
    }

    public CalculateTransactionCommands withTransaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public CalculateTransactionCommand build() {
        return new CalculateTransactionCommand(this.transaction);
    }
}
