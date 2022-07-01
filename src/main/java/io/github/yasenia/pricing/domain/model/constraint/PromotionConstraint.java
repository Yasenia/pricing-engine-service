package io.github.yasenia.pricing.domain.model.constraint;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.common.exception.DataDeserializeException;
import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @Type(value = AndConstraint.class, name = "AND"),
    @Type(value = OrConstraint.class, name = "OR"),
    @Type(value = MustIncludedProductsConstraint.class, name = "MUST_INCLUDED_PRODUCTS"),
    @Type(value = MustExcludedProductsConstraint.class, name = "MUST_EXCLUDED_PRODUCTS")
})
public interface PromotionConstraint {

    boolean isSatisfied(CalculationContext calculationContext);

    default JsonNode toJsonNode() {
        return new ObjectMapper().valueToTree(this);
    }

    static PromotionConstraint fromJsonNode(JsonNode jsonNode) {
        try {
            return new ObjectMapper().treeToValue(jsonNode, PromotionConstraint.class);
        } catch (JsonProcessingException e) {
            throw new DataDeserializeException(e, jsonNode, PromotionConstraint.class);
        }
    }
}
