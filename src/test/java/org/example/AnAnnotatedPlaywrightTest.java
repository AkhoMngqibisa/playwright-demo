package org.example;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@UsePlaywright(AnAnnotatedPlaywrightTest.MyOptions.class)
public class AnAnnotatedPlaywrightTest {

    public static class MyOptions implements OptionsFactory {

        @Override
        public Options getOptions() {
            return null;
        }
    }

    @Test
    void shouldShowThePageTitle(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        String pageTitle = page.title();
        Assertions.assertTrue(pageTitle.contains("Practice Software Testing"));
    }

    @Test
    void shouldShowSearchTermsInTheTitle(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        int matchingSearchResults = page.locator(".card").count();
        Assertions.assertTrue(matchingSearchResults > 0);
    }
}
