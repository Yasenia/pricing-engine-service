package io.github.yasenia.pricing.rest.request.payload;

import io.github.yasenia.pricing.domain.model.constraint.AndConstraint;
import io.github.yasenia.pricing.domain.model.constraint.MustExcludedProductsConstraint;
import io.github.yasenia.pricing.domain.model.constraint.MustIncludedProductsConstraint;
import io.github.yasenia.pricing.domain.model.constraint.OrConstraint;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import org.junit.jupiter.api.RepeatedTest;

import static io.github.yasenia.pricing.rest.Requests.aPromotionConstraintPayload;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class PromotionConstraintPayloadTest {

    @RepeatedTest(5)
    void should_convert_to_promotion_constraint_correctly() {
        var promotionConstraintPayload = aPromotionConstraintPayload();
        assertMatch(promotionConstraintPayload.toConstraint(), promotionConstraintPayload);
    }

    private void assertMatch(PromotionConstraint constraint, PromotionConstraintPayload constraintPayload) {
        switch (constraintPayload.type()) {
            case AND -> {
                if (!(constraint instanceof AndConstraint andConstraint)) throw new AssertionError();
                var subConstraintPayloads = constraintPayload.subConstraints();
                var subConstraints = andConstraint.subConstraints();
                assertThat(subConstraints, hasSize(subConstraintPayloads.size()));
                for (int i = 0; i < subConstraints.size(); i++) assertMatch(subConstraints.get(i), subConstraintPayloads.get(i));
            }
            case OR -> {
                if (!(constraint instanceof OrConstraint orConstraint)) throw new AssertionError();
                var subConstraintPayloads = constraintPayload.subConstraints();
                var subConstraints = orConstraint.subConstraints();
                assertThat(subConstraints, hasSize(subConstraintPayloads.size()));
                for (int i = 0; i < subConstraints.size(); i++) assertMatch(subConstraints.get(i), subConstraintPayloads.get(i));
            }
            case MUST_INCLUDED_PRODUCTS -> {
                if (!(constraint instanceof MustIncludedProductsConstraint mustIncludedProductsConstraint)) throw new AssertionError();
                assertThat(constraintPayload.mustIncludedProductSet().toProductSet(), equalTo(mustIncludedProductsConstraint.mustIncludedProductSet()));
            }
            case MUST_EXCLUDED_PRODUCTS -> {
                if (!(constraint instanceof MustExcludedProductsConstraint mustIncludedProductsConstraint)) throw new AssertionError();
                assertThat(constraintPayload.mustExcludedProductSet().toProductSet(), equalTo(mustIncludedProductsConstraint.mustExcludedProductSet()));
            }
            default -> { }
        }
    }
}
