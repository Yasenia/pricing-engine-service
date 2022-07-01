package io.github.yasenia.pricing.application;

import io.github.yasenia.pricing.application.command.CreatePromotionCommand;
import io.github.yasenia.pricing.application.command.DeletePromotionCommand;
import io.github.yasenia.pricing.application.command.EditPromotionInfoCommand;
import io.github.yasenia.pricing.application.result.PromotionCreatedResult;
import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;
import io.github.yasenia.pricing.domain.factory.PromotionFactory;
import io.github.yasenia.pricing.domain.repository.PromotionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PromotionManagementApplicationServiceImpl implements PromotionManagementApplicationService {

    private final PromotionFactory promotionFactory;

    private final PromotionRepository promotionRepository;

    public PromotionManagementApplicationServiceImpl(PromotionFactory promotionFactory, PromotionRepository promotionRepository) {
        this.promotionFactory = promotionFactory;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public PromotionCreatedResult createPromotion(CreatePromotionCommand command) throws InvalidPromotionInfoException {
        var promotion = promotionFactory.createPromotion(command.creatorId(), command.info(), command.constraint(), command.rule());
        var savedPromotion = promotionRepository.save(promotion);
        return new PromotionCreatedResult(savedPromotion.getId());
    }

    @Override
    public void editPromotionInfo(EditPromotionInfoCommand command) throws NoSuchPromotionException, InvalidPromotionInfoException {
        var promotion = promotionRepository.findById(command.promotionId()).orElseThrow(() -> NoSuchPromotionException.ofId(command.promotionId()));
        promotion.editInfo(command.editorId(), command.info());
        promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(DeletePromotionCommand command) throws NoSuchPromotionException {
        var promotion = promotionRepository.findById(command.promotionId()).orElseThrow(() -> NoSuchPromotionException.ofId(command.promotionId()));
        promotionRepository.delete(promotion);
    }
}
