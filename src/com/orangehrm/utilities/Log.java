package com.orangehrm.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.File;

/**
 * Log utility wrapper for Log4j.
 */
public class Log {
    protected static final Logger logger = Logger.getLogger(Log.class);

    static {
        try {
            String log4jPath = System.getProperty("user.dir") + File.separator + File.separator + "config" + File.separator + "log4j.properties";
            System.out.println("--> " + log4jPath + "<--");
            PropertyConfigurator.configure(log4jPath);

            // Console confirmation message
            System.out.println("INFO: Log4j configured from: " + log4jPath);
            logger.info("Log4j successfully initialized from: " + log4jPath);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to configure Log4j: " + e.getMessage());
        }
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void fatal(String msg) {
        logger.fatal(msg);
    }
}