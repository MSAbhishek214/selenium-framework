package com.darkuros.selenium.utils;

import java.time.Duration;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

/**
 * Utility class for common interactions with WebElements in Selenium tests.
 * Provides methods to safely click elements, scroll them into view, and wait for
 * URL changes. These methods are particularly useful in headless mode or when
 * dealing with dynamic content that may not be immediately interactable.
 */
public final class InteractionUtils {

	private static final Logger logger = LoggerFactoryUtils.getLogger(InteractionUtils.class);

	private InteractionUtils() {
	}

	/**
	 * Safely clicks on a WebElement, handling potential
	 * ElementClickInterceptedException by scrolling the element into view and
	 * clicking it using JavaScript. Especially useful when running tests in
	 * headless mode or when elements are obscured by overlays.
	 *
	 * @param driver  the WebDriver instance
	 * @param element the WebElement to click
	 */
	public static void safeClick(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
			logger.debug("safeClick executed via WebDriver click");
		} catch (ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			logger.warn("safeClick fallback triggered due to click interception");
		}
	}

	/**
	 * Safely scrolls a WebElement into view using JavaScript. This is useful when
	 * the element is not currently visible in the viewport, especially in headless
	 * mode or when elements are obscured by overlays.
	 * 
	 * @param driver
	 * @param element
	 */
	public static void scrollIntoView(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		logger.debug("scrollIntoView executed for element: {}", element);
	}

	/**
	 * Waits for the current URL to contain a specific substring. This is useful for
	 * verifying navigation or redirection in tests. Very useful in headless mode
	 * where the URL might change after an action.
	 *
	 * @param driver     the WebDriver instance
	 * @param partialUrl the substring to check in the current URL
	 */
	public static void waitForUrlContains(WebDriver driver, String partialUrl) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(ExpectedConditions.urlContains(partialUrl));
			logger.info("URL now contains: {}", partialUrl);
		} catch (TimeoutException e) {
			logger.warn("Timeout while waiting for URL to contain: {}", partialUrl);
		}
	}
}