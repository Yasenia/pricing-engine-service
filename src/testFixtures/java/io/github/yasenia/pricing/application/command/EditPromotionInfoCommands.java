package io.github.yasenia.pricing.application.command;

import io.github.yasenia.pricing.domain.model.PromotionInfo;

import static io.github.yasenia.pricing.common.Texts.aPromotionId;
import static io.github.yasenia.pricing.common.Texts.aUserId;
import static io.github.yasenia.pricing.domain.model.PromotionInfos.aPromotionInfo;

public class EditPromotionInfoCommands {
    private String editorId;
    private String promotionId;
    private PromotionInfo info;

    private EditPromotionInfoCommands() {
    }

    public static EditPromotionInfoCommand aEditPromotionInfoCommand() {
        return newEditPromotionInfoCommand().build();
    }

    public static EditPromotionInfoCommands newEditPromotionInfoCommand() {
        return new EditPromotionInfoCommands()
            .withEditorId(aUserId())
            .withPromotionId(aPromotionId())
            .withInfo(aPromotionInfo());
    }

    public EditPromotionInfoCommands withEditorId(String editorId) {
        this.editorId = editorId;
        return this;
    }

    public EditPromotionInfoCommands withPromotionId(String promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public EditPromotionInfoCommands withInfo(PromotionInfo info) {
        this.info = info;
        return this;
    }

    public EditPromotionInfoCommand build() {
        return new EditPromotionInfoCommand(
            this.editorId,
            this.promotionId,
            this.info
        );
    }
}
