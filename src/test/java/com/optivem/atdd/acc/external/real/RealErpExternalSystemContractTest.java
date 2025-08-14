package com.optivem.atdd.acc.external.real;

import com.optivem.atdd.TestConfiguration;
import com.optivem.atdd.acc.external.base.BaseErpExternalSystemContractTest;
import com.optivem.atdd.acc.shared.drivers.external.erp.ErpDriver;
import com.optivem.atdd.acc.shared.drivers.external.erp.RealErpDriver;
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
