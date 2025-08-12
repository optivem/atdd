package com.optivem.atdd.acceptancetests.shared.drivers.external.erp;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class ErpStubDriver extends BaseErpDriver {

//    private final WireMock erpWireMockStub;

    public ErpStubDriver(WebClient webClient) {
        super(webClient);
        // this.erpWireMockStub = new WireMock("localhost", 8089);
        // this.erpWireMockStub = new WireMock(8089);
        // this.erpWireMockStub = null;
        WireMock.configureFor("localhost", 8089);
    }

    public void setupProduct(String sku, String price) {
        var url = "/products/" + sku;
        WireMock.stubFor(get(urlPathEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"price\": " + price + "}")));

//        erpWireMockStub.stubFor(get(urlPathEqualTo(url))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"price\": " + price + "}")));
    }


//    public ErpStubDriver(WebClient webClient) {
//        super(webClient);
//    }
//
//    public void setupProduct(String sku, String price) {
//        // TODO: VJ: Use variables
//
//        String stubJson = String.format("""
//        {
//          "request": {
//            "method": "GET",
//            "urlPath": "/products/%s"
//          },
//          "response": {
//            "status": 200,
//            "headers": {
//              "Content-Type": "application/json"
//            },
//            "jsonBody": {
//              "price": %s
//            }
//          }
//        }
//        """, sku, price);
//
//        webClient.post()
//                .uri("/__admin/mappings")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(stubJson)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }
}
