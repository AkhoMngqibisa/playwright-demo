package org.example.toolshop1;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

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
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"))
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

    @DisplayName("Locate elements by text")
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

        @DisplayName("Locate an element by title")
        @Test
        void byTitle() {
            page.getByTitle("Practice Software Testing - Toolshop").click();
        }
    }

    @DisplayName("Locate elements by placeholders and labels")
    @Nested
    class LocatingElementsByPlaceholdersAndLabels {
        @BeforeEach
        void openTheCataloguePage() {
            openPage();
        }

        @DisplayName("Locate an element by a placeholder")
        @Test
        void byPlaceholder() {
            page.getByPlaceholder("Search").fill("Pliers");
            PlaywrightAssertions.assertThat(page.getByPlaceholder("Search")).isVisible();
        }

        @DisplayName("Locate an element by a label")
        @Test
        void byLabel() {
            page.getByLabel("Search").fill("Akhona");
            PlaywrightAssertions.assertThat(page.getByLabel("Search")).isVisible();
        }
    }

    @DisplayName("Locate elements by role")
    @Nested
    class LocatingElementsByRole {
        @BeforeEach
        void openTheCataloguePage() {
            openPage();
        }

        @DisplayName("Locate element by button")
        @Test
        void byButton() {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Categories")).click();
            PlaywrightAssertions.assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Categories"))).isVisible();
        }

        @DisplayName("Locate element by heading")
        @Test
        void byHeader() {
            PlaywrightAssertions.assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Price Range"))).isVisible();
        }

        @DisplayName("Locate element by heading level")
        @Test
        void byHeaderLevel() {
            PlaywrightAssertions.assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Price Range").setLevel(4))).isVisible();
        }

        @DisplayName("Locate element by checkbox")
        @Test
        void byCheckbox() {
            Locator hammerCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Hammer"));
            hammerCheckbox.check();

            PlaywrightAssertions.assertThat(hammerCheckbox).isChecked();
        }

    }

    @DisplayName("Locate elements by test Id")
    @Nested
    class LocatingElementsByTestID {

        @BeforeAll
        static void setTestId() {
            playwright.selectors().setTestIdAttribute("data-test");
        }

        @BeforeEach
        void openTheCataloguePage() {
            openPage();
        }

        @DisplayName("Locate element using data-test field")
        @Test
        void byTestId() {
            PlaywrightAssertions.assertThat(page.getByTestId("search-submit")).isVisible();
        }
    }

    @DisplayName("Locate elements by css")
    @Nested
    class LocatingElementsByCSS {

        @BeforeEach
        void openTheContactPage() {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("By Id")
        @Test
        void byId() {
            // Locate the first name element by Id
            page.locator("#first_name").fill("Akhona Mngqibisa");
            PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("Akhona Mngqibisa");
        }

        @DisplayName("By CSS class")
        @Test
        void byCSSClass() {
            page.locator("#first_name").fill("Akhona Mngqibisa");
            page.locator(".btnSubmit").click();

            List<String> alertMessage = page.locator(".alert").allTextContents();
            Assertions.assertFalse(alertMessage.isEmpty());
        }

        @DisplayName("By attribute")
        @Test
        void byAttribute() {
            page.locator("input[placeholder='Your last name *']").fill("Mngqibisa");
            PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Mngqibisa");
        }
    }
}
