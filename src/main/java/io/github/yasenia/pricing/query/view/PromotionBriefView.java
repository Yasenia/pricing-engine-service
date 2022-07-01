package io.github.yasenia.pricing.query.view;

import java.time.OffsetDateTime;

public record PromotionBriefView(
    String id,
    String code,
    String title,
    String description,
    String creatorId,
    OffsetDateTime createTime,
    String lastEditorId,
    OffsetDateTime lastEditTime
) {
}
