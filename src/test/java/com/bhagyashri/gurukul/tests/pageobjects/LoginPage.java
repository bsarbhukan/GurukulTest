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

//This class provides abstraction over the URL http://localhost:8080/#/login through
//page object model. 
public class LoginPage extends LoadableComponent<LoginPage> {
	
	private static String url = "http://localhost:8080/#/login";
	private String title = "gurukul";
	private WebElement username;
	private WebElement password;
	private WebElement rememberMe;
	@FindBy(xpath = "//button[contains(.,'Authenticate')]")
	private WebElement submit;
	
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	public LoginPage(WebDriver driver, WebDriverWait driverwait) {
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
	
	public LoginPage loginAndRemember(String usrnameText, String passwordText)
	{
		driver.get(url);
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		PageFactory.initElements(driver, this);
		
		username.click();
		username.clear();
		username.sendKeys(usrnameText);
		password.click();
		password.clear();
		password.sendKeys(passwordText);
		
		// Ensure that remember me is checked.
		 if(rememberMe.getAttribute("checked") == null) 
			 rememberMe.click();                  
		submit.click();
		return this;
	}
	
	// This function returns the LoginPage with valid username/password
	public LoginPage login(String usrnameText, String passwordText) {
		driver.get(url);
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		PageFactory.initElements(driver, this);
		
		username.click();
		username.clear();
		username.sendKeys(usrnameText);
		password.click();
		password.clear();
		password.sendKeys(passwordText);
		
		 if(rememberMe.getAttribute("checked") != null) // if Checked 
			 rememberMe.click();                    //to Uncheck it 
		submit.click();
		return this;
		
	}
	
	// This function returns the BranchesPage if login with the valid username/password.
	public BranchesPage getBranchesPage(String usrnameText, String passwordText)
	{
		login(usrnameText, passwordText);
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Entities')]")));
		
		WebElement entities = driver.findElement(By.xpath("//a[contains(.,'Entities')]"));
		entities.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.entities.branch']")));
		
		WebElement branchButton = driver.findElement(By.xpath("//span[@translate='global.menu.entities.branch']"));
		branchButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Create a new Branch')]")));
		
		BranchesPage branch = new BranchesPage(driver, driverwait);
		
		return branch;
	}
	
	// This function returns the StaffsPage if login with the valid username/password.
	public StaffsPage getStaffsPage(String usrnameText, String passwordText)
	{
		login(usrnameText, passwordText);
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Entities')]")));
		
		WebElement entities = driver.findElement(By.xpath("//a[contains(.,'Entities')]"));
		entities.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.entities.staff']")));
		
		WebElement branchButton = driver.findElement(By.xpath("//span[@translate='global.menu.entities.staff']"));
		branchButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Create a new Staff')]")));
		
		StaffsPage staffs = new StaffsPage(driver, driverwait);
		
		return staffs;
	}
	
	// This function returns the SettingsPage if login with the valid username/password.
	public SettingsPage getSettingsPage(String usrnameText, String passwordText)
	{
		login(usrnameText, passwordText);
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.account.main']")));
		
		WebElement account = driver.findElement(By.xpath("//span[@translate='global.menu.account.main']"));
		account.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.account.settings']")));
		
		WebElement settingButton = driver.findElement(By.xpath("//span[@translate='global.menu.account.settings']"));
		settingButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@translate='settings.title']")));
		
		SettingsPage settings = new SettingsPage(driver, driverwait);
		
		return settings;
	}
	
	// This function returns the PasswordChangePage if login with the valid username/password.
	public PasswordChangePage getPasswordChangePage(String usrnameText, String passwordText)
	{
		login(usrnameText, passwordText);
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.account.main']")));
		
		WebElement account = driver.findElement(By.xpath("//span[@translate='global.menu.account.main']"));
		account.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.account.password']")));
		
		WebElement passwordButton = driver.findElement(By.xpath("//span[@translate='global.menu.account.password']"));
		passwordButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@translate='password.title']")));
		
		PasswordChangePage passwordChangePage = new PasswordChangePage(driver, driverwait);
		
		return passwordChangePage;
	}
	
	// This function is for logout functionality
	public LoginPage logout()
	{
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='global.menu.account.main']")));
		
		WebElement account = driver.findElement(By.xpath("//span[@translate='global.menu.account.main']"));
		account.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@ng-click='logout()']")));
		
		WebElement logoutButton = driver.findElement(By.xpath("//a[@ng-click='logout()']"));
		logoutButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@translate='main.title']")));
		
		return this;
	}
}

