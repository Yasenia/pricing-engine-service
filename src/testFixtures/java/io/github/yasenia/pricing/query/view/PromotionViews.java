package io.github.yasenia.pricing.query.view;

import static io.github.yasenia.pricing.common.Texts.aPromotionCode;
import static io.github.yasenia.pricing.common.Texts.aPromotionDescription;
import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.common.Texts.aPromotionTitle;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.query.view.PromotionConstraintViews.aPromotionConstraintView;
import static io.github.yasenia.pricing.query.view.PromotionRuleViews.aPromotionRuleView;
import static java.time.OffsetDateTime.now;

public class PromotionViews {

    private String id;

    public static PromotionViews newPromotionView() {
        return new PromotionViews()
            .withId(aPromotionId());
    }

    public PromotionView build() {
        return new PromotionView(
            this.id,
            aPromotionCode(),
            aPromotionTitle(),
            aPromotionDescription(),
            aPromotionConstraintView(),
            aPromotionRuleView(),
            aUserId(),
            now(),
            aUserId(),
            now()
        );
    }

    public PromotionViews withId(String id) {
        this.id = id;
        return this;
    }
}
