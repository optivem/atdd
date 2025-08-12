package com.optivem.atdd.e2e.v2;

import com.optivem.atdd.e2e.v2.shared.pages.ShopPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
class ShopE2ETestV2 {

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

    @Test
    void shouldCompletePurchaseSuccessfully() {
        ShopPage shopPage = new ShopPage(seleniumDriver);
        shopPage.open(baseUrl);
        shopPage.enterSku("8");
        shopPage.enterQuantity("5");
        shopPage.placeOrder();

        String text = shopPage.getConfirmationMessage();
        assertThat(text).matches("Success! Total price is \\$\\d+(\\.\\d{2})?");
    }
}