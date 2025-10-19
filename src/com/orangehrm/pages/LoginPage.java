package com.orangehrm.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseOfPOM;

/**
 * Login page actions.
 */
public class LoginPage extends BaseOfPOM {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameInputField)).clear();
        usernameInputField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInputField)).clear();
        passwordInputField.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isLoggedIn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(profileMenu));
            return profileMenu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(profileMenu)).click();
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        } catch (Exception e) {
            // swallow - logout best-effort
        }
    }
}
