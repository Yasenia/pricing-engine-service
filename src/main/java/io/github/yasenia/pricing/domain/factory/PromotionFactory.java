package io.github.yasenia.pricing.domain.factory;

import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.model.Promotion;
import io.github.yasenia.pricing.domain.model.PromotionInfo;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PromotionFactory {

    public static final int PROMOTION_CODE_LENGTH = 8;

    private static final Random RANDOM = new Random();
    private static final String FIRST_CHARS = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Promotion createPromotion(
        String creatorId, PromotionInfo info, PromotionConstraint constraint, PromotionRule rule
    ) throws InvalidPromotionInfoException {
        var promotionCode = generatePromotionCode();
        return new Promotion(creatorId, promotionCode, info, constraint, rule);
    }

    private String generatePromotionCode() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(randomCharIn(FIRST_CHARS));
        for (int i = 0; i < PROMOTION_CODE_LENGTH - 1; i++) stringBuilder.append(randomCharIn(CHARS));
        return stringBuilder.toString();
    }

    private char randomCharIn(String str) {
        return str.charAt(RANDOM.nextInt(str.length()));
    }
}
