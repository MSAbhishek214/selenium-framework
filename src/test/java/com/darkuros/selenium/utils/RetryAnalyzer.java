package com.darkuros.selenium.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);
	private static final int maxRetryCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount.get() < maxRetryCount) {
			retryCount.set(retryCount.get() + 1);
			System.out.println("🔁 Retrying test: " + result.getName() + " | Thread: "
					+ Thread.currentThread().threadId() + " | Attempt: " + retryCount.get());

			return true;
		}
		return false;
	}
}