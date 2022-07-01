package io.github.yasenia.pricing.query.view;

import java.time.OffsetDateTime;

public record PromotionView(
    String id,
    String code,
    String title,
    String description,
    PromotionConstraintView constraint,
    PromotionRuleView rule,
    String creatorId,
    OffsetDateTime createTime,
    String lastEditorId,
    OffsetDateTime lastEditTime
) {
}
