package com.darkuros.selenium.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * BasePage serves as a foundation for all Page Object classes in the Selenium
 * framework. It provides common functionality such as WebDriver management,
 * explicit waits, and logging.
 * 
 * All specific page objects should extend this class to inherit its properties
 * and methods.
 */
public abstract class BasePage {

	private static final Logger logger = LoggerFactoryUtils.getLogger(BasePage.class);
	private final WebDriver driver; // Driver declared here but lives in BaseTest

	/**
	 * Returns the WebDriver instance associated with this page.
	 * 
	 * @return WebDriver instance
	 */
	protected WebDriver getDriver() {
		return driver;
	}

	protected WebDriverWait wait; // WebDriverWait is used for explicit waits in Page Objects

	/**
	 * Constructor for BasePage. Initializes the WebDriver and WebDriverWait, and
	 * sets up the PageFactory for this page.
	 * 
	 * @param driver the WebDriver instance to be used by this page
	 * @throws IllegalStateException if the provided WebDriver is null
	 */
	public BasePage(WebDriver driver) {
		if (driver == null) {
			logger.error("WebDriver passed to BasePage is null! Throwing IllegalStateException.");
			throw new IllegalStateException("WebDriver passed to BasePage is null!");
		}
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// Log the initialization of the BasePage
		logger.info("Initializing Page Object: {}", this.getClass().getSimpleName());
		logger.debug("WebDriver session established for BasePage");
		logger.debug("Explicit wait configured with 5 seconds timeout");

		// Initialize elements for the specific child Page Object
		// 'this' refers to the concrete Page Object instance (e.g., LoginPage)
		PageFactory.initElements(this.driver, this);
	}

	/**
	 * Returns the current URL of the WebDriver instance. This method logs the
	 * current URL for debugging purposes. Different from the one in BaseTest.
	 * 
	 * @return the current URL as a String
	 */
	public String getCurrentPageURL() {
		String currentUrl = getDriver().getCurrentUrl();
		logger.info("Current driver URL: {}", currentUrl);
		return currentUrl;
	}

}
