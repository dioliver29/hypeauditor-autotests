package com.hypeauditor;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.hypeauditor.config.CredentialsConfig;
import com.hypeauditor.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Tag("systemProperties")
public class TestBase {

    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    static String login = config.login();
    static String password = config.password();
    static String influencerNickName = "olymarkes";
    static String influencerName = "Ольга Маркес";

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.baseUrl = System.getProperty("baseUrl"); // https://hypeauditor.com/
        Configuration.remote = String.format("https://%s:%s"+"@"+ System.getProperty("remoteSelenideUrl") +"/wd/hub", login, password);
        Configuration.browser = System.getProperty("browser");
        Configuration.browserVersion = System.getProperty("browserVer");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}
