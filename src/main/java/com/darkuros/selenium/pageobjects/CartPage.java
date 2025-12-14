package com.darkuros.selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.IReporter;
import com.darkuros.selenium.utils.InteractionUtils;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * Represents the Shopping Cart page.
 * This class provides methods to verify the items in the cart and to proceed to the checkout process.
 */
public class CartPage extends BasePage {

    private static final Logger logger = LoggerFactoryUtils.getLogger(CartPage.class);

    public CartPage(WebDriver driver, long explicitWaitInSeconds, IReporter reporter) {
        super(driver, explicitWaitInSeconds, reporter);
    }

    // --- WebElements and Locators ---

    @FindBy(css = ".cartSection h3")
    private List<WebElement> cartItems;

    @FindBy(css = ".totalRow button")
    private WebElement checkoutButton;

    // --- Page Actions ---

    /**
     * Verifies if a specific product is present in the cart.
     *
     * @param productName The name of the product to check for.
     * @return true if the product is found in the cart, false otherwise.
     */
    public boolean isProductInCart(String productName) {
        logger.info("Verifying if '{}' is present in the cart.", productName);
        boolean isPresent = cartItems.stream()
                .anyMatch(item -> item.getText().equalsIgnoreCase(productName));
        if (isPresent) {
            logger.info("'{}' was found in the cart.", productName);
            reporter.logPass("Verified that '" + productName + "' is correctly displayed in the cart.");
        } else {
            logger.warn("'{}' was NOT found in the cart.", productName);
        }
        return isPresent;
    }

    /**
     * Clicks the 'Checkout' button to proceed to the next step.
     * @return 
     *
     * @return A new CheckoutPage object.
     */
    public CheckoutPage goToCheckout() {
        logger.info("Clicking the checkout button.");
        InteractionUtils.safeClick(getDriver(), checkoutButton);
        return new CheckoutPage(getDriver(), explicitWaitInSeconds, reporter);
    }
}