# OrangeHRM Hybrid Automation Framework (Selenium + Java + TestNG)

## Project Overview

The OrangeHRM Hybrid Automation Framework is a modular, data-driven, and keyword-enabled automation testing framework developed using Selenium WebDriver, Java, and TestNG — without Maven.

It is designed to automate functional and regression testing of the OrangeHRM web application. The framework is lightweight, scalable, and easy to configure manually with JAR dependencies. It combines the best practices of Page Object Model (POM), Data-Driven, and Keyword-Driven testing approaches.

## Key Features

- **Hybrid Framework:** Combines Data-Driven, Keyword-Driven, and POM design patterns.

- **Modular Design:** Separates page objects, utilities, test data, and configuration.

- **Data-Driven Testing:** Reads test data dynamically from Excel using Apache POI.

- **Cross-Browser Execution:** Supports Chrome, Edge, and Firefox.

- **TestNG Integration:** Enables grouping, prioritization, and parallel testing.

- **Detailed Reports:** Generates HTML reports with TestNG and Extent Reports.

- **Logging:** Uses Log4j for runtime logging and debugging.

- **Easy Integration:** Can be integrated with Jenkins or any CI/CD pipeline manually.

---

## Tech Stack
| Component | Technology | 
|------------|-------------|
| **Programming Language** | Java | 
| **Automation Tool** | Selenium WebDriver | 
| **Testing Framework** | TestNG |
| **Design Pattern** | Page Object Model (POM) | 
| **Data Source** | Excel (Apache POI) |
| **Reporting** | Extent Reports / Allure Reports | 
| **Logging** | Log4j |
| **Build / Dependency Management** | Manual(non-Maven project) |
| **CI/CD Integration** |	Jenkins, GitHub Actions (optional) |

---

## Folder Structure / Layout
```graphql
orangehrm-hybrid-automation-framework/
│
├── drivers/                     # Browser drivers (chromedriver.exe, geckodriver.exe, etc.)
├── lib/                         # All external JAR dependencies (Selenium, TestNG, etc.)
├── src/
│   ├── com.orangehrm.base/      # Base classes (BaseTest, DriverFactory)
│   ├── com.orangehrm.pages/     # Page Object classes
│   ├── com.orangehrm.tests/     # TestNG test classes
│   ├── com.orangehrm.utilities/ # Utilities (Excel, ConfigReader, Log, etc.)
│   └── com.orangehrm.reports/   # Report generator and listeners
│
├── testdata/                    # Excel sheets for test data
│   └── testdata.xlsx
│
├── config/
│   └── config.properties         # Environment & application settings
│
├── reports/                      # Extent/TestNG reports output
├── logs/                         # Log files (automation.log)
├── testng.xml                    # Test suite configuration
├── README.md                     # Project documentation
└── run.bat                       # Optional batch file for executing tests
```

---

## Installation & Setup
**1. Install Prerequisites**

Ensure the following tools are installed on your machine:

| Tool                                 | Version               | Download Link                                                                        |
| ------------------------------------ | --------------------- | ------------------------------------------------------------------------------------ |
| **Java JDK**                         | 11 or higher          | [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) |
| **TestNG Plugin (Eclipse/IntelliJ)** | Latest                | Built-in Marketplace                                                                 |
| **Selenium WebDriver JARs**          | 4.x                   | [Selenium Downloads](https://www.selenium.dev/downloads/)                            |
| **Apache POI JARs**                  | 5.x                   | [Apache POI](https://poi.apache.org/download.html)                                   |
| **Log4j JARs**                       | 2.x                   | [Log4j Downloads](https://logging.apache.org/log4j/2.x/download.html)                |
| **Extent Reports JARs**              | 5.x                   | [Extent Reports GitHub](https://github.com/extent-framework/extentreports-java)      |
| **Web Drivers**                      | Chrome, Firefox, Edge | [WebDriver Binaries](https://chromedriver.chromium.org/downloads)                    |


**2. Project Setup**

1. Create a new Java Project in **Eclipse** or **IntelliJ IDEA**.

2. Copy the project folder structure shown above.

3. Copy all required `.jar` files into the `lib/` directory.

4. Required JAR file names: Download the following dependencies for your non-Maven project setup
   
      - **Selenium WebDriver**
   
        | JAR                                | Version | Purpose                    |
        | ---------------------------------- | ------- | -------------------------- |
        | selenium-java-4.22.0.jar           | 4.22.0  | Main Selenium WebDriver    |
        | selenium-api-4.22.0.jar            | 4.22.0  | Selenium API interfaces    |
        | selenium-chrome-driver-4.22.0.jar  | 4.22.0  | ChromeDriver support       |
        | selenium-edge-driver-4.22.0.jar    | 4.22.0  | EdgeDriver support         |
        | selenium-firefox-driver-4.22.0.jar | 4.22.0  | GeckoDriver support        |
        | selenium-support-4.22.0.jar        | 4.22.0  | Selenium support utilities |

        **Download:** [Selenium Official](https://www.selenium.dev/downloads/)

    - **TestNG**
  
      | JAR               | Version | Purpose                                           |
      | ----------------- | ------- | ------------------------------------------------- |
      | testng-7.10.2.jar | 7.10.2  | TestNG framework for test execution and reporting |

      **Download:** [TestNG](https://testng.org/doc/download.html)

    - **Apache POI (Excel Handling)**

      | JAR                          | Version | Purpose                 |
      | ---------------------------- | ------- | ----------------------- |
      | poi-5.3.0.jar                | 5.3.0   | Core POI for Excel      |
      | poi-ooxml-5.3.0.jar          | 5.3.0   | Excel `.xlsx` support   |
      | poi-ooxml-full-5.3.0.jar     | 5.3.0   | Full OOXML dependencies |
      | commons-collections4-4.4.jar | 4.4     | Dependency for POI      |
      | xmlbeans-5.1.1.jar           | 5.1.1   | Dependency for POI      |
      | commons-compress-1.21.jar    | 1.21    | Dependency for POI      |

      **Download:** [Apache POI](https://poi.apache.org/download.html)

    - **Extent Reports (HTML Reporting)**
  
      | JAR                     | Version | Purpose                                      |
      | ----------------------- | ------- | -------------------------------------------- |
      | extentreports-5.1.0.jar | 5.1.0   | ExtentReports HTML report generation         |
      | gson-2.11.0.jar         | 2.11.0  | Required by ExtentReports for JSON reporting |

      **Download:** [Extent Reports GitHub](https://github.com/extent-framework/extentreports-java)
      
    - **Log4j (Logging)**
  
      | JAR                   | Version | Purpose                   |
      | --------------------- | ------- | ------------------------- |
      | log4j-api-2.23.1.jar  | 2.23.1  | Log4j API                 |
      | log4j-core-2.23.1.jar | 2.23.1  | Log4j Core Implementation |

      **Download:** [Log4j Downloads](https://logging.apache.org/log4j/2.x/download.html)
      
    - **WebDriverManager (Optional but recommended)**
  
      | JAR                        | Version | Purpose                                       |
      | -------------------------- | ------- | --------------------------------------------- |
      | webdrivermanager-5.9.2.jar | 5.9.2   | Automatically manages browser driver binaries |

      **Download:** [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)

6. Add all `.jar` files to the **Build Path**:

      - Right-click the project → **Build Path** → **Configure Build Path** → **Libraries** → **Add External JARs...** (use Classpath, not Modulepath).

      - Select all JARs inside the `lib/` folder.


**3. Configuration**

- Open `config/Config.properties` and update your environment details:
```properties
browser=chrome
url=https://opensource-demo.orangehrmlive.com/
username=Admin
password=admin123
```

1 Add test data in `testdata/testdata.xlsx` for data-driven tests.

---

## Run Tests
**Option 1 — Run from TestNG XML**

1. Open `testng.xml`

2. Click **Run As → TestNG Suite**

**Option 2 — Run via Command Line (Windows)**
```bash
java -cp "bin;lib/*" org.testng.TestNG testng.xml
```

**Option 3 — Run with Batch File**

Create a `run.bat` file in the root directory with:
```bat
@echo off
java -cp "bin;lib/*" org.testng.TestNG testng.xml
pause
```

Then double-click `run.bat` to execute all tests.

---

## Reporting
**Extent Report**

After execution, the detailed HTML report will be generated at:
```bash
/reports/ExtentReport.html
```

**TestNG Default Report**
```bash
/test-output/index.html
```

Open it in a browser to view the summary and results.

---

## Logging

All runtime logs are captured using **Log4j** and stored at:
```bash
/logs/automation.log
```

You can configure logging levels (INFO, DEBUG, ERROR) in the `log4j.properties` file.

---

## Configure Test Data

Test data for data-driven tests is stored in:
```bash
/testdata/testdata.xlsx
```

Use Apache POI utilities (`ExcelUtil.java`) to fetch and manage Excel data dynamically.

---

## Continuous Integration (Optional)

Integrate the framework with **Jenkins** or **GitHub Actions** to trigger builds automatically.

**Example Jenkins Pipeline Script:**

```groovy
stage('Run Automation Tests') {
    steps {
        bat 'java -cp "bin;lib/*" org.testng.TestNG testng.xml'
    }
}
```

---

## Author

**Developed By:** QA Automation Team

**Maintainer:** Vimalkumar Murugesan

**Email:** [vimalkumarm523@gmail.com](mailto:vimalkumarm523@gmail.com)

---

## License

This project is licensed under the [Apache-2.0 license](http://www.apache.org/licenses/LICENSE-2.0) — you are free to use, modify, and distribute it with attribution.
```sql
Apache-2.0 license

Copyright (c) 2025 Vimalkumar Murugesan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...

```

---

## Conclusion

The **OrangeHRM Hybrid Automation Framework** provides a robust, reusable, and scalable foundation for automating OrangeHRM test cases.
It streamlines test execution, simplifies data management, enhances reporting, and accelerates QA cycles for agile delivery.
