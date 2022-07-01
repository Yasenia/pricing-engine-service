package io.github.yasenia.pricing.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yasenia.pricing.application.PromotionManagementApplicationService;
import io.github.yasenia.pricing.application.command.CreatePromotionCommand;
import io.github.yasenia.pricing.application.command.DeletePromotionCommand;
import io.github.yasenia.pricing.application.command.EditPromotionInfoCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.yasenia.pricing.application.result.PromotionCreatedResults.aPromotionCreatedResult;
import static io.github.yasenia.pricing.rest.Requests.aCreatePromotionRequest;
import static io.github.yasenia.pricing.rest.Requests.aDeletePromotionRequest;
import static io.github.yasenia.pricing.rest.Requests.anEditPromotionInfoRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromotionManagementCommandController.class)
class PromotionManagementCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromotionManagementApplicationService promotionManagementApplicationService;

    @Test
    void should_return_ok_when_create_promotion_by_api() throws Exception {
        // given
        var request = aCreatePromotionRequest();
        var result = aPromotionCreatedResult();
        given(promotionManagementApplicationService.createPromotion(any())).willReturn(result);
        // when
        mockMvc
            .perform(
                post("/management/create-promotion")
                    .contentType(APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsBytes(request))
            )
            // then
            .andExpect(status().isOk())
            // and then (convert result to response correctly)
            .andExpect(jsonPath("$.promotionId").value(result.promotionId()))
        ;
        // and then (convert request to command correctly)
        var commandCaptor = ArgumentCaptor.forClass(CreatePromotionCommand.class);
        verify(promotionManagementApplicationService, only()).createPromotion(commandCaptor.capture());
        var capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.creatorId(), equalTo(request.creatorId()));
        assertThat(capturedCommand.info().title(), equalTo(request.title()));
        assertThat(capturedCommand.info().description(), equalTo(request.description()));
        assertThat(capturedCommand.constraint(), equalTo(request.constraint().toConstraint()));
        assertThat(capturedCommand.rule(), equalTo(request.rule().toRule()));
    }

    @Test
    void should_return_ok_when_edit_promotion_info_by_api() throws Exception {
        // given
        var request = anEditPromotionInfoRequest();
        // when
        mockMvc
            .perform(
                post("/management/edit-promotion-info")
                    .contentType(APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsBytes(request))
            )
            // then
            .andExpect(status().isOk())
        ;
        // and then (convert request to command correctly)
        var commandCaptor = ArgumentCaptor.forClass(EditPromotionInfoCommand.class);
        verify(promotionManagementApplicationService, only()).editPromotionInfo(commandCaptor.capture());
        var capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.editorId(), equalTo(request.editorId()));
        assertThat(capturedCommand.promotionId(), equalTo(request.promotionId()));
        assertThat(capturedCommand.info().title(), equalTo(request.title()));
        assertThat(capturedCommand.info().description(), equalTo(request.description()));
    }

    @Test
    void should_return_ok_when_delete_promotion_by_api() throws Exception {
        // given
        var request = aDeletePromotionRequest();
        // when
        mockMvc
            .perform(
                post("/management/delete-promotion")
                    .contentType(APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsBytes(request))
            )
            // then
            .andExpect(status().isOk())
        ;
        // and then (convert request to command correctly)
        var commandCaptor = ArgumentCaptor.forClass(DeletePromotionCommand.class);
        verify(promotionManagementApplicationService, only()).deletePromotion(commandCaptor.capture());
        var capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.promotionId(), equalTo(request.promotionId()));
    }

}
