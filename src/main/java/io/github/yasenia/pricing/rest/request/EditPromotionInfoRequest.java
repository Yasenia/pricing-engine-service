package io.github.yasenia.pricing.rest.request;

public record EditPromotionInfoRequest(
    String editorId,
    String promotionId,
    String title,
    String description
) {
}
