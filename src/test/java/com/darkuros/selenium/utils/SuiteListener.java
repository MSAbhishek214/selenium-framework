package com.darkuros.selenium.utils;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		ConfigReader.getProps();
	}

	@Override
	public void onFinish(ISuite suite) {
		
	}

}
