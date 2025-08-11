package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.ErpDriver;
import com.optivem.atdd.acceptancetests.shared.RealErpDriver;
import org.springframework.web.reactive.function.client.WebClient;

public class RealErpExternalSystemContractTest extends BaseErpExternalSystemContractTest {
    @Override
    protected ErpDriver createErpDriver(WebClient webClient) {
        return new RealErpDriver(webClient);
    }
}
