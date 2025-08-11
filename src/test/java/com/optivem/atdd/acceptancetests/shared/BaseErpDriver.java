package com.optivem.atdd.acceptancetests.shared;

import org.springframework.web.reactive.function.client.WebClient;

public class BaseErpDriver {
    protected final WebClient webClient;

    protected BaseErpDriver(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductResponse getProduct(String sku) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products")
                        .queryParam("sku", sku)
                        .build())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }
}