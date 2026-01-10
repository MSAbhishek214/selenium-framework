package com.darkuros.selenium.retry;

import org.slf4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * This class implements IRetryAnalyzer to provide retry logic for failed tests.
 * It allows tests to be retried a specified number of times before being marked as failed.
 * The retry count is managed per thread, by using ThreadLocal version of retryCount, to ensure thread safety in parallel test executions.
 * Retries are applied only when this analyzer is set, either via annotation or through the RetryTransformer.
 * @see RetryTransformer
 */
public class RetryAnalyzer implements IRetryAnalyzer {

	private static final Logger logger = LoggerFactoryUtils.getLogger(RetryAnalyzer.class);
	private ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0); // Thread-local retry count so that parallel tests do not interfere with each other.
	private static final int maxRetryCount = 2;

	/**
	 * Determines whether a test should be retried.
	 * If the current retry count is less than the maximum allowed retries, it increments the count and returns true.
	 * Otherwise, it returns false, indicating no more retries should be attempted.
	 * 
	 * @param result The result of the test method that just ran.
	 * @return true if the test should be retried, false otherwise.
	 */
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