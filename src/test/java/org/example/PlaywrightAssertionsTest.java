package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class PlaywrightAssertionsTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"))
        );
    }

    @AfterAll
    static void teardownBrowser() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void setup() {
        browserContext = browser.newContext();
        page = browser.newPage();
    }

    @AfterEach
    void tearDown() {
        browserContext.close();
    }

    @DisplayName("Making assertions about the contents of a field")
    @Nested
    class LocatingElementsUsingCSS {
        @BeforeEach
        void openContactPage() {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Checking the value of a field")
        @Test
        void filedValues() {

        }
    }

}
