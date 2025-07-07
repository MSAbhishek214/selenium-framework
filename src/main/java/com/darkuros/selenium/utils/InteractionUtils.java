package com.darkuros.selenium.utils;

import java.time.Duration;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class InteractionUtils {

    private InteractionUtils() {}

    /**
	 * Safely clicks on a WebElement, handling potential ElementClickInterceptedException
	 * by scrolling the element into view and clicking it using JavaScript. Especially useful
	 * when running tests in headless mode or when elements are obscured by overlays.
	 *
	 * @param driver   the WebDriver instance
	 * @param element  the WebElement to click
	 */
    public static void safeClick(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
    
    /**
     * Safely scrolls a WebElement into view using JavaScript. This is useful when
     * the element is not currently visible in the viewport, especially in
     * headless mode or when elements are obscured by overlays.
     * @param driver
     * @param element
     */
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
	 * Waits for the current URL to contain a specific substring.
	 * This is useful for verifying navigation or redirection in tests. Very useful
	 * in headless mode where the URL might change after an action.
	 *
	 * @param driver       the WebDriver instance
	 * @param partialUrl   the substring to check in the current URL
	 */
    public static void waitForUrlContains(WebDriver driver, String partialUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }
}