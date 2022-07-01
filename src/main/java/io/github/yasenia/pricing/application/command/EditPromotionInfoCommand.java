package io.github.yasenia.pricing.application.command;

import io.github.yasenia.pricing.domain.model.PromotionInfo;

public record EditPromotionInfoCommand(
    String editorId,
    String promotionId,
    PromotionInfo info
) {
}
