package com.orangehrm.reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("INFO: Test Suite Started - " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("INFO: Test Suite Finished - " + context.getName());
        ExtentTestManager.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription() != null
                ? result.getMethod().getDescription()
                : "No description provided.";

        System.out.println("INFO: Starting Test - " + testName);
        ExtentTestManager.startTest(testName, description)
                         .log(Status.INFO, "Test Started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("PASS: " + testName + " passed.");
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("FAIL: " + testName + " failed.");

        ExtentTestManager.getTest().log(Status.FAIL,
                "Test Failed: " + testName +
                "<br>Exception: " + result.getThrowable());

        // ✅ Corrected: Pass WebDriver to ScreenshotUtil
        try {
            WebDriver driver = ((BaseTest) result.getInstance()).getDriver();
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, testName);
            ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to attach screenshot for " + testName + ": " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("SKIP: " + testName + " skipped.");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped: " + testName);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional
    }
}