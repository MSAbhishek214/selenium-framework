package com.darkuros.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.darkuros.selenium.base.BaseTest;

public class ExtentTestListener implements ITestListener {
	private static final Logger logger = LoggerFactoryUtils.getLogger(ExtentTestListener.class);

	private static ExtentReports extent = ExtentReportManager.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		logger.info("Suite Started: {}", context.getSuite().getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Test started: {}", result.getMethod().getMethodName());
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test passed: {}", result.getMethod().getMethodName());
		extentTest.get().pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.error("Test failed: {} | Reason: {}", result.getMethod().getMethodName(),
				result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error");

		Object testInstance = result.getInstance();
		if (testInstance instanceof BaseTest baseTest) {
			WebDriver driver = baseTest.getDriver();
			if (driver != null) {
				String screenshotPath = baseTest.captureScreenshot(result.getMethod().getMethodName());
				if (screenshotPath != null) {
					logger.info("Screenshot captured: {}", screenshotPath);
					extentTest.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
				} else {
					logger.warn("Screenshot path was null, skipping attach");
					extentTest.get().info("Screenshot skipped: path was null");
				}
			} else {
				logger.warn("Driver was null during failure handling");
				extentTest.get().info("Screenshot skipped: driver was null at time of failure");
			}
		}

		extentTest.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.warn("Test skipped: {}", result.getMethod().getMethodName());
		extentTest.get().skip("Test skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("Flushing ExtentReports for suite: {}", context.getSuite().getName());
		extent.flush();
	}
}