package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * Represents the Checkout Page where the user enters shipping information
 * and finalizes the order.
 */
public class CheckoutPage extends BasePage {

    private static final Logger logger = LoggerFactoryUtils.getLogger(CheckoutPage.class);

    public CheckoutPage(WebDriver driver, long explicitWaitInSeconds) {
        super(driver, explicitWaitInSeconds);
    }

    // --- WebElements and Locators ---

    @FindBy(css = "[placeholder='Select Country']")
    private WebElement countryInput;

    @FindBy(css = ".ta-results button")
    private WebElement selectCountryButton; // Assumes the desired country is the first result

    @FindBy(css = ".action__submit")
    private WebElement placeOrderButton;

    @FindBy(css = ".hero-primary")
    private WebElement confirmationMessage;
    
    private final By countryResults = By.cssSelector(".ta-results");

    // --- Page Actions ---

    /**
     * Enters a country name into the auto-suggest dropdown and selects it.
     *
     * @param countryName The full name of the country to select.
     */
    public void selectCountry(String countryName) {
        logger.info("Selecting country: {}", countryName);
        Actions a = new Actions(getDriver());
        a.sendKeys(countryInput, countryName).build().perform();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(countryResults));
        selectCountryButton.click();
    }

    /**
     * Clicks the 'Place Order' button to submit the order.
     */
    public void placeOrder() {
        logger.info("Placing the order.");
        placeOrderButton.click();
    }
    
    /**
     * Retrieves the text from the final order confirmation message.
     *
     * @return The confirmation text (e.g., "THANKYOU FOR THE ORDER.").
     */
    public String getConfirmationMessage() {
        String message = confirmationMessage.getText();
        logger.info("Retrieved confirmation message: {}", message);
        return message;
    }
}