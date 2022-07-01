package io.github.yasenia.pricing.query.view;

import static io.github.yasenia.pricing.common.Texts.aPromotionCode;
import static io.github.yasenia.pricing.common.Texts.aPromotionDescription;
import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.common.Texts.aPromotionTitle;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.core.tool.Times.currentTime;

public class PromotionBriefViews {

    public static PromotionBriefView aPromotionBriefView() {
        return new PromotionBriefView(
            aPromotionId(),
            aPromotionCode(),
            aPromotionTitle(),
            aPromotionDescription(),
            aUserId(),
            currentTime(),
            aUserId(),
            currentTime()
        );
    }
}
