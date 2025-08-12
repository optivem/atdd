package com.optivem.atdd.e2e.v3;

import com.optivem.atdd.e2e.v3.shared.dsl.ShopDsl;
import com.optivem.atdd.e2e.v3.shared.drivers.UiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
public class ShopE2ETestV3 {

    @LocalServerPort
    private int port;

    private WebDriver seleniumDriver;
    private String baseUrl;

    private ShopDsl shop;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;

        shop = new ShopDsl(new UiDriver(seleniumDriver, baseUrl + "/shop"));
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Test
    public void shouldCompletePurchaseSuccessfully() {
        shop.placeOrder("sku: ABC1001", "quantity: 5");
        shop.assertTotalPriceIsPositive();
    }
}