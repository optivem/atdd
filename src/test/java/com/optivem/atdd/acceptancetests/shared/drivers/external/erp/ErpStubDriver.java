package com.optivem.atdd.acceptancetests.shared.drivers.external.erp;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ErpStubDriver extends BaseErpDriver {
//
//    private final WireMock erpWireMockStub;
//
//    public ErpStubDriver(WireMock erpWireMockStub) {
//        this.erpWireMockStub = erpWireMockStub;
//    }
//
//    public void setupProduct(String sku, String price) {
//        var url = "/products/" + sku;
//        erpWireMockStub.stubFor(get(urlPathEqualTo(url))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"price\": " + price + "}")));
//    }


    public ErpStubDriver(WebClient webClient) {
        super(webClient);
    }

    public void setupProduct(String sku, String price) {
        // TODO: VJ: Use variables

        String stubJson = String.format("""
        {
          "request": {
            "method": "GET",
            "urlPath": "/products/%s"
          },
          "response": {
            "status": 200,
            "headers": {
              "Content-Type": "application/json"
            },
            "jsonBody": {
              "price": %s
            }
          }
        }
        """, sku, price);

        webClient.post()
                .uri("/__admin/mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stubJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
