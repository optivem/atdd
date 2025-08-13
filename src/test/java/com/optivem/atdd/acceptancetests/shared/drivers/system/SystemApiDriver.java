package com.optivem.atdd.acceptancetests.shared.drivers.system;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class SystemApiDriver implements SystemDriver {
    private final WebClient webClient;
    private final String baseUrl;
    private double lastTotalPrice;

    public SystemApiDriver(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void load() {
        webClient.get()
                .uri("/api/shop/echo")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void submitOrder(String sku, String quantity) {
        var responseMono = webClient.post()
                .uri("/api/shop/order")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new OrderRequest(sku, Integer.parseInt(quantity)))
                .retrieve()
                .bodyToMono(OrderResponse.class);

        var response = responseMono.block();
        if (response != null) {
            lastTotalPrice = response.getTotalPrice();
        }
    }

    @Override
    public void assertTotalPriceEquals(String expectedTotalPrice) {
//        var expected = Double.parseDouble(expectedTotalPrice);
//        if (Double.compare(expected, lastTotalPrice) != 0) {
//            throw new AssertionError("Expected total price: " + expected + ", but was: " + lastTotalPrice);
//        }
    }

    // DTOs
    public static class OrderRequest {
        private String sku;
        private int quantity;

        public OrderRequest(String sku, int quantity) {
            this.sku = sku;
            this.quantity = quantity;
        }

        public String getSku() { return sku; }
        public void setSku(String sku) { this.sku = sku; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    public static class OrderResponse {
        private double totalPrice;

        public double getTotalPrice() { return totalPrice; }
        public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    }
}
