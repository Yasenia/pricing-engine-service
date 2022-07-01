package io.github.yasenia.pricing.rest.request.payload;

import io.github.yasenia.pricing.domain.model.constraint.AndConstraint;
import io.github.yasenia.pricing.domain.model.constraint.MustExcludedProductsConstraint;
import io.github.yasenia.pricing.domain.model.constraint.MustIncludedProductsConstraint;
import io.github.yasenia.pricing.domain.model.constraint.OrConstraint;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;

import java.util.List;

public record PromotionConstraintPayload(
    ConstraintType type,
    List<PromotionConstraintPayload> subConstraints,
    ProductSetPayload mustIncludedProductSet,
    ProductSetPayload mustExcludedProductSet
) {
    public enum ConstraintType {
        AND, OR, MUST_INCLUDED_PRODUCTS, MUST_EXCLUDED_PRODUCTS
    }

    public PromotionConstraint toConstraint() {
        return switch (type) {
            case AND -> new AndConstraint(subConstraints.stream().map(PromotionConstraintPayload::toConstraint).toList());
            case OR -> new OrConstraint(subConstraints.stream().map(PromotionConstraintPayload::toConstraint).toList());
            case MUST_INCLUDED_PRODUCTS -> new MustIncludedProductsConstraint(mustIncludedProductSet.toProductSet());
            case MUST_EXCLUDED_PRODUCTS -> new MustExcludedProductsConstraint(mustExcludedProductSet.toProductSet());
        };
    }
}
