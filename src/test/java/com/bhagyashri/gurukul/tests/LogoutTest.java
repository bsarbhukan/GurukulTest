// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class LogoutTest {
	
	private String browser;
	LoginPage login;
	
	WebDriver driver;
	WebDriverWait driverwait;
	
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Check Logout functionality in chrome browser
	        				{"chrome"},
	        				//Check Logout functionality in firefox browser
	        				{"firefox"}
       				}
        		);
    }


	public LogoutTest(String browser)
	{
	
		this.browser = browser;
	}
	
	@Before
	public void setup()
	{
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		login = new LoginPage(driver, driverwait);
		login.login("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests the Logout feature for the gurukula web application
	@Test
	public void testLogoutPage()
	{
		login.logout();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Welcome to Gurukula!')]")));
	}
	

}
