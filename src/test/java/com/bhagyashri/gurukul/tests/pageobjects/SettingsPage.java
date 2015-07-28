// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

//This class provides abstraction over the URL http://localhost:8080/#/settings through
//page object model. 
public class SettingsPage extends LoadableComponent<SettingsPage> {
	private WebElement firstName;
	private WebElement lastName;
	private WebElement email;
	private WebElement langKey;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement saveButton;

	private static String url = "http://localhost:8080/#/settings";
	private String title = "Settings";
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	public SettingsPage(WebDriver driver, WebDriverWait driverwait) {
		this.driver = driver;
		this.driverwait = driverwait;
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
		driver.get(url);
	}
	
	@Override
	protected void isLoaded()  {
		assertTrue(driver.getTitle().equals(title));
	}
	
	// This function clears the First Name field and enters input.
	// After clicking on the "Save" button is waits till message "Settings saved!" 
	// is visible on the page.
	public void setFirstName(String firstNameText)
	{
		firstName.clear();
		firstName.sendKeys(firstNameText);
		saveButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(.,'Settings saved!')]")));
	}
	
	// This function clears the Last Name field and enters input.
	// After clicking on the "Save" button is waits till message "Settings saved!" 
	// is visible on the page.
	public void setLastName(String lastNameText)
	{
		lastName.clear();
		lastName.sendKeys(lastNameText);
		saveButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(.,'Settings saved!')]")));
	}
	
	// This function clears the email field and enters input.
	// After clicking on the "Save" button is waits till message "Settings saved!" 
	// is visible on the page.
	public void setEmail(String emailText)
	{
		email.clear();
		email.sendKeys(emailText);
		saveButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(.,'Settings saved!')]")));
	}
	
	// This function is intended to throw a runtime exception for every invalid input entered
	public void setIncorrectFirstName(String firstNameText) throws Exception
	{
		firstName.clear();
		firstName.sendKeys(firstNameText);
		// Give enough time for server communication to enable the button
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Boolean isSaveButtonEnabled = saveButton.isEnabled();
		if(isSaveButtonEnabled)
		{
			throw new Exception("Saving should not be possible for incorrect data.");
		}
	}
	
	// This function is intended to throw a runtime exception for every invalid input entered
	public void setIncorrectLastName(String lastNameText) throws Exception
	{
		lastName.clear();
		lastName.sendKeys(lastNameText);
		// Give enough time for server communication to enable the button
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Boolean isSaveButtonEnabled = saveButton.isEnabled();
		if(isSaveButtonEnabled)
		{
			throw new Exception("Saving should not be possible for incorrect data.");
		}
	}
	
	// This function is intended to throw a runtime exception for every invalid input entered
	public void setIncorrectEmail(String emailText) throws Exception
	{
		email.clear();
		email.sendKeys(emailText);
		// Give enough time for server communication to enable the button
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Boolean isSaveButtonEnabled = saveButton.isEnabled();
		if(isSaveButtonEnabled)
		{
			throw new Exception("Saving should not be possible for incorrect data.");
		}
	}
	
	public String getFirstName()
	{
		return firstName.getAttribute("value");
	}
	
	public String getLastName()
	{
		return lastName.getAttribute("value");
	}
	
	public String getEmail()
	{
		return email.getAttribute("value");
	}
	
	public String getLanguage()
	{
		return langKey.getAttribute("value");
	}
}
