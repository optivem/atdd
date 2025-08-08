package com.optivem.atdd.e2e.v3;

import org.springframework.web.reactive.function.client.WebClient;

public class RealErpDriver {
    protected final WebClient webClient;

    protected RealErpDriver(WebClient webClient) {
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