package io.github.yasenia.pricing.domain.model;

import static io.github.yasenia.pricing.common.Texts.aPromotionDescription;
import static io.github.yasenia.pricing.common.Texts.aPromotionTitle;

public class PromotionInfos {

    private String title;
    private String description;

    private PromotionInfos() {
    }

    public static PromotionInfo aPromotionInfo() {
        return newPromotionInfo().build();
    }

    public static PromotionInfos newPromotionInfo() {
        return new PromotionInfos()
            .withTitle(aPromotionTitle())
            .withDescription(aPromotionDescription());
    }

    public PromotionInfos withTitle(String title) {
        this.title = title;
        return this;
    }

    public PromotionInfos withDescription(String description) {
        this.description = description;
        return this;
    }

    public PromotionInfo build() {
        return new PromotionInfo(title, description);
    }
}
