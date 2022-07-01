package io.github.yasenia.pricing.application;

import io.github.yasenia.pricing.application.result.TransactionCalculatedResult;
import io.github.yasenia.pricing.application.command.CalculateTransactionCommand;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;
import io.github.yasenia.pricing.domain.exception.PriceNotFetchedException;
import io.github.yasenia.pricing.domain.exception.UnsatisfiedPromotionConstraintException;
import io.github.yasenia.pricing.domain.service.CalculationService;
import org.springframework.stereotype.Service;

@Service
public class CalculationApplicationServiceImpl implements CalculationApplicationService {

    private final CalculationService calculationService;

    public CalculationApplicationServiceImpl(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Override
    public TransactionCalculatedResult calculateTransaction(CalculateTransactionCommand command)
            throws PriceNotFetchedException, NoSuchPromotionException, UnsatisfiedPromotionConstraintException {
        var calculationContext = calculationService.calculateTransaction(command.transaction());
        return new TransactionCalculatedResult();
    }
}
