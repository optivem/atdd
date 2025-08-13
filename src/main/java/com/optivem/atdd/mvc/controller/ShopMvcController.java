package com.optivem.atdd.mvc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class ShopMvcController {

    @Value("${erp.url}")
    private String erpUrl;

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
        double price = fetchPriceFromApi(sku);
        double total = price * quantity;
        return """
            <html>
            <body>
                <form method='post' action='/shop'>
                    <input name='sku' aria-label='SKU' value='%s' />
                    <input name='quantity' aria-label='Quantity' value='%d' />
                    <button type='submit' aria-label='Place Order'>Place Order</button>
                </form>
                <div role='alert'>Success! Total Price is $%.2f</div>
            </body>
            </html>
            """.formatted(sku, quantity, total);
    }

    private double fetchPriceFromApi(String sku) {
        try {
            var url = erpUrl + "/products/" + sku;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
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