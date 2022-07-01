package io.github.yasenia.pricing.domain.assertions;

import io.github.yasenia.pricing.domain.model.Promotion;
import org.hamcrest.CustomMatcher;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class PromotionMatchers {

    public static CustomMatcher<Promotion> saved() {
        return new CustomMatcher<>("persisted") {
            @Override
            public boolean matches(Object actual) {
                return actual instanceof Promotion actualPromotion && nonNull(actualPromotion.getId());
            }
        };
    }

    public static CustomMatcher<Promotion> sameAs(Promotion targetPromotion) {
        return new CustomMatcher<>("same as <%s>".formatted(targetPromotion)) {
            @Override
            public boolean matches(Object actual) {
                if (!(actual instanceof Promotion actualPromotion)) return false;
                return Objects.equals(targetPromotion.getId(), actualPromotion.getId())
                    && Objects.equals(targetPromotion.getInfo(), actualPromotion.getInfo())
                    && Objects.equals(targetPromotion.getConstraint(), actualPromotion.getConstraint())
                    && Objects.equals(targetPromotion.getRule(), actualPromotion.getRule())
                    && Objects.equals(targetPromotion.getCreatorId(), actualPromotion.getCreatorId())
                    && Objects.equals(targetPromotion.getCreateTime(), actualPromotion.getCreateTime())
                    && Objects.equals(targetPromotion.getLastEditorId(), actualPromotion.getLastEditorId())
                    && Objects.equals(targetPromotion.getLastEditTime(), actualPromotion.getLastEditTime())
                    ;
            }
        };
    }
}
