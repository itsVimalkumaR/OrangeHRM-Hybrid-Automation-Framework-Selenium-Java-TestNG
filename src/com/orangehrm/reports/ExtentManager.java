package com.orangehrm.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports manager (Compatible with ExtentReports 5.x+)
 * Handles creation of timestamped report and global Extent instance.
 */
public class ExtentManager {

    private static ExtentReports extent;

    /**
     * Returns a single instance of ExtentReports (Singleton pattern).
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    /**
     * Creates a timestamped Extent HTML report with system/environment details.
     */
    private static ExtentReports createInstance() {
        try {
            // Timestamped report file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportDir = System.getProperty("user.dir") + File.separator + "reports";
            new File(reportDir).mkdirs(); // ensure folder exists
            String reportPath = reportDir + File.separator + "ExtentReport_" + timestamp + ".html";

            // Create Spark (HTML) Reporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
            sparkReporter.config().setReportName("OrangeHRM Functional Test Execution");
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

            // Attach to ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Add system info
            extent.setSystemInfo("Application", "OrangeHRM");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Vimal Kumar");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", "Chrome");

            System.out.println("INFO: Extent report initialized: " + reportPath);
            return extent;

        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize Extent Report - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
