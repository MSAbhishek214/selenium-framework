package com.darkuros.selenium.utils;

/**
 * An interface defining the contract for a report logger.
 * This allows the framework's main code to log steps without being coupled
 * to a specific reporting implementation (e.g., ExtentReports, Allure).
 */
public interface IReporter {

    /**
     * Logs a 'pass' status step to the report.
     * @param message The descriptive message for the test step.
     */
    void logPass(String message);

    /**
     * Logs an 'info' status step to the report.
     * @param message The descriptive message for the test step.
     */
    void logInfo(String message);
}