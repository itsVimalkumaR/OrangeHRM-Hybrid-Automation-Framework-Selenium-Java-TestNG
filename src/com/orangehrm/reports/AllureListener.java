package com.orangehrm.reports;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Allure listener that attaches screenshots on failure.
 * This class should not extend BaseTest (listener should be independent).
 */
public class AllureListener implements ITestListener {

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Object testClass = result.getInstance();
            // Try to reflectively obtain driver from test instance if available
            java.lang.reflect.Field driverField = null;
            try {
                driverField = testClass.getClass().getDeclaredField("driver");
                driverField.setAccessible(true);
                Object driverObj = driverField.get(testClass);
                if (driverObj instanceof org.openqa.selenium.TakesScreenshot) {
                    byte[] screenshot = ((TakesScreenshot) driverObj).getScreenshotAs(OutputType.BYTES);
                    saveScreenshot(screenshot);
                }
            } catch (NoSuchFieldException | IllegalAccessException ignore) {
                // cannot get driver - nothing to attach
            }
        } catch (Exception e) {
            // swallow
        }
    }
}
