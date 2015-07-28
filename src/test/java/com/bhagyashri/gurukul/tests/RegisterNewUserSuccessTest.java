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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.pageobjects.NewRegistrationPage;
import com.bhagyashri.gurukul.tests.utils.RandomLowerCaseString;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class RegisterNewUserSuccessTest {
	
	private String login;
	private String email;
	private String password;
	private String confirmPassword;
	private String browser;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//New Password and Confirmed New Password should accept the same value which
        					//which has alphanumeric and special characters
	        				{"kk@test1kk","kk@test1kk","chrome"},
	        				//New Password and Confirmed New Password should accept the same value which
        					//which has alphanumeric and special characters
	        				{"AMS%%BUSSTOP2","AMS%%BUSSTOP2","firefox"}
       				}
        		);
    }


	public RegisterNewUserSuccessTest(String password, String confirmPassword, String browser)
	{
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.browser = browser;
		RandomLowerCaseString randomString = new RandomLowerCaseString(8);
		
		this.login = randomString.nextString();
		this.email = randomString.nextString() + "@gmail.com";
	}
	
	@Before
	public void setup()
	{
	}
	
	
	@After
	public void teardown()
	{
	}
	
	private boolean isElementPresent(WebDriver driver, By by){
	    return !driver.findElements(by).isEmpty();
	}
	
	// Tests that new account can be created and can be used for future login
	@Test
	public void testNewUserSuccessRegistration()
	{
		WebDriver localDriver = WebDriverUtils.getDriver(browser);
		WebDriverWait localDriverwait = WebDriverUtils.getWebDriverWait(localDriver);
		NewRegistrationPage localNewRegistrationPage = new NewRegistrationPage(localDriver,localDriverwait);
		localNewRegistrationPage.registerNewUser(login, email, password, confirmPassword);
		localDriver.quit();
		
		localDriver = WebDriverUtils.getDriver(browser);
		localDriverwait = WebDriverUtils.getWebDriverWait(localDriver);
		LoginPage localLoginPage = new LoginPage(localDriver, localDriverwait);
		localLoginPage.login(login, password);
		
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String expectedString = "You are logged in as user \"" + login + "\".";
		String foundString = "";
		if(isElementPresent(localDriver, By.xpath("//div[2]/div/div")))
		{
			foundString = localDriver.findElement(By.xpath("//div[2]/div/div")).getText();
			
		}
		localDriver.quit();
		assertEquals(expectedString, foundString);
	}

}
