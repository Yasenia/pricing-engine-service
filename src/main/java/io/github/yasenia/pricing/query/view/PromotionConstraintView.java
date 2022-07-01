package io.github.yasenia.pricing.query.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.common.exception.DataDeserializeException;
import org.jooq.JSONB;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record PromotionConstraintView(
    ConstraintType type,
    List<PromotionConstraintView> subConstraints,
    ProductSetView mustIncludedProductSet,
    ProductSetView mustExcludedProductSet
) {
    public enum ConstraintType {
        AND, OR, MUST_INCLUDED_PRODUCTS, MUST_EXCLUDED_PRODUCTS
    }

    public static PromotionConstraintView fromJsonb(JSONB jsonb) {
        try {
            return new ObjectMapper().readValue(jsonb.data(), PromotionConstraintView.class);
        } catch (JsonProcessingException e) {
            throw new DataDeserializeException(e, jsonb, PromotionConstraintView.class);
        }
    }
}
