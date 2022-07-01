package io.github.yasenia.pricing.application.command;

import io.github.yasenia.pricing.domain.model.PromotionInfo;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;

public record CreatePromotionCommand(
    String creatorId,
    PromotionInfo info,
    PromotionConstraint constraint,
    PromotionRule rule
) {
}
