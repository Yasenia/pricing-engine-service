package io.github.yasenia.pricing.domain.service;

import io.github.yasenia.pricing.domain.adapter.ProductServiceAdapter;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;
import io.github.yasenia.pricing.domain.exception.PriceNotFetchedException;
import io.github.yasenia.pricing.domain.exception.UnsatisfiedPromotionConstraintException;
import io.github.yasenia.pricing.domain.model.Promotion;
import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;
import io.github.yasenia.pricing.domain.model.calculation.CalculationItem;
import io.github.yasenia.pricing.domain.model.transaction.Transaction;
import io.github.yasenia.pricing.domain.model.transaction.TransactionItem;
import io.github.yasenia.pricing.domain.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static io.github.yasenia.core.tool.Exceptions.rethrowFunction;
import static java.util.stream.Collectors.toSet;

@Service
public class CalculationService {

    private final PromotionRepository promotionRepository;
    private final ProductServiceAdapter productServiceAdapter;

    public CalculationService(PromotionRepository promotionRepository, ProductServiceAdapter productServiceAdapter) {
        this.promotionRepository = promotionRepository;
        this.productServiceAdapter = productServiceAdapter;
    }

    public CalculationContext calculateTransaction(Transaction transaction)
            throws PriceNotFetchedException, NoSuchPromotionException, UnsatisfiedPromotionConstraintException {
        var initialContext = setUpInitialCalculationContext(transaction);
        var promotions = selectPromotions(transaction);
        return initialContext.applyPromotions(promotions);
    }

    private CalculationContext setUpInitialCalculationContext(Transaction transaction) throws PriceNotFetchedException {
        var productIds = transaction.items().stream().map(TransactionItem::productId).collect(toSet());
        var priceTable = productServiceAdapter.priceTableOf(productIds);
        return new CalculationContext(
            transaction.items().stream().map(rethrowFunction(transactionItem -> new CalculationItem(
                transactionItem.productId(),
                transactionItem.quantity(),
                priceTable
                    .priceOf(transactionItem.productId())
                    .orElseThrow(() -> new PriceNotFetchedException(transactionItem.productId()))
                    .multiply(BigDecimal.valueOf(transactionItem.quantity()))
            ))).toList()
        );
    }

    private List<Promotion> selectPromotions(Transaction transaction) throws NoSuchPromotionException {
        return transaction.appliedPromotionCodes().stream().map(rethrowFunction(code ->
            promotionRepository.findByCode(code).orElseThrow(() -> NoSuchPromotionException.ofCode(code))
        )).toList();
    }
}
