# Selenium WebDriver Automation Framework

This is a robust and scalable test automation framework designed for web applications using Java, Selenium WebDriver, and TestNG. It incorporates modern best practices like the Page Object Model, data-driven testing, and parallel execution to ensure reliable and maintainable automated tests.

---

## 🌟 Core Features

* **Page Object Model (POM):** Clean separation of UI elements and test logic for high maintainability.
* **Parallel Execution:** Configured to run tests in parallel at the method level for significant speed improvements, with a thread-safe design using `ThreadLocal`.
* **Rich HTML Reporting:** Automatic generation of detailed ExtentReports with test status, steps, and screenshots on failure.
* **Data-Driven Testing:** Easily feed data to tests using TestNG's `@DataProvider`, supporting both static and dynamically generated test data.
* **Automatic Retries:** Automatically re-runs failed tests to handle intermittent issues and reduce flakiness.
* **Centralized Configuration:** Manage test environments (URLs, browser, waits) easily through an external `config.properties` file.
* **Structured Logging:** Clean and configurable logging using SLF4J and Logback, with separate log files for each run.
* **Resilient Interactions:** Includes utility methods to handle common Selenium exceptions, making tests more stable.

---

## 🔧 Tech Stack

* **Language:** Java 21
* **Automation Tool:** Selenium WebDriver 4
* **Test Runner:** TestNG
* **Build & Dependency Management:** Apache Maven
* **Reporting:** ExtentReports 5
* **Driver Management:** WebDriverManager
* **Logging:** SLF4J + Logback

---

## 🚀 Getting Started

### Prerequisites

* Java Development Kit (JDK) 21 or higher installed.
* Apache Maven installed.

### How to Run Tests

1.  **Clone the repository:**
    ```sh
    git clone <your-repository-url>
    cd selenium-framework
    ```

2.  **Run from Command Line:**
    You can run the entire regression suite using Maven. The default test suite is configured in the `pom.xml`.

    ```sh
    # This command runs the default suite specified in the surefire plugin
    mvn clean test
    ```

3.  **Run a Specific Test Suite:**
    To run a specific TestNG suite file (e.g., the regression suite):

    ```sh
    mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/regression-testng.xml
    ```

4.  **Override Configuration at Runtime:**
    You can override settings from `config.properties` directly from the command line.

    ```sh
    # Run tests on Firefox in headed mode
    mvn clean test -Dbrowser=firefox -Dheadless=false

    # Run tests on Edge in headless mode
    mvn clean test -Dbrowser=edge -Dheadless=true
    ```

---

## ⚙️ Configuration

* All primary configurations are located in `src/test/resources/config.properties`.
* **`baseURL`**: The base URL of the application under test.
* **`browser`**: The default browser for tests (e.g., `chrome`, `firefox`, `edge`).
* **`headless`**: Set to `true` to run in headless mode, `false` otherwise.
* **`implicitWait`**: The default implicit wait time in seconds.

---

## 📊 Reporting & Logging

* **HTML Reports:** After a test run, detailed ExtentReports are generated in the `/reports` directory. Each report has a unique timestamped filename.
* **Log Files:** A detailed log file (`framework.log`) is created in the `/logs` directory for each test execution.
