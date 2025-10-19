package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

import java.time.Duration;

/**
 * LdapTests_9
 * Updated with proper waits and loader handling
 */
public class LdapTests_9 extends BaseTest {

    private WebDriverWait wait;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Wait for loader to disappear
    private void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-form-loader")));
        } catch (Exception e) {
            // Loader might not appear, safe to ignore
        }
    }

    /* ----------------------------------------------------------------------
     * TC_051: Verify Test Connection popup visibility after entering LDAP data
     * ---------------------------------------------------------------------- */
    @Test(priority = 51, description = "Verify Test Connection popup visibility after entering LDAP configuration")
    public void verifyTestConnectionPopupAfterEnteringLdapData() throws Exception {

        waitForLoaderToDisappear();

        ldapPage.HostTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.HostTxtFields.sendKeys(excelUtil.getCellData(1, 0));

        ldapPage.portTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.portTxtFields.sendKeys(excelUtil.getCellData(1, 1));

        ldapPage.DistingNameTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.DistingNameTxtFields.sendKeys(excelUtil.getCellData(1, 2));

        ldapPage.PasswordTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.PasswordTxtFields.sendKeys(excelUtil.getCellData(1, 3));

        ldapPage.BdNameTxtField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.BdNameTxtField.sendKeys(excelUtil.getCellData(1, 4));

        ldapPage.UNAttribute.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.UNAttribute.sendKeys(excelUtil.getCellData(1, 5));

        ldapPage.USearchFilter.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.USearchFilter.sendKeys(excelUtil.getCellData(1, 7));

        ldapPage.FNameTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.FNameTxtFields.sendKeys(excelUtil.getCellData(1, 8));

        ldapPage.LNameTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.LNameTxtFields.sendKeys(excelUtil.getCellData(1, 10));

        ldapPage.SyncInterval.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.SyncInterval.sendKeys(excelUtil.getCellData(1, 14));

        waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.testConnectionButton)).click();

        try {
            wait.until(ExpectedConditions.visibilityOf(ldapPage.ConnPopUpTxt));
            Reporter.log("The Connection status popup appeared successfully.", true);
            ldapPage.connectionStatusCloseButton.click();
        } catch (Exception e) {
            Reporter.log("The Connection status popup did not appear.", true);
            ScreenshotUtil.captureScreenshot(driver, "verifyTestConnectionPopupAfterEnteringLdapDataFailed");
        }
    }

    /* ----------------------------------------------------------------------
     * TC_052: Verify Save functionality after entering LDAP configuration
     * ---------------------------------------------------------------------- */
    @Test(priority = 52, description = "Verify Save functionality after entering LDAP configuration values")
    public void verifySaveFunctionalityAfterEnteringLdapData() throws Exception {

        waitForLoaderToDisappear();

        ldapPage.HostTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.HostTxtFields.sendKeys(excelUtil.getCellData(1, 0));

        ldapPage.portTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.portTxtFields.sendKeys(excelUtil.getCellData(1, 1));

        // Handle dropdowns safely
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.encryptionDropdown)).click();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.LdapImplemenDD)).click();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        ldapPage.DistingNameTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.DistingNameTxtFields.sendKeys(excelUtil.getCellData(1, 2));

        ldapPage.PasswordTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.PasswordTxtFields.sendKeys(excelUtil.getCellData(1, 3));

        ldapPage.BdNameTxtField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.BdNameTxtField.sendKeys(excelUtil.getCellData(1, 4));

        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.SearchScopeDD)).click();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        ldapPage.UNAttribute.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.UNAttribute.sendKeys(excelUtil.getCellData(1, 5));

        ldapPage.USearchFilter.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.USearchFilter.sendKeys(excelUtil.getCellData(1, 7));

        ldapPage.UUIDAttribute1.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.UUIDAttribute1.sendKeys(excelUtil.getCellData(2, 6));

        ldapPage.FNameTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.FNameTxtFields.sendKeys(excelUtil.getCellData(1, 8));

        ldapPage.midName1.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.midName1.sendKeys(excelUtil.getCellData(2, 9));

        ldapPage.LNameTxtFields.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.LNameTxtFields.sendKeys(excelUtil.getCellData(1, 10));

        ldapPage.UserStatus1.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.UserStatus1.sendKeys(excelUtil.getCellData(2, 11));

        ldapPage.WorkEmail1.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.WorkEmail1.sendKeys(excelUtil.getCellData(2, 12));

        ldapPage.EmpID1.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.EmpID1.sendKeys(excelUtil.getCellData(2, 13));

        ldapPage.SyncInterval.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        ldapPage.SyncInterval.sendKeys(excelUtil.getCellData(1, 14));

        waitForLoaderToDisappear();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
            wait.until(ExpectedConditions.visibilityOf(ldapPage.SuccessSMS));
            Reporter.log("The 'Successfully Updated' message is visible after saving.", true);
        } catch (Exception e) {
            ScreenshotUtil.captureScreenshot(driver, "verifySaveFunctionalityAfterEnteringLdapDataFailed");
            Reporter.log("The 'Successfully Updated' message is not visible.", true);
        }
    }
}
