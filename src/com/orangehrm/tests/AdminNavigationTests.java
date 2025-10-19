package com.orangehrm.tests;

import org.testng.Assert;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * Basic admin navigation test
 */
public class AdminNavigationTests extends BaseTest {

//	@Test(enabled = false, description = "Navigate to Admin section")
	public void testNavigateToUsers() throws InterruptedException {
		AdminPage adminPage = new AdminPage(driver);
		adminPage.goToAdmin();

        Thread.sleep(5000);
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "LDAPConfigPage");
        System.out.println("Screenshot saved at: " + screenshotPath);
		Assert.assertTrue(adminPage.adminLink.isDisplayed(), "Admin link should be displayed");
		
	}
}
