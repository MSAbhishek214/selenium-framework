package com.darkuros.selenium.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * This class implements IAnnotationTransformer to set the RetryAnalyzer for all test methods.
 * It ensures that the RetryAnalyzer is applied globally without needing to specify it on each test method.
 * If this transformer is not used, tests will not be retried upon failure unless explicitly annotated.
 * @see RetryAnalyzer
 */
public class RetryTransformer implements IAnnotationTransformer {
	
	/**
	 * Transforms the test annotation to set the RetryAnalyzer.
	 * This method is called by TestNG for each test method.
	 * Here annotation means the ITestAnnotation instance associated with the test method.
	 * 
	 * @param annotation The test annotation to be transformed.
	 * @param testClass The test class (can be null).
	 * @param testConstructor The test constructor (can be null).
	 * @param testMethod The test method (can be null).
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
