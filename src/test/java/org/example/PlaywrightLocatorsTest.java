package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeAll;

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
}
