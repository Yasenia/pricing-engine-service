package io.github.yasenia.pricing.domain.service;

import io.github.yasenia.pricing.domain.adapter.ProductServiceAdapter;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;
import io.github.yasenia.pricing.domain.exception.PriceNotFetchedException;
import io.github.yasenia.pricing.domain.exception.UnsatisfiedPromotionConstraintException;
import io.github.yasenia.pricing.domain.model.Promotion;
import io.github.yasenia.pricing.domain.model.price.PriceTable;
import io.github.yasenia.pricing.domain.repository.PromotionRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static io.github.yasenia.pricing.common.Numbers.DELTA;
import static io.github.yasenia.pricing.common.Texts.aPromotionCode;
import static io.github.yasenia.pricing.domain.exception.NoSuchPromotionException.KeyType.CODE;
import static io.github.yasenia.pricing.domain.model.CalculationResults.aCalculationResult;
import static io.github.yasenia.pricing.domain.model.TransactionItems.newTransactionItem;
import static io.github.yasenia.pricing.domain.model.Transactions.newTransaction;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CalculationServiceTest {

    private final PromotionRepository promotionRepository = mock(PromotionRepository.class);
    private final ProductServiceAdapter productServiceAdapter = mock(ProductServiceAdapter.class);

    private final CalculationService calculationService = new CalculationService(promotionRepository, productServiceAdapter);

    @Test
    void should_return_calculation_context_when_calculate_transaction_given_transaction_without_any_promotions() {
        // given: set up price table
        var priceTable = mock(PriceTable.class);
        var item1 = newTransactionItem().withQuantity(2).build();
        var item2 = newTransactionItem().withQuantity(3).build();
        given(productServiceAdapter.priceTableOf(any())).willReturn(priceTable);
        given(priceTable.priceOf(item1.productId())).willReturn(Optional.of(ONE));
        given(priceTable.priceOf(item2.productId())).willReturn(Optional.of(TEN));
        // and given: set up transaction
        var transaction = newTransaction().withItem(item1).withItem(item2).build();
        // then
        var calculationContext = assertDoesNotThrow(() -> {
            // when
            return calculationService.calculateTransaction(transaction);
        });
        // and then
        assertThat(calculationContext, is(notNullValue()));
        assertThat(calculationContext.latestResult().totalPrice(), closeTo(BigDecimal.valueOf(32), DELTA));
    }

    @Test
    void should_throw_exception_when_calculate_transaction_given_transaction_with_a_no_priced_item() {
        // given: set up price table
        var priceTable = mock(PriceTable.class);
        var item1 = newTransactionItem().withQuantity(2).build();
        var item2 = newTransactionItem().withQuantity(3).build();
        given(productServiceAdapter.priceTableOf(any())).willReturn(priceTable);
        given(priceTable.priceOf(item1.productId())).willReturn(Optional.of(ONE));
        // and given: set up transaction
        var transaction = newTransaction().withItem(item1).withItem(item2).build();
        // then
        var exception = assertThrows(PriceNotFetchedException.class, () -> {
            // when
            calculationService.calculateTransaction(transaction);
        });
        // and then
        assertThat(exception.productId, is(item2.productId()));
    }

    @Test
    void should_return_calculation_context_when_calculate_transaction_given_transaction_with_a_promotion() {
        // given: set up price table
        var priceTable = mock(PriceTable.class);
        var item1 = newTransactionItem().withQuantity(2).build();
        var item2 = newTransactionItem().withQuantity(3).build();
        given(productServiceAdapter.priceTableOf(any())).willReturn(priceTable);
        given(priceTable.priceOf(item1.productId())).willReturn(Optional.of(ONE));
        given(priceTable.priceOf(item2.productId())).willReturn(Optional.of(TEN));
        // and given: set up promotions
        var promotionCode = aPromotionCode();
        var promotion = mock(Promotion.class);
        var calculationResult = aCalculationResult();
        given(promotionRepository.findByCode(promotionCode)).willReturn(Optional.of(promotion));
        given(promotion.isSatisfied(any())).willReturn(true);
        given(promotion.applyTo(any())).willReturn(calculationResult);
        // and given: set up transaction
        var transaction = newTransaction().withItem(item1).withItem(item2).withAppliedPromotionCode(promotionCode).build();
        // then
        var calculationContext = assertDoesNotThrow(() -> {
            // when
            return calculationService.calculateTransaction(transaction);
        });
        // and then
        assertThat(calculationContext.latestResult().totalPrice(), is(equalTo(calculationResult.totalPrice())));
    }

    @Test
    void should_throw_exception_when_calculate_transaction_given_transaction_with_a_not_existed_promotion() {
        // given: set up price table
        var priceTable = mock(PriceTable.class);
        var item1 = newTransactionItem().withQuantity(2).build();
        var item2 = newTransactionItem().withQuantity(3).build();
        given(productServiceAdapter.priceTableOf(any())).willReturn(priceTable);
        given(priceTable.priceOf(item1.productId())).willReturn(Optional.of(ONE));
        given(priceTable.priceOf(item2.productId())).willReturn(Optional.of(TEN));
        // and given: set up promotions
        var promotionCode = aPromotionCode();
        given(promotionRepository.findByCode(promotionCode)).willReturn(Optional.empty());
        // and given: set up transaction
        var transaction = newTransaction().withItem(item1).withItem(item2).withAppliedPromotionCode(promotionCode).build();
        // then
        var exception = assertThrows(NoSuchPromotionException.class, () -> {
            // when
            calculationService.calculateTransaction(transaction);
        });
        // and then
        assertThat(exception.keyType, is(CODE));
        assertThat(exception.key, is(promotionCode));
    }

    @Test
    void should_throw_exception_when_calculate_transaction_given_transaction_with_an_unsatisfied_promotion() {
        // given: set up price table
        var priceTable = mock(PriceTable.class);
        var item1 = newTransactionItem().withQuantity(2).build();
        var item2 = newTransactionItem().withQuantity(3).build();
        given(productServiceAdapter.priceTableOf(any())).willReturn(priceTable);
        given(priceTable.priceOf(item1.productId())).willReturn(Optional.of(ONE));
        given(priceTable.priceOf(item2.productId())).willReturn(Optional.of(TEN));
        // and given: set up promotions
        var promotionCode = aPromotionCode();
        var promotion = mock(Promotion.class);
        given(promotionRepository.findByCode(promotionCode)).willReturn(Optional.of(promotion));
        given(promotion.isSatisfied(any())).willReturn(false);
        given(promotion.getCode()).willReturn(promotionCode);
        // and given: set up transaction
        var transaction = newTransaction().withItem(item1).withItem(item2).withAppliedPromotionCode(promotionCode).build();
        // then
        var exception = assertThrows(UnsatisfiedPromotionConstraintException.class, () -> {
            // when
            calculationService.calculateTransaction(transaction);
        });
        // and then
        assertThat(exception.promotionCode, is(promotionCode));
    }
}
