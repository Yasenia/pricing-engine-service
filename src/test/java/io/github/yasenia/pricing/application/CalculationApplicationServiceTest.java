package io.github.yasenia.pricing.application;

import io.github.yasenia.pricing.domain.model.transaction.Transaction;
import io.github.yasenia.pricing.domain.service.CalculationService;
import io.github.yasenia.testcore.ApplicationServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static io.github.yasenia.pricing.application.command.CalculateTransactionCommands.newCalculateTransactionCommand;
import static io.github.yasenia.pricing.domain.model.CalculationContexts.aCalculationContext;
import static io.github.yasenia.pricing.domain.model.Transactions.aTransaction;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ApplicationServiceTest
class CalculationApplicationServiceTest {

    @Autowired
    private CalculationApplicationService calculationApplicationService;

    @MockBean
    private CalculationService calculationService;

    @Test
    void should_return_calculated_result_when_calculate_transaction() throws Exception {
        // given
        var calculationContext = aCalculationContext();
        var transaction = aTransaction();
        given(calculationService.calculateTransaction(any())).willReturn(calculationContext);
        var command = newCalculateTransactionCommand().withTransaction(transaction).build();
        // then
        var result = assertDoesNotThrow(() -> {
            // when
            return calculationApplicationService.calculateTransaction(command);
        });
        // and then
        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(calculationService, only()).calculateTransaction(transactionCaptor.capture());
        var capturedTransaction = transactionCaptor.getValue();
        assertThat(capturedTransaction, equalTo(transaction));
        // and then
        assertThat(result, is(notNullValue()));
    }
}
