package io.github.yasenia.pricing.domain.model.product;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @Type(value = IdAllowListProductSet.class, name = "ID_ALLOW_LIST"),
    @Type(value = IdBlockListProductSet.class, name = "ID_BLOCK_LIST"),
})
public interface ProductSet {

    boolean include(String productId);

}
