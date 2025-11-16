package org.example.toolshop1;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

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
                .setHeadless(false).setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "disable-extensions"))
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
        record Product(String name, Double price) {
        }

        private static APIRequestContext requestContext;

        @BeforeAll
        static void setupRequestContext() {
            requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                    .setBaseURL("https://api.practicesoftwaretesting.com")
                    .setExtraHTTPHeaders(new HashMap<>() {
                        {
                            put("Accept", "application/json");
                        }
                    })
            );
        }

        static Stream<Product> products() {
            APIResponse response = requestContext.get("products?page=2");
            Assertions.assertThat(response.status()).isEqualTo(200);

            JsonObject jsonObject = new Gson().fromJson(response.text(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");

            return data.asList().stream().map(jsonElement -> {
                JsonObject productJsonObject = jsonElement.getAsJsonObject();
                return new Product(productJsonObject.get("name").getAsString(),
                        productJsonObject.get("price").getAsDouble());
            });
        }
    }
}
