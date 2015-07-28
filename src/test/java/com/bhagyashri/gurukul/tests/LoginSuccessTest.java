// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.assertEquals;

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
public class LoginSuccessTest {
	
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


	public LoginSuccessTest(String user, String password, String browser)
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
	
	// Test the login functionality using the valid user name/ password
	@Test
	public void testLoginSuccessCorrectPassword()
	{
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Entities')]")));
		String loginString = "You are logged in as user \"" + user + "\".";
		assertEquals(loginString, driver.findElement(By.xpath("//div[2]/div/div")).getText());
	}

}
