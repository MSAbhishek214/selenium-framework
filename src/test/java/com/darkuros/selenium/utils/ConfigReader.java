package com.darkuros.selenium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties = new Properties();

	static {
		try {
			FileInputStream file = new FileInputStream("src/test/resources/config.properties");
			properties.load(file);
		} catch (IOException e) {
			throw new RuntimeException("❌ Failed to load config.properties: " + e.getMessage());
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	public static Properties getProps() {
		return properties;
	}
}
