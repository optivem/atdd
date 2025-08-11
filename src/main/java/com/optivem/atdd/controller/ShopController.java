package com.optivem.atdd.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class ShopController {

    @GetMapping("/shop")
    @ResponseBody
    public String shopPage() {
        return """
            <html>
            <body>
                <form method='post' action='/shop'>
                    <input name='sku' aria-label='SKU' />
                    <input name='quantity' aria-label='Quantity' />
                    <button type='submit' aria-label='Place Order'>Place Order</button>
                </form>
                <div role='alert'></div>
            </body>
            </html>
            """;
    }

    @PostMapping("/shop")
    @ResponseBody
    public String placeOrder(@RequestParam String sku, @RequestParam int quantity) {
        // double price = 2.50; // TODO: VJ: Remove: Hardcoded for APPLE1001
        double price = fetchPriceFromApi();
        double total = price * quantity;
        return """
            <html>
            <body>
                <form method='post' action='/shop'>
                    <input name='sku' aria-label='SKU' value='%s' />
                    <input name='quantity' aria-label='Quantity' value='%d' />
                    <button type='submit' aria-label='Place Order'>Place Order</button>
                </form>
                <div role='alert'>Success! Total price is $%.2f</div>
            </body>
            </html>
            """.formatted(sku, quantity, total);
    }

    private double fetchPriceFromApi() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://dummyjson.com/products/1"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.body());
            return node.get("price").asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch price", e);
        }
    }
}