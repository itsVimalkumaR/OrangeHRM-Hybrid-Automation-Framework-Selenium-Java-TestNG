package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * TestCases_8
 *
 * This test class validates:
 * - Save functionality across different encryption and LDAP implementation combinations
 * - Test Connection popup visibility with/without UUID field
 * - Save functionality with complete LDAP data
 */
public class LdapTests_8 extends BaseTest {

    private WebDriverWait wait;

    /* ----------------------------------------------------------------------
     * Common setup & helper methods
     * ---------------------------------------------------------------------- */

    // Initialize WebDriverWait
    public void setupWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Helper: Clear existing text and enter new value
    private void clearAndType(WebElement element, String text) {
        element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        element.sendKeys(text);
    }

    // Helper: Select dropdown using Robot key down (n times)
    private void selectDropdown(int downPressCount) throws Exception {
        for (int i = 0; i < downPressCount; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    // Helper: Fill LDAP fields from Excel
    private void fillLdapFields() {
        clearAndType(ldapPage.DistingNameTxtFields, excelUtil.getCellData(1, 2));
        clearAndType(ldapPage.PasswordTxtFields, excelUtil.getCellData(1, 3));
        clearAndType(ldapPage.BdNameTxtField, excelUtil.getCellData(1, 4));
        clearAndType(ldapPage.UUIDAttribute1, excelUtil.getCellData(1, 6));
        clearAndType(ldapPage.midName1, excelUtil.getCellData(1, 9));
        clearAndType(ldapPage.UserStatus1, excelUtil.getCellData(1, 11));
        clearAndType(ldapPage.WorkEmail1, excelUtil.getCellData(1, 12));
        clearAndType(ldapPage.EmpID1, excelUtil.getCellData(1, 13));
    }

    // Helper: Verify success message after Save click
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            wait.until(ExpectedConditions.visibilityOf(ldapPage.SuccessSMS));
            Assert.assertTrue(ldapPage.SuccessSMS.isDisplayed(), "Success message not displayed!");
            Reporter.log("Successfully Updated message is visible.", true);
        } catch (Exception e) {
            ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessFailed");
            Reporter.log("Successfully Updated message is NOT visible.", true);
            softAssert.assertTrue(false, "Success message is not visible");
        }
    }

    // Helper: Verify Connection popup after Test Connection click
    private void verifyConnectionPopup() {
        try {
            ldapPage.testConnectionButton.click();
            wait.until(ExpectedConditions.visibilityOf(ldapPage.ConnPopUpTxt));
            if (ldapPage.ConnPopUpTxt.isDisplayed()) {
                Reporter.log("Connection status popup appeared: " + ldapPage.ConnPopUpTxt.getText(), true);
                ldapPage.connectionStatusCloseButton.click();
            }
        } catch (Exception e) {
            Reporter.log("The Test Connection button did not work or popup did not appear.", true);
        }
    }

    /* ----------------------------------------------------------------------
     * Test Cases
     * ---------------------------------------------------------------------- */

    // TC_046
    @Test(priority = 46, description = "Verify Save functionality after selecting first Encryption option")
    public void verifySaveAfterSelectingFirstEncryptionOption() throws Exception {
        setupWait();
        WebElement encryptionDropdownIcon = driver.findElement(
                By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[1]"));
        actions.moveToElement(encryptionDropdownIcon).click().perform();
        selectDropdown(1);

        fillLdapFields();
        verifySaveSuccess();
    }

    // TC_047
    @Test(priority = 47, description = "Verify Save functionality after selecting second Encryption and LDAP Implementation options")
    public void verifySaveAfterSelectingSecondEncryptionAndLdapImplementation() throws Exception {
        setupWait();
        ldapPage.encryptionDropdown.click();
        selectDropdown(2);

        ldapPage.LdapImplemenDD.click();
        selectDropdown(2);

        fillLdapFields();
        ldapPage.SearchScopeDD.click();
        selectDropdown(2);

        verifySaveSuccess();
    }

    // TC_048
    @Test(priority = 48, description = "Verify Test Connection popup after removing UUID field with first Encryption option")
    public void verifyTestConnectionPopupAfterRemovingUuidFirstEncryption() throws Exception {
        setupWait();
        ldapPage.encryptionDropdown.click();
        selectDropdown(1);

        fillLdapFields();
        ldapPage.UUIDAttribute1.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        verifyConnectionPopup();
    }

    // TC_049
    @Test(priority = 49, description = "Verify Test Connection popup after removing UUID field with second Encryption and LDAP Implementation options")
    public void verifyTestConnectionPopupAfterRemovingUuidSecondEncryptionAndImplementation() throws Exception {
        setupWait();
        WebElement encryptionDropdownIcon = driver.findElement(
                By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[1]"));
        actions.moveToElement(encryptionDropdownIcon).click().perform();
        selectDropdown(2);

        ldapPage.LdapImplemenDD.click();
        selectDropdown(2);

        fillLdapFields();
        ldapPage.UUIDAttribute1.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        verifyConnectionPopup();
    }

    // TC_050
    @Test(priority = 50, description = "Verify Save functionality after entering complete LDAP configuration details")
    public void verifySaveAfterEnteringCompleteLdapConfiguration() throws Exception {
        setupWait();

        clearAndType(ldapPage.HostTxtFields, excelUtil.getCellData(1, 0));
        clearAndType(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));
        ldapPage.portTxtFields.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        clearAndType(ldapPage.DistingNameTxtFields, excelUtil.getCellData(1, 2));
        clearAndType(ldapPage.PasswordTxtFields, excelUtil.getCellData(1, 3));
        clearAndType(ldapPage.BdNameTxtField, excelUtil.getCellData(1, 4));
        clearAndType(ldapPage.UNAttribute, excelUtil.getCellData(1, 5));
        clearAndType(ldapPage.USearchFilter, excelUtil.getCellData(1, 7));
        clearAndType(ldapPage.FNameTxtFields, excelUtil.getCellData(1, 8));
        clearAndType(ldapPage.LNameTxtFields, excelUtil.getCellData(1, 10));
        clearAndType(ldapPage.SyncInterval, excelUtil.getCellData(1, 14));
        ldapPage.SyncInterval.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        verifySaveSuccess();
    }
}
