package com.darkuros.selenium.utils;

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
		extentTest.get().fail(result.getThrowable());

		Object testInstance = result.getInstance();
		String screenshotPath = ((BaseTest) testInstance).captureScreenshot(result.getMethod().getMethodName());

		if (screenshotPath == null) {
			extentTest.get().info("🚨 Screenshot not captured: driver is null");
			return;
		} else {
			extentTest.get().addScreenCaptureFromPath(screenshotPath);
		}
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