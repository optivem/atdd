package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.ErpDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class BaseErpExternalSystemContractTest {

    private String erpUrl;

    private ErpDriver erpDriver;

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
        // Abstract method, implemented by Stub & Real External System Driver
        // setupProduct("APPLE1001", 2.50);

        var response = erpDriver.getProduct("8");

        // assertThat(response.getPrice()).isEqualTo(2.50);
    }
}