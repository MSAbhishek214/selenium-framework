package com.darkuros.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.darkuros.selenium.base.BaseTest;

public class ExtentTestListener implements ITestListener {
	private static ExtentReports extent = ExtentReportManager.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().pass("✅ Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Object testInstance = result.getInstance();

		if (testInstance instanceof BaseTest baseTest) {
			WebDriver driver = baseTest.getDriver();
			if (driver != null) {
				String screenshotPath = baseTest.captureScreenshot(result.getMethod().getMethodName());
				if (screenshotPath != null) {
					extentTest.get().addScreenCaptureFromPath(screenshotPath, "📸 Failure Screenshot");
				} else {
					extentTest.get().info("🚫 Screenshot skipped: path was null");
				}
			} else {
				extentTest.get().info("🚫 Screenshot skipped: driver was null at time of failure");
			}
		}
		
		extentTest.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().skip("⚠️ Test skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}