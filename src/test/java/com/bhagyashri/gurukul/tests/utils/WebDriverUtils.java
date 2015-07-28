// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

// Utility class for accessing browser specific webdriver and timeouts.
public class WebDriverUtils {

	private final static int WEBDIRVER_WAIT_TIME = 30; // In seconds
	public final static int WAIT_TIME = 1000; // In milliseconds
	public final static int MAX_STRESS_LIMIT = 100;
	
	// Factory method that returns a specific instance of the webdriver
	// based on passed parameter.
	public static WebDriver getDriver(String browser)
	{
		WebDriver instance = null;
		if(browser.equalsIgnoreCase("chrome"))
		{
			instance = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			instance = new FirefoxDriver();
		}
		
		else if(browser.equalsIgnoreCase("htmlunit"))
		{
			instance = new HtmlUnitDriver();
		}
		
		return instance;
	}
	
	public static WebDriverWait getWebDriverWait(WebDriver driver)
	{
		return new WebDriverWait(driver, WEBDIRVER_WAIT_TIME);
	}
}
