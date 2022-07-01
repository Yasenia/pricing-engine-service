package io.github.yasenia.pricing.query.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.common.exception.DataDeserializeException;
import org.jooq.JSONB;
import org.junit.jupiter.api.Test;

import static io.github.yasenia.pricing.common.Randoms.aRandomText;
import static io.github.yasenia.pricing.query.view.PromotionRuleViews.aPromotionRuleView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PromotionRuleViewTest {

    @Test
    void should_deserialize_from_json_successfully_given_valid_json_data() throws JsonProcessingException {
        // given
        var originalView = aPromotionRuleView();
        var jsonb = mock(JSONB.class);
        given(jsonb.data()).willReturn(new ObjectMapper().writeValueAsString(originalView));
        // when
        var deserializedView = assertDoesNotThrow(() -> PromotionRuleView.fromJsonb(jsonb));
        // then
        assertThat(deserializedView, equalTo(originalView));
    }

    @Test
    void should_throw_system_exception_given_invalid_json_data() {
        // given
        var jsonb = mock(JSONB.class);
        given(jsonb.data()).willReturn(aRandomText(10));
        // then
        assertThrows(DataDeserializeException.class, () -> {
            // when
            PromotionRuleView.fromJsonb(jsonb);
        });
    }
}
