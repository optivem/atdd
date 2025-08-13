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
    private final WebDriverWait wait;

    public SystemUiDriver(WebDriver driver, String shopUrl) {
        this.driver = driver;
        this.shopUrl = shopUrl;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void load() {
        driver.get(shopUrl);
    }

    public void submitOrder(String sku, String quantity) {
        driver.findElement(By.cssSelector("[aria-label='SKU']")).sendKeys(sku);
        driver.findElement(By.cssSelector("[aria-label='Quantity']")).sendKeys(quantity);
        driver.findElement(By.cssSelector("[aria-label='Place Order']")).click();
    }

    public void confirmTotalPriceEquals(String expectedTotalPrice) {
        var confirmationElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']"))
        );

        var confirmation = confirmationElement.getText();

        var matcher = Pattern.compile("Success! Total Price is \\$(\\d+(?:\\.\\d{2})?)")
                .matcher(confirmation);

        assertThat(matcher.find()).isTrue();
        assertThat(matcher.group(1)).isEqualTo(expectedTotalPrice);
    }
}