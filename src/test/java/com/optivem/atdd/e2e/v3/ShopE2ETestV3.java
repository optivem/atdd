package com.optivem.atdd.e2e.v3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopE2ETestV3 {

    @LocalServerPort
    private int port;

    private WebDriver seleniumDriver;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Disabled
    @Test
    public void shouldCompletePurchaseSuccessfully() {
        var erp = new RealErpDsl(new RealErpDriver(WebClient.create("http://external-erp")));
        var shop = new ShopDsl(new UiDriver(seleniumDriver, baseUrl + "/shop"));

        shop.placeOrder("sku: APPLE1001", "quantity: 5");
        shop.assertConfirmation("message: Success! Total price is $12.50");
    }
}