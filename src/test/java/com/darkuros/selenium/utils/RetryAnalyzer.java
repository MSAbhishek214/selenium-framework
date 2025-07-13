package com.darkuros.selenium.utils;

import org.slf4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private static final Logger logger = LoggerFactoryUtils.getLogger(RetryAnalyzer.class);
	private ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);
	private static final int maxRetryCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount.get() < maxRetryCount) {
			retryCount.set(retryCount.get() + 1);
			logger.info("Retrying test: {} | Thread: {} | Attempt: {} of {}", result.getName(),
					Thread.currentThread().threadId(), retryCount.get(), maxRetryCount);
			return true;
		}
		logger.info("No more retries for test: {} | Thread: {} | Final Attempt: {} of {}", result.getName(),
				Thread.currentThread().threadId(), retryCount.get(), maxRetryCount);
		return false;
	}
}