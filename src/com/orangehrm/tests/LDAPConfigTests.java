package com.orangehrm.tests;

import org.testng.Assert;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.LdapConfigPage;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * LDAP configuration navigation test
 */
public class LDAPConfigTests extends BaseTest {

//    @Test(enabled = false, description = "Open LDAP Configuration through Admin > Configuration > LDAP Configuration")
    public void testOpenLdapConfig() throws InterruptedException {
        AdminPage adminPage = new AdminPage(driver);
        adminPage.navigateToLdapConfig();
        
        LdapConfigPage ldapPage = new LdapConfigPage(driver);
        boolean onLdap = ldapPage.isOnLdapPage();
        
        Thread.sleep(5000);
        // Take screenshot whether success or failure
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "LDAPConfigPage");
        System.out.println("Screenshot saved at: " + screenshotPath);
        
        Assert.assertTrue(onLdap, "LDAP Configuration page should be visible after navigation");
    }
}
