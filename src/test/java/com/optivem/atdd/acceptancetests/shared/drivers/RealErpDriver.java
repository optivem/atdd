package com.optivem.atdd.acceptancetests.shared.drivers;

import org.springframework.web.reactive.function.client.WebClient;

public class RealErpDriver extends BaseErpDriver {
    public RealErpDriver(WebClient webClient) {
        super(webClient);
    }
}


