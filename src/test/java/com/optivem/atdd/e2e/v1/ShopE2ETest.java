package com.optivem.atdd.e2e.v1;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("e2e")
class ShopE2ETest {

//    @LocalServerPort
    private int port = 8080; // TODO: VJ: This should come from Environment perhaps

    private WebDriver seleniumDriver;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);

        baseUrl = "http://localhost:" + port; // NOTE: VJ: In real life we'd want to set the actual string
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Test
    void shouldCompletePurchaseSuccessfully() {
        // Act
        seleniumDriver.get(baseUrl + "/shop");

        var skuInput = seleniumDriver.findElement(By.cssSelector("[aria-label='SKU']"));
        skuInput.sendKeys("ABC1001");

        var quantityInput = seleniumDriver.findElement(By.cssSelector("[aria-label='Quantity']"));
        quantityInput.sendKeys("5");

        var placeOrderButton = seleniumDriver.findElement(By.cssSelector("[aria-label='Place Order']"));
        placeOrderButton.click();

        var wait = new WebDriverWait(seleniumDriver, Duration.ofSeconds(60));
        var confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']")));

        var confirmationMessageText = confirmationMessage.getText();

        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+) and Total Price \\$(\\d+(?:\\.\\d{2})?)");
        var matcher = pattern.matcher(confirmationMessageText);

        assertThat(matcher.find());

        var totalPriceString = matcher.group(2);
        var totalPrice = Double.parseDouble(totalPriceString);
        assertThat(totalPrice).isPositive();
    }
}