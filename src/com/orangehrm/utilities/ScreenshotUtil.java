// ScreenshotUtil.java

package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

/**
 * Screenshot helper that creates reports/screenshots directory if missing.
 */
public class ScreenshotUtil {

	public static String captureScreenshot(WebDriver driver, String screenshotName) {
		if (driver == null)
			return null;
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String dir = System.getProperty("user.dir") + "/reports/screenshots";
			File outDir = new File(dir);
			if (!outDir.exists())
				outDir.mkdirs();

			String destPath = dir + "/" + screenshotName + "_" + timestamp + ".png";
			File dest = new File(destPath);
			FileUtils.copyFile(src, dest);
			Log.info("Screenshot saved: " + dest);
			return destPath;
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
			Log.error("Failed to capture screenshot: " + e.getMessage());
			return null;
		}
	}
}
