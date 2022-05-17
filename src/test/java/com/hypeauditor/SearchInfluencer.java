package com.hypeauditor;


import com.codeborne.selenide.logevents.SelenideLogger;
import com.hypeauditor.helpers.Attach;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.hypeauditor.TestBase.influencerName;
import static com.hypeauditor.TestBase.influencerNickName;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;

public class SearchInfluencer extends TestBase{
    @Test
    @Owner("stikheeva")
    @Description("Open main page and search for Influencer")
    @DisplayName("Successful search for Influencer")
    void searchInfluencer() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Open {baseUrl}", () -> open("baseUrl"));

        step("Find 'Influencer Analytics'", () -> {
            $$(".button-container")
                    .first()
                    .$(byText("Influencer Analytics"))
                    .hover();

        });

        step("Check the description of 'Influencer Analytics'", () -> {
            $(".hfTooltipShow")
                    .shouldHave(text("Analyze social media influencers and get statistics for any account and channel"));
        });
        step("Click search panel 'Check influencer'", () ->
                $$(".buttons-landing--desktop")
                .first()
                .$("a")
                .click());

        step("Pre-find {influencerNickName}", () -> {
            $("[type=search]")
                    .setValue(influencerNickName);

        });

        step("Set search for 'Instagram'", () ->
                $(".--tabs")
                .$(".kyb-suggest__tabs")
                .$("div", 1)
                .click());

        step("Check the Influencer name = {influencerName}", () -> {
            $(".kyb-suggest--fullname")
                    .shouldHave(text(influencerName));
        });

        step("Open Influencer = 'Ольга Маркес'", () ->
                $(".--tabs")
                .$(".kyb-search-bar--results")
                .$("div", 1)
                .click());
    }

    @Test
    @DisplayName("Page title should have header text")
    void titleTest() {
        step("Open url 'https://hypeauditor.com/'", () ->
                open("https://hypeauditor.com/"));

        step("Page title should have text '100% AI-Powered Influencer Marketing Platform | HypeAuditor'", () -> {
            String expectedTitle = "100% AI-Powered Influencer Marketing Platform | HypeAuditor";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @DisplayName("Page console log should not have errors")
    void consoleShouldNotHaveErrorsTest() {
        step("Open url 'https://hypeauditor.com/'", () ->
                open("https://hypeauditor.com/"));

        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = Attach.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}
