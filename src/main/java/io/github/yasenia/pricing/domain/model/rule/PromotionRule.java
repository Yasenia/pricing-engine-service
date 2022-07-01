package io.github.yasenia.pricing.domain.model.rule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.common.exception.DataDeserializeException;
import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;
import io.github.yasenia.pricing.domain.model.calculation.CalculationResult;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @Type(value = FixedAmountDiscountRule.class, name = "FIXED_AMOUNT_DISCOUNT")
})
public interface PromotionRule {

    CalculationResult applyTo(CalculationContext calculationContext);

    default JsonNode toJson() {
        return new ObjectMapper().valueToTree(this);
    }

    static PromotionRule fromJsonNode(JsonNode jsonNode) {
        try {
            return new ObjectMapper().treeToValue(jsonNode, PromotionRule.class);
        } catch (JsonProcessingException e) {
            throw new DataDeserializeException(e, jsonNode, PromotionRule.class);
        }
    }
}
