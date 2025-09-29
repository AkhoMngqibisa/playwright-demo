package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ASimplePlaywrightTest {

    @Test
    void shouldShowThePageTitle() {
        // Create a Playwright environment
        Playwright playwright = Playwright.create();

        // Create a browser
        Browser browser = playwright.chromium().launch();

        // Create a page
        Page page = browser.newPage();

        // Navigate to the page
        page.navigate("https://practicesoftwaretesting.com");

        // Fetch the page title
        String pageTitle = page.title();

        // Check what the title contains
        Assertions.assertTrue(pageTitle.contains("Practice Software Testing"));

    }
}
