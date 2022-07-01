package io.github.yasenia.pricing.rest.request.payload;

import io.github.yasenia.pricing.domain.model.product.IdAllowListProductSet;
import io.github.yasenia.pricing.domain.model.product.IdBlockListProductSet;
import io.github.yasenia.pricing.domain.model.product.ProductSet;
import org.junit.jupiter.api.RepeatedTest;

import static io.github.yasenia.pricing.rest.Requests.aProductSetPayload;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ProductSetPayloadTest {

    @RepeatedTest(5)
    void should_convert_to_product_set_correctly() {
        var productSetPayload = aProductSetPayload();
        assertMatch(productSetPayload.toProductSet(), productSetPayload);
    }

    private void assertMatch(ProductSet productSet, ProductSetPayload productSetPayload) {
        switch (productSetPayload.type()) {
            case ID_ALLOW_LIST -> {
                if (!(productSet instanceof IdAllowListProductSet idAllowListProductSet)) throw new AssertionError();
                assertThat(idAllowListProductSet.allowedProductIds(), equalTo(productSetPayload.allowedProductIds()));
            }
            case ID_BLOCK_LIST -> {
                if (!(productSet instanceof IdBlockListProductSet idBlockListProductSet)) throw new AssertionError();
                assertThat(idBlockListProductSet.blockedProductIds(), equalTo(productSetPayload.blockedProductIds()));
            }
            default -> { }
        }
    }
}
