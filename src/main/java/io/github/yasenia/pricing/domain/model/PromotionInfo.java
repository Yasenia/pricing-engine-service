package io.github.yasenia.pricing.domain.model;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public record PromotionInfo(
    String title,
    String description
) {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isValid() {
        return isTitleValid() && isDescriptionValid();
    }

    private boolean isTitleValid() {
        return nonNull(title) && title.length() > 0 && title.length() <= 100;
    }

    private boolean isDescriptionValid() {
        return isNull(description) || description.length() <= 500;
    }
}
