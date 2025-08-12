package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.ErpDriver;
import com.optivem.atdd.acceptancetests.shared.ErpStubDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acc")
public class ErpStubExternalSystemContractTest extends BaseErpExternalSystemContractTest {
    @Value("${erp.url}")
    private String erpUrl;

    @Override
    protected String getErpUrl() {
        return erpUrl;
    }

    @Override
    protected ErpDriver createErpDriver(WebClient webClient) {
        return new ErpStubDriver(webClient);
    }

    @Override
    protected void setupProduct(String sku, String price) {
        ErpStubDriver erpStubDriver = (ErpStubDriver) getErpDriver();
        erpStubDriver.setupProduct(sku, price);
    }
}
