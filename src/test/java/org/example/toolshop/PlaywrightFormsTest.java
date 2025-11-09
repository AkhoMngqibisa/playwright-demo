package org.example.toolshop;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class PlaywrightFormsTest {

    @DisplayName("Interacting with text fields")
    @Nested
    class WhenInteractingWithTextFields {

        @BeforeEach
        void openContactPage(Page page) {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Input fields")
        @Test
        void findValues(Page page) {
            var firstNameField = page.getByLabel("First name");

            firstNameField.fill("Sarah-Jane");

            assertThat(firstNameField).hasValue("Sarah-Jane");
        }

        @DisplayName("Complete the form")
        @Test
        void completeForm(Page page) throws URISyntaxException {
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
        @ParameterizedTest
        @ValueSource(strings = {"First name","Last name","Email Address","Message"})
        void mandatoryFields(String fieldName, Page page) {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailAddressField = page.getByLabel("Email address");
            var subjectField = page.getByLabel("Subject");
            var messageField = page.getByLabel("Message");
            var sendButton = page.getByText("Send");

            // Fill in the field values
            firstNameField.fill("Sarah-Jane");
            lastNameField.fill("Smith");
            emailAddressField.fill("sjsmith@gmail.com");
            subjectField.selectOption("Payments");
            messageField.fill("Hello, world!");

            // Clear one of the fields
            page.getByLabel(fieldName).clear();

            sendButton.click();

            // Check the error message for that field
            var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName+" is required");

            assertThat(errorMessage).isVisible();
        }

    }
}
