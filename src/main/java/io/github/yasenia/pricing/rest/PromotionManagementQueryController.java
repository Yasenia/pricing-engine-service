package io.github.yasenia.pricing.rest;

import io.github.yasenia.pricing.query.PromotionQueryService;
import io.github.yasenia.pricing.query.criteria.QueryPromotionCriteria;
import io.github.yasenia.pricing.query.view.PromotionBriefView;
import io.github.yasenia.pricing.query.view.PromotionView;
import io.github.yasenia.pricing.rest.response.PagedResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class PromotionManagementQueryController {

    private final PromotionQueryService promotionQueryService;

    public PromotionManagementQueryController(PromotionQueryService promotionQueryService) {
        this.promotionQueryService = promotionQueryService;
    }

    @GetMapping("/query-promotion-by-id")
    public PromotionView queryById(@RequestParam String promotionId) throws Exception {
        return promotionQueryService.queryPromotionById(promotionId);
    }

    @GetMapping("/query-paged-promotions")
    public PagedResponse<PromotionBriefView> queryPagedPromotions(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        var criteria = new QueryPromotionCriteria();
        return new PagedResponse<>(
            promotionQueryService.countPromotions(criteria),
            promotionQueryService.queryPromotions(criteria, (page - 1) * size, size)
        );
    }
}
