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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.pageobjects.PasswordChangePage;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class PasswordChangeSuccessTest {
	
	private String browser;
	private String password;
	
	WebDriver driver;
	WebDriverWait driverwait;
	PasswordChangePage passwordChangePage;
	
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//New Password field should accept the valid password
	        				{"admin12","chrome"},
	        				//New Password field should accept the combination of alpha and special characters
	        				{"Scenary@HOLLAND","firefox"},
	        				//New Password field should accept only alpha input
	        				{"crocodile","chrome"},
	        				//New Password field should accept the combination of alpha+number +special characters
	        				{"1butterfly@park####","firefox"}
        		}
        		);
        	}
    


	public PasswordChangeSuccessTest(String password, String browser)
	{
		this.password = password;
		this.browser = browser;
	}
	
	@Before
	public void setup()
	{
		
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		
		LoginPage login = new LoginPage(driver, driverwait);
		passwordChangePage = login.getPasswordChangePage("admin","admin");
	}
	
	
	@After
	public void teardown()
	{
		passwordChangePage.changePassword("admin");
		driver.quit();
	}
	
	// Tests that password can be changed successfully when exact same passwords are provided
	// in the new password and new password confirmation field.
	@Test
	public void testChangePasswordSuccess()
	{
		passwordChangePage.changePassword(password);
	}
}
