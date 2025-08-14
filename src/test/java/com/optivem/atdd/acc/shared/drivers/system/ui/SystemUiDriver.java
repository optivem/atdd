package com.optivem.atdd.acc.shared.drivers.system.ui;

import com.optivem.atdd.acc.shared.drivers.system.SystemDriver;
import org.openqa.selenium.WebDriver;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemUiDriver implements SystemDriver {
    private final ShopPage shopPage;

    private static final String ORDER_CONFIRMATION_REGEX = "Success! Order has been created with Order Number ([\\w-]+) and Total Price \\$(\\d+(?:\\.\\d{2})?)";
    private static final Pattern ORDER_CONFIRMATION_PATTERN = Pattern.compile(ORDER_CONFIRMATION_REGEX);

    private static final int ORDER_NUMBER_GROUP = 1;
    private static final int TOTAL_PRICE_GROUP = 2;

    public SystemUiDriver(WebDriver driver, String shopUrl) {
        this.shopPage = new ShopPage(driver, shopUrl);
    }

    @Override
    public void load() {
        shopPage.navigateToShop();
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