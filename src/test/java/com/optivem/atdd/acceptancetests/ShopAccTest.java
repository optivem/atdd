package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.channels.Channel;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelContext;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelExtension;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpStubDriver;
import com.optivem.atdd.acceptancetests.shared.dsl.external.erp.ErpStubDsl;
import com.optivem.atdd.acceptancetests.shared.dsl.system.ShopDsl;
import com.optivem.atdd.acceptancetests.shared.drivers.system.UiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acc")
@ExtendWith(ChannelExtension.class)
public class ShopAccTest {

    @LocalServerPort
    private int port;

    @Value("${erp.url}")
    private String erpUrl;

    private WebDriver seleniumDriver;
    private String baseUrl;

    private WebClient webClient;

    private ErpStubDriver erpStubDriver;
    private ErpStubDsl erpStub;

    private ShopDsl shop;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;

        webClient = WebClient.create(erpUrl);

        erpStubDriver = new ErpStubDriver(webClient);
        erpStub = new ErpStubDsl(erpStubDriver);
        shop = new ShopDsl(new UiDriver(seleniumDriver, baseUrl + "/shop"));
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Test
    @Channel({ChannelType.UI, ChannelType.API})
    public void shouldCompletePurchaseSuccessfully() {
        // TODO: VJ: Implement
        var currentChannel = ChannelContext.get();
        if(currentChannel == null) {
            throw new RuntimeException("Current channel is null!!!");
        }


        erpStub.setupProduct("sku: ABC1001", "price: 2.50");
        shop.placeOrder("sku: ABC1001", "quantity: 5");
        shop.assertConfirmation("totalPrice: 12.50");
    }
}