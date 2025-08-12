package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.drivers.ErpDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseErpExternalSystemContractTest {

    private String erpUrl;

    protected ErpDriver erpDriver;

    protected abstract String getErpUrl();

    protected abstract ErpDriver createErpDriver(WebClient webClient);

    @BeforeEach
    void setUp() {
        this.erpUrl = getErpUrl();
        var webClient = WebClient.create(erpUrl);
        this.erpDriver = createErpDriver(webClient);
    }

    @Test
    void shouldFetchProductDetails() {
        setupProduct("8", "2.50");

        var response = erpDriver.getProduct("8");

        assertThat(response.getPrice()).isPositive();

        // assertThat(response.getPrice()).isEqualTo(2.50);
    }

    protected void setupProduct(String sku, String price) {
        // Empty implementation for subclasses to override if needed
    }
}