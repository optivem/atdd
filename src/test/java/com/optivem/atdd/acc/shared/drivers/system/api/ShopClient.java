package com.optivem.atdd.acc.shared.drivers.system.api;

import com.optivem.atdd.acc.shared.drivers.system.api.dtos.GetOrderResponse;
import com.optivem.atdd.acc.shared.drivers.system.api.dtos.PlaceOrderRequest;
import com.optivem.atdd.acc.shared.drivers.system.api.dtos.PlaceOrderResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ShopClient {
    private final WebClient webClient;

    public ShopClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void echo() {
        webClient.get()
                .uri("/api/shop/echo")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public PlaceOrderResponse placeOrder(PlaceOrderRequest orderRequest) {
        var responseMono = webClient.post()
                .uri("/api/shop/order")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(PlaceOrderResponse.class);

        var response = responseMono.block();
        return response;
    }

    public GetOrderResponse getOrder(String externalOrderNumber) {
        var responseMono = webClient.get()
                .uri("/api/shop/order/{orderNumber}", externalOrderNumber)
                .retrieve()
                .bodyToMono(GetOrderResponse.class);

        var response = responseMono.block();
        return response;
    }
}

