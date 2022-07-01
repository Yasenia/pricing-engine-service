package io.github.yasenia.pricing.rest.request;

import io.github.yasenia.pricing.rest.request.payload.PromotionConstraintPayload;
import io.github.yasenia.pricing.rest.request.payload.PromotionRulePayload;

public record CreatePromotionRequest(
    String creatorId,
    String title,
    String description,
    PromotionConstraintPayload constraint,
    PromotionRulePayload rule
) {
}
