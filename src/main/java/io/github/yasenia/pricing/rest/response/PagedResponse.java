package io.github.yasenia.pricing.rest.response;

import java.util.List;

public record PagedResponse<T>(
    int totalCount,
    List<T> data
) {
}
