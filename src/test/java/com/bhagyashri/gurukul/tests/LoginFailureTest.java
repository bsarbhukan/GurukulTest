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
public class LoginFailureTest {
	
	private String user;
	private String password;
	private String browser;
	LoginPage loginPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Input invalid username and password check in chrome browser
	        				{"test","test","chrome"},
	        				//Input for injecting sql to the code to check the security vulnerability in chrome
	        				{";select * from user;--","test","chrome"},
	        				//Input invalid username and password check in chrome browser
	        				{"notRegistered","notPresent","firefox"},
	        				//Input for injecting sql to the code to check the security vulnerability in firefox
	        				{";select * from user;--","test","firefox"},
	        				//xss injection to check the security vulnerability in firefox
	        				{"<script>alert(2);</script>","hello@world","firefox"}
       				}
        		);
    }


	public LoginFailureTest(String user, String password, String browser)
	{
		this.user = user;
		this.password = password;
		this.browser = browser;
	}
	
	@Before
	public void setup()
	{
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		loginPage = new LoginPage(driver, driverwait);
		loginPage.login(user, password);
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Test the scenarioes of invalid login
	@Test
	public void testLoginFailInvalidInput()
	{
		String loginFailString = "//strong[contains(.,'Authentication failed!')]";
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginFailString)));
	}

}
