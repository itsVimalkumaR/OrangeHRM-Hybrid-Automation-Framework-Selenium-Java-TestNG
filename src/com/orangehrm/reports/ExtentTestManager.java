package com.orangehrm.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Thread-safe ExtentTest manager.
 * Handles creation and retrieval of test-level ExtentTest instances.
 */
public class ExtentTestManager {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();

    /**
     * Start a new Extent test for the given test name and description.
     *
     * @param testName the name of the test
     * @param description short test description (optional)
     * @return the created ExtentTest instance
     */
    public static synchronized ExtentTest startTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        testMap.put(Thread.currentThread().getId(), test);
        return test;
    }

    /**
     * Get the ExtentTest instance associated with the current thread.
     */
    public static synchronized ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }

    /**
     * Flush the report after all tests are done.
     * This should be called once (usually in TestNG @AfterSuite or listener onFinish).
     */
    public static synchronized void flushReport() {
        try {
            extent.flush();
            System.out.println("INFO: Extent report flushed successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to flush Extent report - " + e.getMessage());
        }
    }
}
