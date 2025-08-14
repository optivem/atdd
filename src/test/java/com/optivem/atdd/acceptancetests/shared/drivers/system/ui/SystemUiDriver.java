package com.optivem.atdd.acceptancetests.shared.drivers.system.ui;

import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriver;
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
}