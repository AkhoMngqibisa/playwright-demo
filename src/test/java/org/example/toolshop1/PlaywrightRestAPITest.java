package org.example.toolshop1;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;

import java.util.Arrays;

@Execution(ExecutionMode.SAME_THREAD)
public class PlaywrightRestAPITest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setupBrowser() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false).setArgs(Arrays.asList("--no-sandbox", "--disable-gpu","disable-extensions"))
        );
    }

    @BeforeEach
    void setup() {
        browserContext = browser.newContext();
        page = browserContext.newPage();

        page.navigate("https://practicesoftwaretesting.com");
        page.getByPlaceholder("Search").waitFor();
    }

    @AfterEach
    void tearDown() {
        browserContext.close();
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @Nested
    class MakingAPICalls {

    }
}
