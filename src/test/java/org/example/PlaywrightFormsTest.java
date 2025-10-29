package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class PlaywrightFormsTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    Page page;

    @BeforeAll
    static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
    }

    @BeforeEach
    void setUpBrowserContext() {
        browserContext = browser.newContext();
        page = browser.newPage();
    }

    @AfterEach
    void tearDownBrowserContext() {
        browserContext.close();
    }

    @AfterAll
    static void tearDownBrowser() {
        playwright.close();
        browser.close();
    }

    @DisplayName("Interacting with text fields")
    @Nested
    class WhenInteractingWithTextFields {

    }
}
