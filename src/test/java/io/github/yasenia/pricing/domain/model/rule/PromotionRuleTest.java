package io.github.yasenia.pricing.domain.model.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.common.exception.DataDeserializeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.yasenia.pricing.domain.model.PromotionRules.aPromotionRule;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PromotionRuleTest {

    @Test
    void should_deserialize_from_json_successfully_given_valid_json_data() {
        // given
        var originalValue = aPromotionRule();
        var json = new ObjectMapper().valueToTree(originalValue);
        // when
        var deserializedValue = Assertions.assertDoesNotThrow(() -> PromotionRule.fromJsonNode(json));
        // then
        assertThat(deserializedValue, equalTo(originalValue));
    }

    @Test
    void should_throw_system_exception_given_invalid_json_data() {
        // given
        var json = new ObjectMapper().missingNode();
        // then
        assertThrows(DataDeserializeException.class, () -> {
            // when
            PromotionRule.fromJsonNode(json);
        });
    }
}
