package io.github.yasenia.pricing.application.command;

import static io.github.yasenia.pricing.common.Texts.aPromotionId;

public class DeletePromotionCommands {
    private String promotionId;

    private DeletePromotionCommands() {
    }

    public static DeletePromotionCommand aDeletePromotionCommand() {
        return newDeletePromotionCommand().build();
    }

    public static DeletePromotionCommands newDeletePromotionCommand() {
        return new DeletePromotionCommands().withPromotionId(aPromotionId());
    }

    public DeletePromotionCommands withPromotionId(String promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public DeletePromotionCommand build() {
        return new DeletePromotionCommand(this.promotionId);
    }
}
