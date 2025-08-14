package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.TestConfiguration;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.RealErpDriver;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

public class RealErpExternalSystemContractTest extends BaseErpExternalSystemContractTest {
    @Override
    protected String getErpUrl() {
        return TestConfiguration.ERP_URL_E2E;
    }

    @Override
    protected ErpDriver createErpDriver(WebClient webClient) {
        return new RealErpDriver(webClient);
    }
}
