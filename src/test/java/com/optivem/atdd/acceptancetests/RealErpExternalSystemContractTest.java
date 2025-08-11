package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.ErpDriver;
import com.optivem.atdd.acceptancetests.shared.RealErpDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

// TODO: JC: Can I avoid SpringBootTest since the app isn't needed, just the config?
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
public class RealErpExternalSystemContractTest extends BaseErpExternalSystemContractTest {

    @Value("${erp.url}")
    private String erpUrl;

    @Override
    protected String getErpUrl() {
        return erpUrl;
    }

    @Override
    protected ErpDriver createErpDriver(WebClient webClient) {
        return new RealErpDriver(webClient);
    }
}
