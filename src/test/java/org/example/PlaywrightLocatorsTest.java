package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class PlaywrightLocatorsTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    public static void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox","--disable-gpu","--disable-extensions"))
        );
    }

    @BeforeEach
    void setup() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterEach
     void closeContext() {
        browserContext.close();
    }

    @AfterAll
    public static void tearDown() {
        browser.close();
        playwright.close();
    }

    private void openPage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("Locating elements by text")
    @Nested
    class LocatingElementsByText {

        @BeforeEach
        void openTheCatalogPage() {
            openPage();
        }

        @DisplayName("Locating an element by text contents")
        @Test
        void byText() {
            page.getByText("Bolt Cutters").click();
            PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
        }

        @DisplayName("Locating an element by alt contents")
        @Test
        void byAltText() {
            page.getByAltText("Combination Pliers").click();
            PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
        }

        @DisplayName("Locate ab element by title")
        @Test
        void byTitle() {
            page.getByTitle("Practice Software Testing - Toolshop").click();
        }
    }
}
