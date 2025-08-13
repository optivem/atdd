package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.channels.Channel;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelExtension;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpStubDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemApiDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriverContext;
import com.optivem.atdd.acceptancetests.shared.dsl.external.erp.ErpStubDsl;
import com.optivem.atdd.acceptancetests.shared.dsl.system.ShopDsl;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemUiDriver;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslContext;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslParamsFactory;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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

        var context = new DslContext();
        var paramsFactory = new DslParamsFactory(context);

        erpStubDriver = new ErpStubDriver(webClient);
        erpStub = new ErpStubDsl(paramsFactory, erpStubDriver);

        var uiDriver = new SystemUiDriver(seleniumDriver, baseUrl + "/shop");
        var apiDriver = new SystemApiDriver(baseUrl);
        var systemDriverContext = new SystemDriverContext(uiDriver, apiDriver);

        shop = new ShopDsl(paramsFactory, systemDriverContext);
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    public void shouldCompletePurchaseSuccessfully1() {
        erpStub.setupProduct("sku: ABC", "price: 2.50");
        shop.placeOrder("sku: ABC", "quantity: 5");
        shop.confirmOrder("totalPrice: 12.50");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    public void shouldCompletePurchaseSuccessfully2() {
        erpStub.setupProduct("sku: ABC", "price: 3.00");
        shop.placeOrder("sku: ABC", "quantity: 10");
        shop.confirmOrder("totalPrice: 30.00");
    }

    // TODO: VJ: DELETE
//    @ParameterizedTest
//    @MethodSource("purchaseParameters")
//    @Channel({ChannelType.UI, ChannelType.API})
//    void shouldCompletePurchaseSuccessfully(String sku, String price, String quantity, String totalPrice) {
//        erpStub.setupProduct("sku: " + sku, "price: " + price);
//        shop.placeOrder("sku: " + sku, "quantity: " + quantity);
//        shop.confirmOrder("totalPrice: " + totalPrice);
//    }
//
//    static Stream<org.junit.jupiter.params.provider.Arguments> purchaseParameters() {
//        return Stream.of(
//            org.junit.jupiter.params.provider.Arguments.of("ABC", "2.50", "5", "12.50"),
//            org.junit.jupiter.params.provider.Arguments.of("ABC", "3.00", "10", "30.00")
//        );
//    }
}