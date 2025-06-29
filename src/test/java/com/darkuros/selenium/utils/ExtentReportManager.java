package com.darkuros.selenium.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	private static ExtentReports extent;

	public static ExtentReports createInstance() {
		String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Darkuros Test Report");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Abhishek");

		return extent;
	}
}