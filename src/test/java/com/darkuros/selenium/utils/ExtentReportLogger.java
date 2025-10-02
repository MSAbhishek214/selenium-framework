package com.darkuros.selenium.utils;

import com.aventstack.extentreports.ExtentTest;

/**
 * An implementation of the IReporter interface that logs messages
 * to an Extent Report. This class is part of the test-scoped code.
 */
public class ExtentReportLogger implements IReporter {

    /**
     * Logs a 'pass' status step to the Extent Report for the current test.
     * @param message The descriptive message for the test step.
     */
    @Override
    public void logPass(String message) {
        ExtentTest currentTest = ExtentTestListener.getTest();
        if (currentTest != null) {
            currentTest.pass(message);
        }
    }

    /**
     * Logs an 'info' status step to the Extent Report for the current test.
     * @param message The descriptive message for the test step.
     */
    @Override
    public void logInfo(String message) {
        ExtentTest currentTest = ExtentTestListener.getTest();
        if (currentTest != null) {
            currentTest.info(message);
        }
    }
}