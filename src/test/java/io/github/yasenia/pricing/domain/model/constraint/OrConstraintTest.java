package io.github.yasenia.pricing.domain.model.constraint;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.yasenia.pricing.domain.model.CalculationContexts.aCalculationContext;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrConstraintTest {

    @Test
    void should_return_true_only_if_any_constraint_is_satisfied() {
        // given
        var transactionContext = aCalculationContext();
        var constraint1 = mock(PromotionConstraint.class);
        var constraint2 = mock(PromotionConstraint.class);
        var constraint3 = mock(PromotionConstraint.class);

        given(constraint1.isSatisfied(transactionContext)).willReturn(false);
        given(constraint2.isSatisfied(transactionContext)).willReturn(false);
        given(constraint3.isSatisfied(transactionContext)).willReturn(true);

        // when
        var constraint12 = new OrConstraint(List.of(constraint1, constraint2));
        var constraint13 = new OrConstraint(List.of(constraint1, constraint3));
        var constraint23 = new OrConstraint(List.of(constraint2, constraint3));
        var constraint123 = new OrConstraint(List.of(constraint1, constraint2, constraint3));

        // then
        assertFalse(constraint12.isSatisfied(transactionContext));
        assertTrue(constraint13.isSatisfied(transactionContext));
        assertTrue(constraint23.isSatisfied(transactionContext));
        assertTrue(constraint123.isSatisfied(transactionContext));
    }
}
