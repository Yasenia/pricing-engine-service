package io.github.yasenia.pricing.application;

import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.exception.NoSuchPromotionException;
import io.github.yasenia.pricing.domain.model.PromotionInfo;
import io.github.yasenia.pricing.domain.repository.PromotionRepository;
import io.github.yasenia.testcore.ApplicationServiceTest;
import io.github.yasenia.testcore.TestTransactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.yasenia.pricing.application.command.CreatePromotionCommands.aCreatePromotionCommand;
import static io.github.yasenia.pricing.application.command.CreatePromotionCommands.newCreatePromotionCommand;
import static io.github.yasenia.pricing.application.command.DeletePromotionCommands.aDeletePromotionCommand;
import static io.github.yasenia.pricing.application.command.DeletePromotionCommands.newDeletePromotionCommand;
import static io.github.yasenia.pricing.application.command.EditPromotionInfoCommands.aEditPromotionInfoCommand;
import static io.github.yasenia.pricing.application.command.EditPromotionInfoCommands.newEditPromotionInfoCommand;
import static io.github.yasenia.pricing.domain.exception.NoSuchPromotionException.KeyType.ID;
import static io.github.yasenia.pricing.domain.model.Promotions.aPromotion;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ApplicationServiceTest
class PromotionManagementApplicationServiceTest {

    @Autowired
    private PromotionManagementApplicationService promotionManagementApplicationService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Test
    void should_create_promotion_and_save_to_repository_when_create_promotion() {
        // given
        var command = aCreatePromotionCommand();
        // then
        var result = assertDoesNotThrow(() -> {
            // when
            return promotionManagementApplicationService.createPromotion(command);
        });
        TestTransactions.commitAndRestart();
        // and then
        assertTrue(promotionRepository.existsById(result.promotionId()));
    }

    @Test
    void should_throw_exception_when_create_promotion_given_invalid_promotion_info() {
        // given
        var promotionInfo = mock(PromotionInfo.class);
        var command = newCreatePromotionCommand().withInfo(promotionInfo).build();
        given(promotionInfo.isValid()).willReturn(false);
        // then
        assertThrows(InvalidPromotionInfoException.class, () -> {
            // when
            promotionManagementApplicationService.createPromotion(command);
        });
    }

    @Test
    void should_edit_info_and_save_promotion_to_repository_when_edit_promotion_info_given_promotion_is_existed() {
        // given
        var savedPromotion = promotionRepository.save(aPromotion());
        var command = newEditPromotionInfoCommand().withPromotionId(savedPromotion.getId()).build();
        // then
        assertDoesNotThrow(() -> {
            // when
            promotionManagementApplicationService.editPromotionInfo(command);
        });
    }

    @Test
    void should_throw_exception_when_edit_promotion_info_given_promotion_is_not_existed() {
        // given
        var command = aEditPromotionInfoCommand();
        // then
        var exception = assertThrows(NoSuchPromotionException.class, () -> {
            // when
            promotionManagementApplicationService.editPromotionInfo(command);
        });
        // and then
        assertThat(exception.keyType, is(ID));
        assertThat(exception.key, is(command.promotionId()));
    }

    @Test
    void should_throw_exception_when_edit_promotion_info_given_invalid_promotion_info() {
        // given
        var savedPromotion = promotionRepository.save(aPromotion());
        var promotionInfo = mock(PromotionInfo.class);
        var command = newEditPromotionInfoCommand().withPromotionId(savedPromotion.getId()).withInfo(promotionInfo).build();
        given(promotionInfo.isValid()).willReturn(false);
        // then
        assertThrows(InvalidPromotionInfoException.class, () -> {
            // when
            promotionManagementApplicationService.editPromotionInfo(command);
        });
    }

    @Test
    void should_remove_promotion_from_repository_when_delete_promotion_given_promotion_is_existed() {
        // given
        var savedPromotion = promotionRepository.save(aPromotion());
        var command = newDeletePromotionCommand().withPromotionId(savedPromotion.getId()).build();
        // then
        assertDoesNotThrow(() -> {
            // when
            promotionManagementApplicationService.deletePromotion(command);
        });
        TestTransactions.commitAndRestart();
        // and then
        assertFalse(promotionRepository.existsById(savedPromotion.getId()));
    }

    @Test
    void should_throw_exception_when_delete_promotion_given_promotion_is_not_existed() {
        // given
        var command = aDeletePromotionCommand();
        // then
        var exception = assertThrows(NoSuchPromotionException.class, () -> {
            // when
            promotionManagementApplicationService.deletePromotion(command);
        });
        // and then
        assertThat(exception.keyType, is(ID));
        assertThat(exception.key, is(command.promotionId()));
    }
}
