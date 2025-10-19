package com.orangehrm.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseOfPOM;

/**
 * Admin page navigation utilities.
 */
public class AdminPage extends BaseOfPOM {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AdminPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void goToAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(adminLink)).click();
    }

    public void openConfiguration() {
        wait.until(ExpectedConditions.elementToBeClickable(configurationLink)).click();
    }

    public void openLdapConfig() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapConfigLink)).click();
    }

    /**
     * Combined navigation to LDAP Configuration page.
     */
    public void navigateToLdapConfig() {
        goToAdmin();
        openConfiguration();
        openLdapConfig();
    }

    /**
     * A convenience method for tests that want to navigate to Users page (if present).
     * Adjust selectors if Users is a different element in your application.
     */
    public void navigateToUsers() {
        goToAdmin();
        // Example: if Users is under Admin > User Management > Users, add the clicks here
        // For now we keep a generic approach of opening admin then configuration then ldap
        openConfiguration();
        // If a specific users link exists, click it (placeholder)
    }
}
