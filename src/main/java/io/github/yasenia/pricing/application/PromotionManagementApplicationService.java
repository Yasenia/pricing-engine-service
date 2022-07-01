package io.github.yasenia.pricing.application;

import io.github.yasenia.pricing.application.command.DeletePromotionCommand;
import io.github.yasenia.pricing.application.command.EditPromotionInfoCommand;
import io.github.yasenia.pricing.application.result.PromotionCreatedResult;
import io.github.yasenia.pricing.application.command.CreatePromotionCommand;
import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;

public interface PromotionManagementApplicationService {

    PromotionCreatedResult createPromotion(CreatePromotionCommand command) throws InvalidPromotionInfoException;

    void editPromotionInfo(EditPromotionInfoCommand command) throws NoSuchPromotionException, InvalidPromotionInfoException;

    void deletePromotion(DeletePromotionCommand command) throws NoSuchPromotionException;
}
