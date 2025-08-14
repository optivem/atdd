package com.optivem.atdd.e2e.v2;

import com.optivem.atdd.TestConfiguration;
import com.optivem.atdd.e2e.v2.shared.pages.ShopPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class ShopE2ETestV2 {
    private int port = TestConfiguration.SERVER_PORT;

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
        shopPage.enterSku("ABC1001");
        shopPage.enterQuantity("5");
        shopPage.placeOrder();

        var confirmationMessageText = shopPage.getConfirmationMessage();

        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+) and Total Price \\$(\\d+(?:\\.\\d{2})?)");
        var matcher = pattern.matcher(confirmationMessageText);

        assertThat(matcher.find());

        var totalPriceString = matcher.group(2);
        var totalPrice = Double.parseDouble(totalPriceString);
        assertThat(totalPrice).isPositive();
    }
}