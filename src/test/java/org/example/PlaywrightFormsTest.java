package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        browser.close();
        playwright.close();
    }

    @DisplayName("Interacting with text fields")
    @Nested
    class WhenInteractingWithTextFields {

        @BeforeEach
        void openContactPage() {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Input fields")
        @Test
        void findValues() {
            var firstNameField = page.getByLabel("First name");

            firstNameField.fill("Sarah-Jane");

            assertThat(firstNameField).hasValue("Sarah-Jane");
        }

        @DisplayName("Complete the form")
        @Test
        void completeForm() throws URISyntaxException {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailAddressField = page.getByLabel("Email address");
            var subjectField = page.getByLabel("Subject");
            var messageField = page.getByLabel("Message");
            var attachmentField = page.getByLabel("Attachment");

            firstNameField.fill("Sarah-Jane");
            lastNameField.fill("Smith");
            emailAddressField.fill("sjsmith@gmail.com");
            subjectField.selectOption("Payments");
            messageField.fill("Hello, world!");

            Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/File to Upload.txt").toURI());
            page.setInputFiles("#attachment", fileToUpload);

            assertThat(firstNameField).hasValue("Sarah-Jane");
            assertThat(lastNameField).hasValue("Smith");
            assertThat(emailAddressField).hasValue("sjsmith@gmail.com");
            assertThat(subjectField).hasValue("payments");
            assertThat(messageField).hasValue("Hello, world!");

            String uploadedFile = attachmentField.inputValue();
            org.assertj.core.api.Assertions.assertThat(uploadedFile).endsWith("File to Upload.txt");

        }

        @DisplayName("Mandatory fields")
        @Test
        void mandatoryFields() {
        }

    }
}
