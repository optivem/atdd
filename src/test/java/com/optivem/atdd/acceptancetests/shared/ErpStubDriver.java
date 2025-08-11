package com.optivem.atdd.acceptancetests.shared;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

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


    public ErpStubDriver(WebClient webClient) {
        super(webClient);
    }

    public void setupProduct(String sku, BigDecimal price) {
        // TODO: VJ: Use variables

        var priceString = price.toString();

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
        """, sku, priceString);

        webClient.post()
                .uri("/__admin/mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stubJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
