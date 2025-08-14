package com.optivem.atdd.acceptancetests;

import com.optivem.atdd.acceptancetests.shared.channels.Channel;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelContext;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelExtension;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;
import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpStubDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemApiDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriverContext;
import com.optivem.atdd.acceptancetests.shared.dsl.external.erp.ErpStubDsl;
import com.optivem.atdd.acceptancetests.shared.dsl.system.ShopDsl;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemUiDriver;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslContext;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslParamsFactory;

import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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

    private SystemDriverContext systemDriverContext;

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
        var drivers = new HashMap<ChannelType, SystemDriver>();
        drivers.put(ChannelType.UI, uiDriver);
        drivers.put(ChannelType.API, apiDriver);

        this.systemDriverContext = new SystemDriverContext(drivers);

        shop = new ShopDsl(paramsFactory, systemDriverContext);
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    static Stream<Arguments> purchaseParameters() {
        return Stream.of(
            Arguments.of("2.50", "5", "12.50"),
            Arguments.of("3.00", "10", "30.00"),
            Arguments.of("5.00", "10", "50.00")
        );
    }

    @Channel({ChannelType.UI, ChannelType.API})
    @TestTemplate
    @MethodSource("purchaseParameters")
    void shouldCompletePurchaseSuccessfully(String price, String quantity, String totalPrice) {
        erpStub.setupProduct("sku: ABC", "price: " + price);
        shop.placeOrder("orderNumber: ORD-1001", "sku: ABC", "quantity: " + quantity);
        shop.confirmOrder("orderNumber: ORD-1001", "totalPrice: " + totalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    public void shouldCompletePurchaseSuccessfullyThisIsSomePlainTest() {
        erpStub.setupProduct("sku: ABC", "price: 2.50");
        shop.placeOrder("orderNumber: ORD-1001", "sku: ABC", "quantity: 5");
        shop.confirmOrder("orderNumber: ORD-1001", "totalPrice: 12.50");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    public void shouldGenerateOrderNumber() {
        erpStub.setupProduct("sku: ABC");
        shop.placeOrder("orderNumber: ORD-1001", "sku: ABC");
        shop.confirmOrderNumberGenerated("orderNumber: ORD-1001");
    }

}


