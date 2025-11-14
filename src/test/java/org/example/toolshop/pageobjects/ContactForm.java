package org.example.toolshop.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;

public class ContactForm {
    private final Page page;
    private Locator firstNameField;
    private Locator lastNameField;
    private Locator emailAddressField;
    private Locator subjectField;
    private Locator messageField;
    private Locator attachmentField;
    private Locator sendButton;

    public ContactForm(Page page) {
        this.page = page;
        this.firstNameField = page.getByLabel("First name");
        this.lastNameField = page.getByLabel("Last name");
        this.emailAddressField = page.getByLabel("Email address");
        this.subjectField = page.getByLabel("Subject");
        this.messageField = page.getByLabel("Message");
        this.attachmentField = page.getByLabel("Attachment");
        this.sendButton = page.getByText("Send");
    }

    public void setFirstName(String firstName) {
        this.firstNameField.fill(firstName);
    }

    public void setLastName(String lastName) {
        this.lastNameField.fill(lastName);
    }

    public void setMessage(String message) {
        this.messageField.fill(message);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddressField.fill(emailAddress);
    }

    public void setSubject(String subject) {
        this.subjectField.selectOption(subject);
    }

    public void setAttachment(Path fileToUpload) {
        page.setInputFiles("#attachment", fileToUpload);
    }

    public void submitForm() {
        sendButton.click();
    }

    public String getAlertMessage() {
        return page.getByRole(AriaRole.ALERT).textContent();
    }

}
