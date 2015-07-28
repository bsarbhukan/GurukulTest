// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

//This class provides abstraction over the URL http://localhost:8080/#/register through
//page object model. 
public class NewRegistrationPage extends LoadableComponent<NewRegistrationPage> {
	
	private static String url = "http://localhost:8080/#/register";
	private String title = "Registration";
	private WebElement login;
	private WebElement email;
	private WebElement password;
	private WebElement confirmPassword;
	private WebElement registerButton;
	
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	public NewRegistrationPage(WebDriver driver, WebDriverWait driverwait) {
		this.driver = driver;
		this.driverwait = driverwait;
	}
	
	@Override
	protected void load() {
		driver.get(url);
	}
	
	@Override
	protected void isLoaded()  {
		assertTrue(driver.getTitle().equals(title));
	}
	
	// This function is used to register a new user by entering valid login, email and password. 
	// After entering all the inputs it waits for WAIT_TIME
	// It locates Register button and clicks on register button and waits till the message 
	// "Registration saved" is visible on the page.
	public NewRegistrationPage registerNewUser(String loginText,String emailText, String passwordText, String confirmPasswordText) {
		driver.get(url);
		PageFactory.initElements(driver, this);
		
		login.click();
		login.clear();
		login.sendKeys(loginText);
		email.click();
		email.clear();
		email.sendKeys(emailText);
		password.click();
		password.clear();
		password.sendKeys(passwordText);
		confirmPassword.click();
		confirmPassword.clear();
		confirmPassword.sendKeys(confirmPasswordText);
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		registerButton = driver.findElement(By.xpath("//button[@type='submit']"));
		registerButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(.,'Registration saved!')]")));
		return this;
		
	}
	
	
}

