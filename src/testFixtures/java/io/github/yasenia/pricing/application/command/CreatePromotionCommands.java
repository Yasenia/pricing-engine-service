package io.github.yasenia.pricing.application.command;

import io.github.yasenia.pricing.domain.model.PromotionInfo;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;

import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.domain.model.PromotionConstraints.aPromotionConstraint;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;
import static io.github.yasenia.pricing.domain.model.PromotionRules.aPromotionRule;

public class CreatePromotionCommands {

    private String creatorId;
    private PromotionInfo info;
    private PromotionConstraint constraint;
    private PromotionRule rule;

    public static CreatePromotionCommand aCreatePromotionCommand() {
        return newCreatePromotionCommand()
            .withCreatorId(aUserId())
            .withInfo(aPromotionInfo())
            .withConstraint(aPromotionConstraint())
            .withRule(aPromotionRule())
            .build();
    }

    public static CreatePromotionCommands newCreatePromotionCommand() {
        return new CreatePromotionCommands();
    }

    public CreatePromotionCommands withCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public CreatePromotionCommands withInfo(PromotionInfo info) {
        this.info = info;
        return this;
    }

    public CreatePromotionCommands withConstraint(PromotionConstraint constraint) {
        this.constraint = constraint;
        return this;
    }

    public CreatePromotionCommands withRule(PromotionRule rule) {
        this.rule = rule;
        return this;
    }

    public CreatePromotionCommand build() {
        return new CreatePromotionCommand(
            this.creatorId,
            this.info,
            this.constraint,
            this.rule
        );
    }
}
