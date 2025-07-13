package com.darkuros.selenium.utils;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

// final class to prevent inheritance
public final class FrameworkHealthChecker {

	private static final Logger logger = LoggerFactoryUtils.getLogger(FrameworkHealthChecker.class);

	private FrameworkHealthChecker() {
	} // utility class, no instantiation

	public static void validateDriver(WebDriver driver, String context) {
		if (driver == null) {
			throw new IllegalStateException("[" + context + "] WebDriver is null. setup() likely failed.");
		} else {
			logger.info("[ {} ] WebDriver ready: {}", context, driver);
		}
	}

	public static void validateConfig(Properties props, String context, String... requiredKeys) {
		for (String key : requiredKeys) {
			String value = props.getProperty(key);
			if (value == null || value.trim().isEmpty()) {
				logger.error("Config key '{}' is missing or blank", key);
			} else {
				logger.debug("Config key '{}' = '{}'", key, value);
			}
		}
	}
}