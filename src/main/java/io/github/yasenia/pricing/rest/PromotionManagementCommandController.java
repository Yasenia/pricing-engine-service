package io.github.yasenia.pricing.rest;

import io.github.yasenia.pricing.application.PromotionManagementApplicationService;
import io.github.yasenia.pricing.application.command.CreatePromotionCommand;
import io.github.yasenia.pricing.application.command.DeletePromotionCommand;
import io.github.yasenia.pricing.application.command.EditPromotionInfoCommand;
import io.github.yasenia.pricing.domain.model.PromotionInfo;
import io.github.yasenia.pricing.rest.request.CreatePromotionRequest;
import io.github.yasenia.pricing.rest.request.DeletePromotionRequest;
import io.github.yasenia.pricing.rest.request.EditPromotionInfoRequest;
import io.github.yasenia.pricing.rest.response.PromotionCreatedResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class PromotionManagementCommandController {

    private final PromotionManagementApplicationService promotionManagementApplicationService;

    public PromotionManagementCommandController(PromotionManagementApplicationService promotionManagementApplicationService) {
        this.promotionManagementApplicationService = promotionManagementApplicationService;
    }

    @PostMapping("/create-promotion")
    public PromotionCreatedResponse createPromotion(@RequestBody CreatePromotionRequest request) throws Exception {
        var command = new CreatePromotionCommand(
            request.creatorId(),
            new PromotionInfo(request.title(), request.description()),
            request.constraint().toConstraint(),
            request.rule().toRule()
        );
        var result = promotionManagementApplicationService.createPromotion(command);
        return new PromotionCreatedResponse(result.promotionId());
    }

    @PostMapping("/edit-promotion-info")
    public void editPromotionInfo(@RequestBody EditPromotionInfoRequest request) throws Exception {
        var command = new EditPromotionInfoCommand(request.editorId(), request.promotionId(), new PromotionInfo(request.title(), request.description()));
        promotionManagementApplicationService.editPromotionInfo(command);
    }

    @PostMapping("/delete-promotion")
    public void deletePromotion(@RequestBody DeletePromotionRequest request) throws Exception {
        var command = new DeletePromotionCommand(request.promotionId());
        promotionManagementApplicationService.deletePromotion(command);
    }

}
