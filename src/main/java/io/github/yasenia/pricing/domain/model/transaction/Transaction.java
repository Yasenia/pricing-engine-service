package io.github.yasenia.pricing.domain.model.transaction;

import java.util.List;
import java.util.Set;

public record Transaction(
    List<TransactionItem> items,
    Set<String> appliedPromotionCodes
) {
}
