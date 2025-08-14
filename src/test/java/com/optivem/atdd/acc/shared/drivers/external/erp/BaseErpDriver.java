package com.optivem.atdd.acc.shared.drivers.external.erp;

import org.springframework.web.reactive.function.client.WebClient;

public class BaseErpDriver implements ErpDriver {
    protected final WebClient webClient;

    protected BaseErpDriver(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductResponse getProduct(String sku) {
        var path = "/products/" + sku;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .build())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }
}