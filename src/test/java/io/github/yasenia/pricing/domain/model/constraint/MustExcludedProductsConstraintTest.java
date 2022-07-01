package io.github.yasenia.pricing.domain.model.constraint;

import io.github.yasenia.pricing.domain.model.CalculationItems;
import io.github.yasenia.pricing.domain.model.product.ProductSet;
import org.junit.jupiter.api.Test;

import static io.github.yasenia.pricing.common.Generators.aListOf;
import static io.github.yasenia.pricing.domain.model.CalculationContexts.newCalculationContext;
import static io.github.yasenia.pricing.domain.model.CalculationItems.aCalculationItem;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MustExcludedProductsConstraintTest {

    @Test
    void should_return_true_only_if_transaction_not_include_must_excluded_products() {
        // given
        var itemInSet = aCalculationItem();
        var itemsNotInSet = aListOf(CalculationItems::aCalculationItem);
        var productSet = mock(ProductSet.class);
        given(productSet.include(any())).willReturn(false);
        given(productSet.include(itemInSet.productId())).willReturn(true);
        var satisfiedTransactionContext = newCalculationContext()
            .withInitialItems(itemsNotInSet)
            .build();
        var unsatisfiedTransactionContext = newCalculationContext()
            .withInitialItems(itemsNotInSet)
            .withInitialItem(itemInSet)
            .build();
        var constraint = new MustExcludedProductsConstraint(productSet);
        // then
        assertTrue(constraint.isSatisfied(satisfiedTransactionContext));
        assertFalse(constraint.isSatisfied(unsatisfiedTransactionContext));
    }
}
