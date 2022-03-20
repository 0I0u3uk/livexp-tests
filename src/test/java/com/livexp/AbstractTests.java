package com.livexp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.livexp.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class AbstractTests {

    protected static TestConfig config = ConfigFactory.create(TestConfig.class);

    private static void prepareBrowser(String browserName) {
        switch (browserName.toLowerCase(Locale.ROOT)) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                break;
            default:
                throw new IllegalArgumentException(String.format("Sorry we don't support %s browser", browserName));
        }
    }

    @BeforeAll
    public static void beforeAll() {
        closeWebDriver();
        Configuration.baseUrl = config.url();
        prepareBrowser(config.browser());
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }
}
