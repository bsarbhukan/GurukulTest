// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.assertNotSame;

// The real user testing of the remember me function requires configuring
// browser profiles and is specific to the browser in use. However a real user
// level testing of the remember me feature is very much possible.
// I just check if the needed cookie is being set and is not null.

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class LoginRememberMeTest {
	
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
        					//Valid inputs for user name and password to check login functionality in chrome browser
	        				{"admin","admin","chrome"},
	        				//Valid inputs for user name and password to check login functionality in firefox browser
	        				{"admin","admin","firefox"}
       				}
        		);
    }
	
	// This method is kept intentionally even though not used. As an 
	// example how cookies can be persisted over different runs of test.
	public WebDriver getChromeWebDriverWithCookie()
	{
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("user-data-dir=~/UserData");
		capabilities.setCapability(ChromeOptions.CAPABILITY,options);
		return new ChromeDriver(capabilities);
	}
	
	public LoginRememberMeTest(String user, String password, String browser)
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
		loginPage.loginAndRemember(user, password);
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests the "Remember Me" feature in Login
	@Test
	public void testLoginRememberMe()
	{
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Entities')]")));
		Cookie cookie= driver.manage().getCookieNamed("remember-me");  
		String cookieValue = cookie.getValue();
		assertNotSame(cookieValue, "");
	}

}
