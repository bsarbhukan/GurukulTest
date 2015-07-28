// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.pageobjects.BranchesPage;
import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.pageobjects.StaffsPage;
import com.bhagyashri.gurukul.tests.utils.Branch;
import com.bhagyashri.gurukul.tests.utils.Staff;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class StaffsTestAddSuccessful extends GurukulTestBase{
	
	private String staffName;
	private String branchName;
	private String branchCode;
	private String browser;
	StaffsPage staffsPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Valid inputs with the different combination
	        				{"Bhagyashri","Mathematics","MATH","firefox"},
	        				{"Ajay","Social Science","SOC","chrome"},
	        				{"Asima","History","HIST","firefox"},
	        				{"Ajitha","Chemistry","CHE","chrome"},
	        				{"JACK","Singing","SING1","firefox"},
	        				{"aaron","football","101","chrome"},
	        				{"Ishara","HomeScience","HOME","chrome"},
	        				// Adding Duplicate entry.
	        				{"Ishara","HomeScience","HOME","chrome"}
       				}
        		);
    }


	public StaffsTestAddSuccessful(String staffName,String branchName, String branchCode, String browser)
	{
		this.branchName = branchName;
		this.branchCode = branchCode;
		this.browser = browser;
		this.staffName = staffName;
	}
	
	@BeforeClass
	public static void startFresh()
	{
		ClearAllRecords();
	}
	
	@Before
	public void setup()
	{
		// Ensure that the branch for the staff exists before adding a staff in the test.
		// We use a different web driver for adding all branches
		WebDriver localWebDriver = WebDriverUtils.getDriver(browser);
		WebDriverWait localWebDriverWait = WebDriverUtils.getWebDriverWait(localWebDriver);
		BranchesPage branchesPage = new LoginPage(localWebDriver, localWebDriverWait).getBranchesPage("admin", "admin");
		branchesPage.addBranch(new Branch(branchName, branchCode));
		localWebDriver.quit();
		
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		
		staffsPage = new LoginPage(driver, driverwait).getStaffsPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Test the addition of new staff name in the staff page
	@Test
	public void testAdditionOfStaffSuccess()
	{
		
		staffsPage.addStaff(new Staff(staffName,branchName));
		assertTrue(staffsPage.getStaffs().contains(new Staff(staffName, branchName)));
	}
	
}
