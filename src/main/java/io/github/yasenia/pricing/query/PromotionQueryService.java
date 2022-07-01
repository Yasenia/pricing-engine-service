package io.github.yasenia.pricing.query;

import io.github.yasenia.pricing.query.criteria.QueryPromotionCriteria;
import io.github.yasenia.pricing.query.exception.PromotionNotFoundException;
import io.github.yasenia.pricing.query.view.PromotionBriefView;
import io.github.yasenia.pricing.query.view.PromotionView;

import java.util.List;

public interface PromotionQueryService {

    PromotionView queryPromotionById(String promotionId) throws PromotionNotFoundException;

    List<PromotionBriefView> queryPromotions(QueryPromotionCriteria criteria, int offset, int limit);

    int countPromotions(QueryPromotionCriteria criteria);
}
