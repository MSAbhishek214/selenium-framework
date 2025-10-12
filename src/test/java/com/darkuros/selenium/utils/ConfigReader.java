package com.darkuros.selenium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;

public class ConfigReader {

	private static final Logger logger = LoggerFactoryUtils.getLogger(ConfigReader.class);
	private static Properties properties = new Properties();

	static {
		try {
			FileInputStream file = new FileInputStream("src/test/resources/config.properties");
			properties.load(file);
			logger.info("Loaded config.properties successfully.");
		} catch (IOException e) {
			logger.error("Could not load prperties file, throwing RuntimeException");
			throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
		}
	}

	public static String get(String key) {
		String value = properties.getProperty(key);
		if (value == null || value.trim().isEmpty()) {
			logger.error("key {} is either empty or null", key);
		}
		return value;
	}

	public static Properties getProps() {
		if (properties.isEmpty()) {
			logger.error("Properties are empty — blocking test execution.");
			throw new IllegalStateException("Properties are not loaded.");
		}
		return properties;
	}
}