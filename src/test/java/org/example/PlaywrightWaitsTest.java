package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlaywrightWaitsTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setupBrowser() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
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

    @DisplayName("Waiting for state")
    @Nested
    class WaitingForState {

        @BeforeEach
        void openHomePage() {
            page.navigate("https://practicesoftwaretesting.com");
            page.waitForSelector(".card-img-top");
        }

        @DisplayName("Should show all product names")
        @Test
        void shouldShowAllProductNames() {
            List<String> productNames = page.getByTestId("product-name").allInnerTexts();
            Assertions.assertThat(productNames).contains("Pliers", "Bolt Cutters", "Hammer");
        }

        @DisplayName("Should show all product images")
        @Test
        void shouldShowAllProductImages() {
            List<String> productImages = page.locator(".card-img-top").all()
                    .stream()
                    .map(img -> img.getAttribute("alt"))
                    .toList();

            Assertions.assertThat(productImages).contains("Pliers", "Bolt Cutters", "Hammer");
        }
    }

    @DisplayName("Automatic Waits")
    @Nested
    class AutomaticWaits {
        @BeforeEach
        void openHomePage() {
            page.navigate("https://practicesoftwaretesting.com");
        }

        @Test
        @DisplayName("Should wait for the filter checkbox options to appear before clicking")
        void shouldWaitForTheFilterCheckboxes() {
            var screwdriverFilter = page.getByLabel(" Screwdriver ");
            screwdriverFilter.click();

            assertThat(screwdriverFilter).isChecked();
        }

        @DisplayName("Should filter products by category")
        @Test
        void shouldFilterProductsByCategory() {
            page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();
            page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();
            page.waitForSelector(".card", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));

            var filteredProducts = page.getByTestId("product-name").allInnerTexts();

            assertFalse(filteredProducts.isEmpty());
            Assertions.assertThat(filteredProducts).contains("Sheet Sander", "Belt Sander", "Random Orbit Sander");
        }
    }

}
