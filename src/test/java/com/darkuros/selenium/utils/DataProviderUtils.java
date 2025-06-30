package com.darkuros.selenium.utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return new Object[][] {
            { "dark@uros.com", "123@dark" }
        };
    }
    
    @DataProvider(name = "registrationData")
    public Object[][] registrationDataProvider() {
		return new Object[][] {
			{},
			{},
			{}
		};
    }
    
    @DataProvider(name = "forgotPasswordData")
    public Object[][] forgotPasswordDataProvider() {
		return new Object[][] {
			{},
			{},
			{}
		};
    }
}