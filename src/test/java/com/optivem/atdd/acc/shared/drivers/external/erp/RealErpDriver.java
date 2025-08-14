package com.optivem.atdd.acc.shared.drivers.external.erp;

import org.springframework.web.reactive.function.client.WebClient;

public class RealErpDriver extends BaseErpDriver {
    public RealErpDriver(WebClient webClient) {
        super(webClient);
    }
}


