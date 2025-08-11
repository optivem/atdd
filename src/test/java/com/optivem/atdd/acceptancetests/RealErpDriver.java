package com.optivem.atdd.acceptancetests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

public class RealErpDriver extends BaseErpDriver {
    protected RealErpDriver(WebClient webClient) {
        super(webClient);
    }
}


