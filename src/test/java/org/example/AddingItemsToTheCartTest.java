package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeAll;

import java.util.Arrays;

public class AddingItemsToTheCartTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType().LaunchOptions().setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
    }
}
