package io.github.yasenia.pricing.rest;

import io.github.yasenia.pricing.common.Randoms;
import io.github.yasenia.pricing.rest.request.CreatePromotionRequest;
import io.github.yasenia.pricing.rest.request.DeletePromotionRequest;
import io.github.yasenia.pricing.rest.request.EditPromotionInfoRequest;
import io.github.yasenia.pricing.rest.request.payload.ProductSetPayload;
import io.github.yasenia.pricing.rest.request.payload.ProductSetPayload.ProductSetType;
import io.github.yasenia.pricing.rest.request.payload.PromotionConstraintPayload;
import io.github.yasenia.pricing.rest.request.payload.PromotionConstraintPayload.ConstraintType;
import io.github.yasenia.pricing.rest.request.payload.PromotionRulePayload;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.yasenia.pricing.common.Texts.aProductId;
import static io.github.yasenia.pricing.common.Texts.aPromotionDescription;
import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.common.Texts.aPromotionTitle;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.rest.request.payload.PromotionRulePayload.RuleType.FIXED_AMOUNT_DISCOUNT;

public class Requests {

    public static ProductSetPayload aProductSetPayload() {
        var productSetType = ProductSetType.values()[Randoms.RANDOM.nextInt(ProductSetType.values().length)];
        return switch (productSetType) {
            case ID_ALLOW_LIST -> new ProductSetPayload(productSetType, IntStream.range(0, 10).mapToObj(i -> aProductId()).toList(), null);
            case ID_BLOCK_LIST -> new ProductSetPayload(productSetType, null, IntStream.range(0, 10).mapToObj(i -> aProductId()).toList());
        };
    }

    public static PromotionConstraintPayload aPromotionConstraintPayload() {
        var constraintType = ConstraintType.values()[Randoms.RANDOM.nextInt(ConstraintType.values().length)];
        return switch (constraintType) {
            case AND, OR -> new PromotionConstraintPayload(constraintType, List.of(aPromotionConstraintPayload(), aPromotionConstraintPayload()), null, null);
            case MUST_INCLUDED_PRODUCTS -> new PromotionConstraintPayload(constraintType, null, aProductSetPayload(), null);
            case MUST_EXCLUDED_PRODUCTS -> new PromotionConstraintPayload(constraintType, null, null, aProductSetPayload());
        };
    }

    public static PromotionRulePayload aPromotionRulePayload() {
        return new PromotionRulePayload(FIXED_AMOUNT_DISCOUNT, BigDecimal.ONE);
    }

    public static CreatePromotionRequest aCreatePromotionRequest() {
        return new CreatePromotionRequest(
            aUserId(),
            aPromotionTitle(),
            aPromotionDescription(),
            aPromotionConstraintPayload(),
            aPromotionRulePayload()
        );
    }

    public static EditPromotionInfoRequest anEditPromotionInfoRequest() {
        return new EditPromotionInfoRequest(
            aUserId(),
            aPromotionId(),
            aPromotionTitle(),
            aPromotionDescription()
        );
    }

    public static DeletePromotionRequest aDeletePromotionRequest() {
        return new DeletePromotionRequest(
            aPromotionId()
        );
    }
}

