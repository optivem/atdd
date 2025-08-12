package com.optivem.atdd.acceptancetests.shared.drivers.external.erp;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class ErpStubDriver extends BaseErpDriver {

    private final WireMock erpWireMockStub;

    public ErpStubDriver(WebClient webClient) {
        super(webClient);
        this.erpWireMockStub = new WireMock("localhost", 8089);
    }

    public void setupProduct(String sku, String price) {
        var url = "/products/" + sku;
        erpWireMockStub.register(get(urlPathEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"price\": " + price + "}")));
    }
}
