package com.optivem.atdd.acceptancetests.shared.drivers.system;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemUiDriver implements SystemDriver {
    private final WebDriver driver;
    private final String shopUrl;


    private final ShopPage shopPage;

    private static final Pattern ORDER_CONFIRMATION_PATTERN = Pattern.compile(
            "Success! Order has been created with Order Number ([\\w-]+) and Total Price \\$(\\d+(?:\\.\\d{2})?)"
    );

    private static final int ORDER_NUMBER_GROUP = 1;
    private static final int TOTAL_PRICE_GROUP = 2;

    public SystemUiDriver(WebDriver driver, String shopUrl) {
        this.driver = driver;
        this.shopUrl = shopUrl;



        this.shopPage = new ShopPage(driver);
    }

    @Override
    public void load() {
        driver.get(shopUrl);
    }

    @Override
    public void submitOrder(String orderNumber, String sku, String quantity) {
        shopPage.enterSku(sku);
        shopPage.enterQuantity(quantity);
        shopPage.clickPlaceOrder();
    }

    @Override
    public void confirmOrderNumberGenerated(String orderNumber) {
        var confirmation = shopPage.getConfirmationMessage();
        var externalOrderNumber = getOrderNumberFromConfirmation(confirmation);
        assertThat(externalOrderNumber).isNotEmpty();
    }

    @Override
    public void confirmOrderTotalPrice(String orderNumber, String expectedTotalPrice) {
        var confirmation = shopPage.getConfirmationMessage();
        var totalPrice = getTotalPriceFromConfirmation(confirmation);
        assertThat(totalPrice).isEqualTo(expectedTotalPrice);
    }

    private static String getOrderNumberFromConfirmation(String confirmation) {
        var matcher = ORDER_CONFIRMATION_PATTERN.matcher(confirmation);
        if (matcher.find()) {
            return matcher.group(ORDER_NUMBER_GROUP);
        }
        return null;
    }

    private static String getTotalPriceFromConfirmation(String confirmation) {
        var matcher = ORDER_CONFIRMATION_PATTERN.matcher(confirmation);
        if (matcher.find()) {
            return matcher.group(TOTAL_PRICE_GROUP);
        }
        return null;
    }

    private class ShopPage {
        private static final String SKU_INPUT = "[aria-label='SKU']";
        private static final String QUANTITY_INPUT = "[aria-label='Quantity']";
        private static final String PLACE_ORDER_BUTTON = "[aria-label='Place Order']";
        private static final String ALERT_ROLE = "[role='alert']";

        private final WebDriver driver;

        private final WebDriverWait wait;

        public ShopPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        public void enterSku(String sku) {
            driver.findElement(By.cssSelector(SKU_INPUT)).sendKeys(sku);
        }

        public void enterQuantity(String quantity) {
            driver.findElement(By.cssSelector(QUANTITY_INPUT)).sendKeys(quantity);
        }

        public void clickPlaceOrder() {
            driver.findElement(By.cssSelector(PLACE_ORDER_BUTTON)).click();
        }

        public String getConfirmationMessage() {
            var confirmationElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ALERT_ROLE))
            );

            return confirmationElement.getText();
        }
    }


}