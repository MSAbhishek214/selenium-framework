package com.darkuros.selenium.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int retryCount = 0;
	private static final int maxRetryCount = 1; // Number of retries allowed

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			System.out.println("Retrying test: " + result.getName() + " | Attempt: " + (retryCount + 1));
			return true;
		}
		return false;
	}
}