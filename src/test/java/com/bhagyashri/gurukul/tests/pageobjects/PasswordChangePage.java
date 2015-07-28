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

//This class provides abstraction over the URL http://localhost:8080/#/password through
//page object model. 
public class PasswordChangePage extends LoadableComponent<PasswordChangePage> {
	private WebElement password;
	private WebElement confirmPassword;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement saveButton;

	private static String url = "http://localhost:8080/#/password";
	private String title = "Password";
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	public PasswordChangePage(WebDriver driver, WebDriverWait driverwait) {
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
	
	// This function is intended to change existing password to a valid password.
	public void changePassword(String passwordText)
	{
		password.clear();
		confirmPassword.clear();
		password.sendKeys(passwordText);
		confirmPassword.sendKeys(passwordText);
		saveButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(.,'Password changed!')]")));
	}
	
	// This function checks that error message is displayed when mismatch passwords are entered 
	// in the New Password and New Password Confirmation field.
	public void changeIncorrectPassword(String passwordText, String confirmPasswordText)
	{
		password.clear();
		confirmPassword.clear();
		password.sendKeys(passwordText);
		confirmPassword.sendKeys(confirmPasswordText);
		
		Boolean isSaveButtonEnabled = saveButton.isEnabled();
		if(isSaveButtonEnabled)
		{
			saveButton.click();
			driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@translate,'global.messages.error.dontmatch')]")));
		}
	}
		
}
