package com.optivem.atdd.mvc.controller;

import com.optivem.atdd.common.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        double totalPrice = PriceCalculator.calculatePrice(erpUrl, sku, quantity);
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
            """.formatted(sku, quantity, totalPrice);
    }


}