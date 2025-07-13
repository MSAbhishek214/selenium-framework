package com.darkuros.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for creating SLF4J loggers.
 * This class provides a static method to obtain a logger instance for a given class.
 * it cannot be inherited or instantiated.
 */
public final class LoggerFactoryUtils {

	// Private constructor to prevent instantiation
	private LoggerFactoryUtils() {
	}

	/**
	 * Returns a logger instance for the specified class.
	 *
	 * @param clazz the class for which the logger is to be created
	 * @return a Logger instance for the specified class
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * Returns a logger instance for the specified name.
	 *
	 * @param name the name for which the logger is to be created
	 * @return a Logger instance for the specified name
	 */
	public static Logger getLogger(String name) {
		return LoggerFactory.getLogger(name);
	}
}