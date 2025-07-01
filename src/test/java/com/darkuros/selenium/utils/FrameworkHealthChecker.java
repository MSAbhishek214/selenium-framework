package com.darkuros.selenium.utils;

import java.util.Properties;
import org.openqa.selenium.WebDriver;

// final class to prevent inheritance
public final class FrameworkHealthChecker {

	private FrameworkHealthChecker() {
	} // utility class, no instantiation

	public static void validateDriver(WebDriver driver, String context) {
		if (driver == null) {
			throw new IllegalStateException("🚨 [" + context + "] WebDriver is null. setup() likely failed.");
		} else {
			System.out.println("✅ [" + context + "] WebDriver ready: " + driver);
		}
	}

	public static void validateConfig(Properties props, String context) {
		if (props == null || props.isEmpty()) {
			throw new IllegalStateException("❌ [" + context + "] Config properties not loaded.");
		}

		if (!props.containsKey("browser") || !props.containsKey("baseURL")) {
			throw new IllegalStateException(
					"❌ [" + context + "] Missing critical config keys like 'browser' or 'baseURL'.");
		}

		System.out.println("📝 [" + context + "] Config validated: " + props.keySet());
	}
}