package io.github.yasenia.pricing.domain.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import io.github.yasenia.pricing.domain.exception.InvalidPromotionInfoException;
import io.github.yasenia.pricing.domain.model.calculation.CalculationContext;
import io.github.yasenia.pricing.domain.model.calculation.CalculationResult;
import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
import io.github.yasenia.pricing.domain.model.rule.PromotionRule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.OffsetDateTime;

import static io.github.yasenia.core.tool.Times.currentTime;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Promotion implements PromotionRule, PromotionConstraint {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    private String code;

    private String title;

    private String description;

    @Type(JsonType.class)
    private JsonNode constraintJson;

    @Type(JsonType.class)
    private JsonNode ruleJson;

    private String creatorId;

    private OffsetDateTime createTime;

    private String lastEditorId;

    private OffsetDateTime lastEditTime;

    @PersistenceCreator
    protected Promotion() {
    }

    public Promotion(
        String creatorId, String code, PromotionInfo info, PromotionConstraint constraint, PromotionRule rule
    ) throws InvalidPromotionInfoException {
        if (!info.isValid()) throw new InvalidPromotionInfoException();
        this.code = code;
        this.title = info.title();
        this.description = info.description();
        this.constraintJson = constraint.toJsonNode();
        this.ruleJson = rule.toJson();
        this.creatorId = creatorId;
        this.createTime = currentTime();
        this.lastEditorId = this.creatorId;
        this.lastEditTime = this.createTime;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public PromotionInfo getInfo() {
        return new PromotionInfo(title, description);
    }

    public PromotionConstraint getConstraint() {
        return PromotionConstraint.fromJsonNode(constraintJson);
    }

    public PromotionRule getRule() {
        return PromotionRule.fromJsonNode(ruleJson);
    }

    public String getCreatorId() {
        return creatorId;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public String getLastEditorId() {
        return lastEditorId;
    }

    public OffsetDateTime getLastEditTime() {
        return lastEditTime;
    }

    @Override
    public boolean isSatisfied(CalculationContext calculationContext) {
        return this.getConstraint().isSatisfied(calculationContext);
    }

    @Override
    public CalculationResult applyTo(CalculationContext calculationContext) {
        return this.getRule().applyTo(calculationContext);
    }

    public void editInfo(String editorId, PromotionInfo info) throws InvalidPromotionInfoException {
        if (!info.isValid()) throw new InvalidPromotionInfoException();
        this.title = info.title();
        this.description = info.description();
        this.lastEditorId = editorId;
        this.lastEditTime = currentTime();
    }
}
