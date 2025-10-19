package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

public class LdapTests_10 extends BaseTest{

	// Helper: clear field and enter text
    private void clearAndType(WebElement element, String value) {
        element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    // Helper: select dropdown using Robot
    private void selectDropdownOption(int pressCount) throws Exception {
        for (int i = 0; i < pressCount; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    // Helper: Click test connection and verify popup
    private void verifyConnectionPopup() {
        ldapPage.testConnectionButton.click();
        if (ldapPage.ConnPopUpTxt.isDisplayed()) {
            Reporter.log("Connection popup appeared: " + ldapPage.ConnPopUpTxt.getText(), true);
            ldapPage.connectionStatusCloseButton.click();
        } else {
            Reporter.log("Connection popup did not appear.", true);
        }
    }

    // Helper: Click Save and verify success message
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            if (ldapPage.SuccessSMS.isDisplayed()) {
                Reporter.log("Successfully Updated message is visible.", true);
            }
        } catch (Exception e) {
	        ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessFailed");
            Reporter.log("Successfully Updated message is NOT visible.", true);
        }
    }

    // TC_053
    @Test(priority = 1)
    public void verifyConnectionPopupForValidData() throws Exception {
        // fill data fields (Host, Port, etc.)
        clearAndType(ldapPage.HostTxtFields, excelUtil.getCellData(1, 0));
        clearAndType(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));
        ldapPage.portTxtFields.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        ldapPage.encryptionDropdown.click();
        selectDropdownOption(1);

        ldapPage.LdapImplemenDD.click();
        selectDropdownOption(1);

        // fill other fields...
        verifyConnectionPopup();
    }

    // TC_054
    @Test(priority = 2)
    public void verifySaveSuccessForWorkEmailToggle() throws Exception {
        clearAndType(ldapPage.HostTxtFields, excelUtil.getCellData(1, 0));
        clearAndType(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));
        ldapPage.portTxtFields.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        ldapPage.encryptionDropdown.click();
        selectDropdownOption(1);
        ldapPage.LdapImplemenDD.click();
        selectDropdownOption(1);

        // Fill fields, enable toggle, and click save
        ldapPage.workMailToggleBtn.click();
        verifySaveSuccess();
    }
}
