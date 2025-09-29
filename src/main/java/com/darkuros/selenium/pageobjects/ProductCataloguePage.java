package com.darkuros.selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
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
	private List<WebElement> productCards;
	
	private final By productsBy = By.cssSelector(".mb-3");
    private final By addToCartButton = By.cssSelector(".card-body button:last-of-type");
    private final By toastMessage = By.id("toast-container");
    
    /**
     * Waits until the list of products is visible on the page.
     */
    public void waitForProductListToAppear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsBy));
        logger.info("Product catalogue is visible.");
    }
    
    /**
     * Finds and returns a specific product card WebElement by its name.
     *
     * @param productName The exact name of the product to find.
     * @return The WebElement representing the product card, or null if not found.
     */
    public WebElement getProductByName(String productName) {
        waitForProductListToAppear();
        WebElement foundProduct = productCards.stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst()
                .orElse(null);

        if (foundProduct != null) {
            logger.info("Found product: {}", productName);
        } else {
            logger.warn("Product not found: {}", productName);
        }
        return foundProduct;
    }
    
    /**
     * Finds a product by its name, clicks 'Add to Cart', and waits for the
     * confirmation toast to appear and disappear.
     *
     * @param productName The exact name of the product to add to the cart.
     */
    public void addProductToCart(String productName) {
        WebElement product = getProductByName(productName);
        if (product != null) {
            product.findElement(addToCartButton).click();
            logger.info("Clicked 'Add to Cart' for product: {}", productName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
            logger.info("'Product Added to Cart' toast is visible.");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastMessage));
            logger.info("Toast is no longer visible.");
        }
    }

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
