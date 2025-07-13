package com.darkuros.selenium.tests;

import static org.testng.Assert.assertNotNull;

import org.slf4j.Logger;
import org.testng.annotations.Test;

import com.darkuros.selenium.utils.LoggerFactoryUtils;

public class ValidateLoggerTest {
	
	private static final Logger logger = LoggerFactoryUtils.getLogger(ValidateLoggerTest.class);

	/**
	 * This test validates that the logger is correctly initialized and can log messages.
	 * It checks if the logger is not null and logs a message at the INFO level.
	 */
	@Test
	public void validateLoggerInitialization() {
		// Validate that the logger is not null
		assertNotNull(logger, "Logger should be initialized");

		// Log a message at INFO level
		logger.info("Logger is successfully initialized and ready to use.");
	}

}
