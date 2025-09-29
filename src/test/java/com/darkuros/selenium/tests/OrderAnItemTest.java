package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.CartPage;
import com.darkuros.selenium.pageobjects.CheckoutPage;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.pageobjects.ProductCataloguePage;

public class OrderAnItemTest extends BaseTest {

    @Test(groups = { "debug" })
    public void submitOrderTest() {
        // Test data
        String productName = "ZARA COAT 3";
        String countryName = "India";

        // 1. Login
        LoginPage loginPage = new LoginPage(getDriver(), this.explicitWaitInSeconds);
        ProductCataloguePage productCataloguePage = loginPage.loginApplication("dark@uros.com", "123@Dark");

        // 2. Find product and add to cart
        productCataloguePage.addProductToCart(productName);
        
        // 3. Navigate to Cart and verify product
        CartPage cartPage = productCataloguePage.goToCart();
        boolean isProductInCart = cartPage.isProductInCart(productName);
        Assert.assertTrue(isProductInCart, "Product was not found in the cart!");

        // 4. Proceed to checkout and place order
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry(countryName);
        checkoutPage.placeOrder();

        // 5. Verify confirmation message
        String confirmationMessage = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmationMessage, "THANKYOU FOR THE ORDER.");
    }
}