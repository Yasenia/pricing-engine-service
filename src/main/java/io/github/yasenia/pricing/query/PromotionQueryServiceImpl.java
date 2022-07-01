package io.github.yasenia.pricing.query;

import io.github.yasenia.pricing.query.criteria.QueryPromotionCriteria;
import io.github.yasenia.pricing.query.exception.PromotionNotFoundException;
import io.github.yasenia.pricing.query.view.PromotionBriefView;
import io.github.yasenia.pricing.query.view.PromotionConstraintView;
import io.github.yasenia.pricing.query.view.PromotionRuleView;
import io.github.yasenia.pricing.query.view.PromotionView;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;
import static org.jooq.generated.Tables.PROMOTION;

@Service
@Transactional(readOnly = true)
public class PromotionQueryServiceImpl implements PromotionQueryService {

    private final DSLContext dslContext;

    public PromotionQueryServiceImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public PromotionView queryPromotionById(String promotionId) throws PromotionNotFoundException {
        try (var selectFromPromotion = dslContext.selectFrom(PROMOTION)) {
            var record = selectFromPromotion.where(PROMOTION.ID.eq(promotionId)).fetchOne();
            if (isNull(record)) throw new PromotionNotFoundException(promotionId);
            return new PromotionView(
                record.getId(),
                record.getCode(),
                record.getTitle(),
                record.getDescription(),
                PromotionConstraintView.fromJsonb(record.getConstraintJson()),
                PromotionRuleView.fromJsonb(record.getRuleJson()),
                record.getCreatorId(),
                record.getCreateTime(),
                record.getLastEditorId(),
                record.getLastEditTime()
            );
        }
    }

    @Override
    public List<PromotionBriefView> queryPromotions(QueryPromotionCriteria criteria, int offset, int limit) {
        try (var selectFromPromotion = dslContext.selectFrom(PROMOTION)) {
            return selectFromPromotion
                .limit(limit)
                .offset(offset)
                .fetch(record -> new PromotionBriefView(
                    record.getId(),
                    record.getCode(),
                    record.getTitle(),
                    record.getDescription(),
                    record.getCreatorId(),
                    record.getCreateTime(),
                    record.getLastEditorId(),
                    record.getLastEditTime()
                ));
        }
    }

    @Override
    public int countPromotions(QueryPromotionCriteria criteria) {
        try (var selectFromPromotion = dslContext.selectFrom(PROMOTION)) {
            return dslContext.fetchCount(selectFromPromotion);
        }
    }
}
