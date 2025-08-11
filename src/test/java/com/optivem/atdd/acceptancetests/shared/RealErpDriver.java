package com.optivem.atdd.acceptancetests.shared;

import org.springframework.web.reactive.function.client.WebClient;

public class RealErpDriver extends BaseErpDriver {
    protected RealErpDriver(WebClient webClient) {
        super(webClient);
    }
}


