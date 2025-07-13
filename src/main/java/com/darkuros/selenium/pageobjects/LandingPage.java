package com.darkuros.selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * LandingPage class represents the landing page of the application. It extends
 * the BasePage class and provides methods to interact with elements on the
 * landing page. This is the home page that users see after logging in.
 */
public class LandingPage extends BasePage {

	// Logger for logging messages related to the LandingPage class
	private static final Logger logger = LoggerFactoryUtils.getLogger(LandingPage.class);

	/**
	 * Constructor for the LandingPage class. It initializes the WebDriver and calls
	 * the constructor of the BasePage class.
	 *
	 * @param driver The WebDriver instance used to interact with the web page.
	 */
	public LandingPage(WebDriver driver) {
		super(driver);
	}

	// Get a list of all products in the catalogue page
	@FindBy(css = ".mb-3")
	List<WebElement> products;

	/**
	 * This method checks if the homepage is displayed.
	 * 
	 * @return true if the home page is displayed, false otherwise.
	 */
	public boolean isHomePageDisplayed() {
		Boolean isDisplayed = wait
				.until(ExpectedConditions.urlToBe("https://rahulshettyacademy.com/client/dashboard/dash"));
		logger.info("Is home page displayed: {}", isDisplayed);
		return isDisplayed;
	}

}
