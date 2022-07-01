package io.github.yasenia.pricing.rest;

import io.github.yasenia.pricing.query.PromotionQueryService;
import io.github.yasenia.pricing.query.criteria.QueryPromotionCriteria;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.query.view.PromotionBriefViews.aPromotionBriefView;
import static io.github.yasenia.pricing.query.view.PromotionViews.newPromotionView;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromotionManagementQueryController.class)
class PromotionManagementQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromotionQueryService promotionQueryService;

    @Test
    void should_return_promotion_view_when_query_promotion_by_id_given_promotion_is_existed() throws Exception {
        // given
        var promotionId = aPromotionId();
        var promotionView = newPromotionView().withId(promotionId).build();
        given(promotionQueryService.queryPromotionById(any())).willReturn(promotionView);
        // when
        mockMvc
            .perform(get("/management/query-promotion-by-id").queryParam("promotionId", promotionId))
            // then
            .andExpect(status().isOk())
            // and then (convert view to response correctly)
            .andExpect(jsonPath("$.id").value(promotionView.id()))
            .andExpect(jsonPath("$.title").value(promotionView.title()))
            .andExpect(jsonPath("$.description").value(promotionView.description()))
            .andExpect(jsonPath("$.constraint").exists())
            .andExpect(jsonPath("$.rule").exists())
            .andExpect(jsonPath("$.creatorId").value(promotionView.creatorId()))
            .andExpect(jsonPath("$.createTime").value(promotionView.createTime().format(ISO_DATE_TIME)))
            .andExpect(jsonPath("$.lastEditorId").value(promotionView.lastEditorId()))
            .andExpect(jsonPath("$.lastEditTime").value(promotionView.lastEditTime().format(ISO_DATE_TIME)))
        ;
        // and then (convert request to criteria correctly)
        verify(promotionQueryService, only()).queryPromotionById(eq(promotionId));
    }

    @Test
    void should_return_paged_promotions_when_query_query_paged_promotions() throws Exception {
        // given
        var promotionBriefViews = range(0, 10).mapToObj(i -> aPromotionBriefView()).toList();
        var totalPromotionCount = 42;
        given(promotionQueryService.countPromotions(any())).willReturn(totalPromotionCount);
        given(promotionQueryService.queryPromotions(any(), anyInt(), anyInt())).willReturn(promotionBriefViews);
        var queryPage = 2;
        var querySize = 10;
        // when
        mockMvc
            .perform(
                get("/management/query-paged-promotions")
                    .queryParam("page", Integer.toString(queryPage))
                    .queryParam("size", Integer.toString(querySize))
            )
            // then
            .andExpect(status().isOk())
            // and then (convert view to response correctly)
            .andExpect(jsonPath("$.totalCount").value(totalPromotionCount))
            .andExpect(jsonPath("$.data.length()").value(promotionBriefViews.size()))
        ;
        // and then (convert request to criteria correctly)
        var criteriaCaptor = ArgumentCaptor.forClass(QueryPromotionCriteria.class);
        verify(promotionQueryService).countPromotions(criteriaCaptor.capture());
        var capturedCriteria = criteriaCaptor.getValue();
        assertThat(capturedCriteria, notNullValue());
        verify(promotionQueryService).queryPromotions(eq(capturedCriteria), eq((queryPage - 1) * querySize), eq(querySize));
        verifyNoMoreInteractions(promotionQueryService);
    }
}
