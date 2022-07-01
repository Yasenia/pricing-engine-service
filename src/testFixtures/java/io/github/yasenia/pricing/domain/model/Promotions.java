package io.github.yasenia.pricing.domain.model;

import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;

import static io.github.yasenia.pricing.common.Texts.aPromotionCode;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.domain.model.PromotionConstraints.aPromotionConstraint;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;
import static io.github.yasenia.pricing.domain.model.PromotionRules.aPromotionRule;

public class Promotions {

    private String creatorId;
    private PromotionInfo info;
    private PromotionConstraint constraint;
    private PromotionRule rule;

    private Promotions() {
    }

    public static Promotion aPromotion() {
        return newPromotion().build();
    }

    public static Promotions newPromotion() {
        return new Promotions()
            .withInfo(aPromotionInfo())
            .withCreatorId(aUserId())
            .withConstraint(aPromotionConstraint())
            .withRule(aPromotionRule())
            ;
    }

    public Promotions withCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Promotions withInfo(PromotionInfo info) {
        this.info = info;
        return this;
    }

    public Promotions withConstraint(PromotionConstraint constraint) {
        this.constraint = constraint;
        return this;
    }

    public Promotions withRule(PromotionRule rule) {
        this.rule = rule;
        return this;
    }

    public Promotion build() {
        try {
            return new Promotion(creatorId, aPromotionCode(), info, constraint, rule);
        } catch (InvalidPromotionInfoException unexpectedException) {
            throw new RuntimeException(unexpectedException);
        }
    }
}
