package com.darkuros.selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * Represents the Product Catalogue page, which displays a list of all
 * available products.
 * This class provides methods to interact with the product list, such as
 * finding a product by name and adding it to the shopping cart.
 */
public class ProductCataloguePage extends BasePage {

	private static final Logger logger = LoggerFactoryUtils.getLogger(ProductCataloguePage.class);

	/**
	 * Constructor for the ProductCataloguePage class. It initializes the WebDriver and calls
	 * the constructor of the BasePage class.
	 *
	 * @param driver The WebDriver instance used to interact with the web page.
	 */
	public ProductCataloguePage(WebDriver driver, long explicitWaitInSeconds) {
		super(driver, explicitWaitInSeconds);
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
				.until(ExpectedConditions.urlToBe("https://rahulshettyacademy.com/client/#/dashboard/dash"));
		logger.info("Is home page displayed: {}", isDisplayed);
		return isDisplayed;
	}

}
