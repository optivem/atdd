package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.TestConfiguration;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpStubDriver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

public class ErpStubExternalSystemContractTest extends BaseErpExternalSystemContractTest {
    @Override
    protected String getErpUrl() {
        return TestConfiguration.ERP_URL_ACC;
    }

    @Override
    protected ErpDriver createErpDriver(WebClient webClient) {
        return new ErpStubDriver(webClient);
    }

    @Override
    protected void setupProduct(String sku, String price) {
        ErpStubDriver erpStubDriver = (ErpStubDriver) erpDriver;
        erpStubDriver.setupProduct(sku, price);
    }

    @Test
    void shouldFetchCorrectProductPrice() {
        setupProduct("ABC1001", "2.50");

        var response = erpDriver.getProduct("ABC1001");

        assertThat(response.getPrice()).isEqualTo("2.50");
    }
}
