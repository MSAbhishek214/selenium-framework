package com.darkuros.selenium.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.testng.ITestContext;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static final Logger logger = LoggerFactoryUtils.getLogger(ExtentReportManager.class);

	public static ExtentReports createInstance(ITestContext context) {
		String suiteName = context.getSuite().getName().replaceAll("\\s+", "_");
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String reportName = "ExtentReport_" + suiteName + "_" + timeStamp + ".html";
		String reportPath = System.getProperty("user.dir") + "/reports/" + reportName;
		logger.info("Extent Report Path: {}", reportPath);

		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("Execution Report - " + suiteName);
		reporter.config().setDocumentTitle("DarkUros Suite: " + suiteName);
		reporter.config().getTheme();
		reporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Abhishek");

		return extent;
	}
}