package com.darkuros.selenium.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.IReporter;
import com.darkuros.selenium.utils.InteractionUtils;
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
	protected final WebDriverWait wait;
	protected final long explicitWaitInSeconds;
	protected final IReporter reporter;
	
	@FindBy(css = "button[routerlink*='cart']")
    private WebElement cartButton;

	/**
	 * Returns the WebDriver instance associated with this page.
	 * 
	 * @return WebDriver instance
	 */
	protected WebDriver getDriver() {
		return driver;
	}

	/**
	 * Constructor for BasePage. Initializes the WebDriver and WebDriverWait, and
	 * sets up the PageFactory for this page.
	 * 
	 * @param driver the WebDriver instance to be used by this page
	 * @param explicitWaitInSeconds the time in seconds for explicit waits
	 * @param reporter the IReporter instance for logging test steps
	 * @throws IllegalStateException if the provided WebDriver is null
	 */
	public BasePage(WebDriver driver, long explicitWaitTime, IReporter reporter) {
		if (driver == null) {
			logger.error("WebDriver passed to BasePage is null! Throwing IllegalStateException.");
			throw new IllegalStateException("WebDriver passed to BasePage is null!");
		}
		this.driver = driver;
		this.explicitWaitInSeconds = explicitWaitTime;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitInSeconds));
		this.reporter = reporter;
		
		// Log the initialization of the BasePage
		logger.info("Initializing Page Object: {}", this.getClass().getSimpleName());
		logger.debug("WebDriver session established for BasePage");

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
	
	/**
     * Clicks the global cart button to navigate to the CartPage.
     * @return A new CartPage object.
     */
    public CartPage goToCart() {
        logger.info("Navigating to the cart page.");
        InteractionUtils.safeClick(getDriver(), cartButton);
        return new CartPage(getDriver(), this.explicitWaitInSeconds, this.reporter);
    }

}
