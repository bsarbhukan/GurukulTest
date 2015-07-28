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
public class PasswordChangeFailureTest {
	
	private String browser;
	private String password;
	private String confirmPassword;
	
	WebDriver driver;
	WebDriverWait driverwait;
	PasswordChangePage passwordChangePage;
	
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					// New Password and Confirm New Password field values are not matching
	        				{"admin12","admin","chrome"},
	        				// New Password and Confirm New Password field should not accept the input less than 5 characters
	        				{"adm","adm","chrome"},
	        				//New Password and Confirm New Password field values are not matching and have very very long values
	        				{"horse##holland#race30##holland#race30","Unicorn##holland#race30##holland#race30","firefox"},
	        				//sql injection input for the password field to check the security vulnerability
	        				{";select * from pass;--","test","chrome"},
	        				//xss injection for the password field to check the security vulnerability
	        				{" <script>alert(document.cookie);</script>","xsscheck","chrome"}
       				}
        		);
    }


	public PasswordChangeFailureTest(String password, String confirmPassword, String browser)
	{
		this.password = password;
		this.confirmPassword = confirmPassword;
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
		driver.quit();
	}
	
	// Tests the changing password functionality when mismatch password is provided.
	@Test
	public void testChangePasswordFailure()
	{
		passwordChangePage.changeIncorrectPassword(password, confirmPassword);
	}
}
