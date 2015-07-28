// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.assertTrue;

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
public class SettingsTest {
	
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
        					//First Name and Last Name fields should accept alpha input
        					//E-mail field should accept valid email address
	        				{"Bhagyashri","Sarbhukan","bhagyashri.sarbhukan@gmail.com","chrome"},
	        				//First Name input is combination of upper case alphabets and numbers
        					//E-mail field should accept valid email address
	        				{"VAISHALI123","Pande","vaishalip@gmail.com","firefox"},
	        				//First Name input is combination of alphabets and special characters
        					//E-mail field should accept valid email address
	        				{"sam*america","samuel","sam@gmail.com","firefox"},
	        				//First Name field should accept maximum characters (50 characters)
	        				{"soniamonalisoniamonalisoniamonalisoniamonalisonia","Jeferry","sonia@yahoo.com","firefox"},
	        				//Last Name field should accept maximum characters (50 characters)
	        				{"monika","gaskabjsdfhjdhfjhshjscdjhjdsjkhdfhdjjksd64439330","moni@hotmail.com","firefox"}
	        				
	        				
       				}
        		);
    }


	public SettingsTest(String firstName, String lastName, String email, String browser)
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
	
	// Test the input entry for the first name field in the settings page
	@Test
	public void testFirstNameChange()
	{
		settingPage.setFirstName(firstName);
		String firstNameAfter = settingPage.getFirstName();
		assertTrue(firstName.equals(firstNameAfter));
	}
	
	// Test the input entry for the last name field in the settings page
	@Test
	public void testLastNameChange()
	{
		settingPage.setLastName(lastName);
		String lastNameAfter = settingPage.getLastName();
		assertTrue(lastName.equals(lastNameAfter));
	}
	
	// Test the input entry for the email Id field in the settings page
	@Test
	public void testEmailIdChange()
	{
		settingPage.setEmail(email);
		String emailAfter = settingPage.getEmail();
		assertTrue(email.equals(emailAfter));
	}
}
