package io.github.yasenia.pricing.query.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.common.exception.DataDeserializeException;
import org.jooq.JSONB;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record PromotionRuleView(
    RuleType type,
    BigDecimal discountAmount
) {

    public enum RuleType {
        FIXED_AMOUNT_DISCOUNT
    }

    public static PromotionRuleView fromJsonb(JSONB jsonb) {
        try {
            return new ObjectMapper().readValue(jsonb.data(), PromotionRuleView.class);
        } catch (JsonProcessingException e) {
            throw new DataDeserializeException(e, jsonb, PromotionRuleView.class);
        }
    }
}
