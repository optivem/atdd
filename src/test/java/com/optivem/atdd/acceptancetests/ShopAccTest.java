package com.optivem.atdd.acceptancetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acc")
public class ShopAccTest {

    @LocalServerPort
    private int port;

    @Value("${erp.url}")
    private String erpUrl;

    private WebDriver seleniumDriver;
    private String baseUrl;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;

        webClient = WebClient.create(erpUrl);
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Test
    public void shouldCompletePurchaseSuccessfully() {
        stubOut();

        var shop = new ShopDsl(new UiDriver(seleniumDriver, baseUrl + "/shop"));

        shop.placeOrder("sku: 8", "quantity: 5");
        shop.assertConfirmation("message: Success! Total price is $12.50");
    }

    private void stubOut() {
        var erpStubDriver = new ErpStubDriver(webClient);
        erpStubDriver.setupProduct("8", "2.50");
    }
}