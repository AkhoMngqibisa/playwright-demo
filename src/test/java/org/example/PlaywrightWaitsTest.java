package org.example;

import com.microsoft.playwright.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class PlaywrightWaitsTest {

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

    @BeforeEach
    void setup() {
        browserContext = browser.newContext();
        page = browser.newPage();
        openHomePage();
    }

    @AfterAll
    static void teardownBrowser() {
        browser.close();
        playwright.close();
    }

    @AfterEach
    void tearDown() {
        browserContext.close();
    }

    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("Should show all product names")
    @Test
    void shouldShowAllProductNames() {
        List<String> productNames = page.getByTestId("product-name").allTextContents();
        Assertions.assertThat(productNames).contains("Pliers", "Bolt Cutters", "Hammer");
    }

    @DisplayName("Should show all product images")
    @Test
    void shouldShowAllProductImages() {
        List <String> productImages = page.locator(".card-img-top").all()
                .stream()
                .map(img -> img.getAttribute("alt"))
                .toList();

        Assertions.assertThat(productImages).contains("Pliers", "Bolt Cutters", "Hammer");
    }
}
