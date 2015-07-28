// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.fail;
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
import com.bhagyashri.gurukul.tests.pageobjects.SettingsPage;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class SettingsTestInvalidInput {
	
	private String browser;
	private String firstName;
	private String lastName;
	private String email;
	
	WebDriver driver;
	WebDriverWait driverwait;
	SettingsPage settingPage;
	
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//First Name and Last Name fields takes only number input which should not happen
        					//Input invalid email address in E-mail field
	        				{"123","456","bhagyashri.sarbhukan@gmail..com","chrome"},
	        				//First Name and Last Name fields has alpha inputs
        					//Input invalid email address in E-mail field
	        				{"Michel","Daniel","679hg","firefox"},
	        				//First Name and Last Name fields has alpha inputs
        					//Input invalid email address in E-mail field
	        				{"Michel","Daniel","679hg","chrome"},
	        				//First Name field should not accept any character after maximum characters are entered (maximum 50 characters)
	        				{"jsdhjhdblsabalahrynbmncvnjkvjcdvnvvndjvnjlsaddfdjh","Tuulos","meetuulos@@yahoo.com","chrome"},
	        				//Last Name field should not accept any character after maximum characters are entered (maximum 50 characters)
	        				{"Heidi","12356667778cbnbhdhsdjhgfdjdfhgjddjsdfhjksfhdfkjsdhfk","heidi@.hotmail.com","firefox"}
       				}
        		);
    }


	public SettingsTestInvalidInput(String firstName, String lastName, String email, String browser)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.browser = browser;
	}
	
	@Before
	public void setup()
	{
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		
		LoginPage login = new LoginPage(driver, driverwait);
		settingPage = login.getSettingsPage("admin","admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests that same username is displayed correctly at the top which has been used
    // for login the gurukula web application
	@Test
	public void testRightUserNameDisplayed()
	{
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(.,'admin')]")));
	}
	
	// Tests the invalid input entry for the first name field in the settings page
	@Test
	public void testInvalidFistNameChange()
	{
		try {
			settingPage.setIncorrectFirstName(firstName);
		} catch (Exception e) {
			fail("Invalid first name was allowed");
		}
	}
	
	// Tests the invalid input entry for the last name field in the settings page
	@Test
	public void testInvalidLastNameChange()
	{
		try {
			settingPage.setIncorrectLastName(lastName);
		} catch (Exception e) {
			fail("Invalid last name was allowed");
		}
	}
	
	// Tests the invalid input entry for the email Id field in the settings page
	@Test
	public void testInvalidEmail()
	{
		try {
			settingPage.setIncorrectEmail(email);
		} catch (Exception e) {
			fail("Invalid e-mail address was allowed");
		}
	}

}
