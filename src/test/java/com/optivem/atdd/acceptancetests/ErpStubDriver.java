package com.optivem.atdd.acceptancetests;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ErpStubDriver extends BaseErpDriver {

//    private final WireMock erpWireMockStub;
//
//    public ErpStubDriver(WireMock erpWireMockStub) {
//        this.erpWireMockStub = erpWireMockStub;
//    }
//
//    public void setupProduct(String sku, String price) {
//        erpWireMockStub.stubFor(get(urlPathEqualTo("/products"))
//                .withQueryParam("sku", equalTo(sku))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"price\": " + price + "}")));
//    }


    protected ErpStubDriver(WebClient webClient) {
        super(webClient);
    }

    public void setupProduct(String sku, String price) {
        // TODO: VJ: Use variables

        String stubJson = """
        {
          "request": {
            "method": "GET",
            "urlPath": "/products/8"
          },
          "response": {
            "status": 200,
            "headers": {
              "Content-Type": "application/json"
            },
            "jsonBody": {
              "price": 2.50
            }
          }
        }
        """;

        webClient.post()
                .uri("/__admin/mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stubJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
