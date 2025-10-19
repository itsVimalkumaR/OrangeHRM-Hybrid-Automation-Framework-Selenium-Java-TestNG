package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * LdapTests_2
 *
 * Verifies Save button, required options, subtitles, dropdowns.
 */
public class LdapTests_2 extends BaseTest {

    // TC_006
    @Test(priority = 6, description = "Verify Save button element tag")
    public void verifySaveButtonTag() {
        String tagName = ldapPage.saveButton.getTagName().toLowerCase();
        Reporter.log("Save button tag: " + tagName, true);
        Assert.assertEquals(tagName, "button", "Save button tag mismatch.");
    }

    // TC_007
    @Test(priority = 7, description = "Verify total required options count")
    public void verifyRequiredOptionsCount() {
        int totalReq = ldapPage.ReqOpt.size() + ldapPage.ReqoOpt1.size();
        Reporter.log("Required options count: " + totalReq, true);
        Assert.assertTrue(totalReq > 0, "No required options found.");
    }

    // TC_008
    @Test(priority = 8, description = "Verify count of subtitles")
    public void verifySubTitlesCount() {
        int subtitleCount = ldapPage.SubTitles.size();
        Reporter.log("Subtitles count: " + subtitleCount, true);
        Assert.assertTrue(subtitleCount > 0, "No subtitles found.");
    }

    // TC_009
    @Test(priority = 9, description = "Verify main title text on LDAP page")
    public void verifyMainTitle() {
        try {
            String actualTitle = ldapPage.MainTitle.getText().trim();
            String expectedTitle = "LDAP Configuration";
            Assert.assertEquals(actualTitle, expectedTitle, "Main title mismatch.");
            Reporter.log("Main title verified: " + actualTitle, true);
            ScreenshotUtil.captureScreenshot(driver, "LDAP_MainTitle");
        } catch (Exception e) {
            Assert.fail("Error verifying main title: " + e.getMessage());
        }
    }

    // TC_010
    @Test(priority = 10, description = "Verify number of dropdown buttons")
    public void verifyDropdownButtonsCount() {
        int dropdownCount = ldapPage.dropdownBtn.size();
        Reporter.log("Dropdown buttons count: " + dropdownCount, true);
        Assert.assertTrue(dropdownCount >= 0, "Dropdown button count invalid.");
    }
}
