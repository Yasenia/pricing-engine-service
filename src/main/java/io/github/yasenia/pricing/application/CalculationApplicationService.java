package io.github.yasenia.pricing.application;

import io.github.yasenia.pricing.application.result.TransactionCalculatedResult;
import io.github.yasenia.pricing.application.command.CalculateTransactionCommand;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;
import io.github.yasenia.pricing.domain.exception.PriceNotFetchedException;
import io.github.yasenia.pricing.domain.exception.UnsatisfiedPromotionConstraintException;

public interface CalculationApplicationService {

    TransactionCalculatedResult calculateTransaction(CalculateTransactionCommand command)
            throws PriceNotFetchedException, NoSuchPromotionException, UnsatisfiedPromotionConstraintException;
}
