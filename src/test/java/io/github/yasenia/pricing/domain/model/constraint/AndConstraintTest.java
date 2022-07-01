package io.github.yasenia.pricing.domain.model.constraint;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.yasenia.pricing.domain.model.CalculationContexts.aCalculationContext;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AndConstraintTest {

    @Test
    void should_return_true_only_if_all_constraints_are_satisfied() {
        // given
        var transactionContext = aCalculationContext();
        var constraint1 = mock(PromotionConstraint.class);
        var constraint2 = mock(PromotionConstraint.class);
        var constraint3 = mock(PromotionConstraint.class);

        given(constraint1.isSatisfied(transactionContext)).willReturn(true);
        given(constraint2.isSatisfied(transactionContext)).willReturn(true);
        given(constraint3.isSatisfied(transactionContext)).willReturn(false);

        // when
        var constraint12 = new AndConstraint(List.of(constraint1, constraint2));
        var constraint13 = new AndConstraint(List.of(constraint1, constraint3));
        var constraint23 = new AndConstraint(List.of(constraint2, constraint3));
        var constraint123 = new AndConstraint(List.of(constraint1, constraint2, constraint3));

        // then
        assertTrue(constraint12.isSatisfied(transactionContext));
        assertFalse(constraint13.isSatisfied(transactionContext));
        assertFalse(constraint23.isSatisfied(transactionContext));
        assertFalse(constraint123.isSatisfied(transactionContext));
    }
}
