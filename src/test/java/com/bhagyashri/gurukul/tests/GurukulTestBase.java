// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.pageobjects.BranchesPage;
import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.pageobjects.StaffsPage;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

public class GurukulTestBase {

	// This method clears all the branch and staff data being added already.
    // It is called for all test classes before starting any of the tests in
	// the class. Since, it is called before class instantiation, it is static.
	// Since, it is a static method, it cannot be parameterized on browser. So,
	// "Firefox" is used here as the webdriver for Firefox is provided by 
	// Selenium
	
	public static void ClearAllRecords() {
		WebDriver driver = WebDriverUtils.getDriver("firefox");
		WebDriverWait driverwait = WebDriverUtils.getWebDriverWait(driver);
		
		// We need to start with a clean slate. Delete all staffs and branches.
		StaffsPage staffsPage = new LoginPage(driver, driverwait).getStaffsPage("admin", "admin");
		staffsPage.deleteAll();
		driver.quit();
		
		driver = WebDriverUtils.getDriver("firefox");
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		BranchesPage branchesPage = new LoginPage(driver, driverwait).getBranchesPage("admin", "admin");
		branchesPage.deleteAll();
		driver.quit();
	}

	public GurukulTestBase() {
		super();
	}

}