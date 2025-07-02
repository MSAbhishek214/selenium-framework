package com.darkuros.selenium.utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {

	@DataProvider(name = "loginScenarios")
	public Object[][] validLoginData() {
		return new Object[][] { { "dark@uros.com", "123@Dark", "success", "" },
				{ "light@uros.com", "123@Dark", "error", "Incorrect email or password." },
				{ "dark@uros.com", "123@dark", "error", "Incorrect email or password." },
				{ "light@uros.com", "123@dark", "error", "Incorrect email or password." } };
	}

	@DataProvider(name = "registrationData")
	public Object[][] registrationDataProvider() {

		return new Object[][] { { DataGeneratorUtils.generateTestUser() } };
	}

	@DataProvider(name = "forgotPasswordData")
	public Object[][] forgotPasswordDataProvider() {
		return new Object[][] { {}, {}, {} };
	}
}