package org.example.toolshop.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ContactForm {
    private final Page page;
    private Locator firstNameField;
    private Locator lastNameField;
    private Locator emailAddressField;
    private Locator subjectField;
    private Locator messageField;
    private Locator attachmentField;

    public ContactForm(Page page) {
        this.page = page;
        this.firstNameField = page.getByLabel("First name");
        this.lastNameField = page.getByLabel("Last name");
        this.emailAddressField = page.getByLabel("Email address");
        this.subjectField = page.getByLabel("Subject");
        this.messageField = page.getByLabel("Message");
        this.attachmentField = page.getByLabel("Attachment");
    }

    public void setFirstName(String firstName) {

    }
}
