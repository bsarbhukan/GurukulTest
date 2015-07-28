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
public class StaffsTestViewScreen extends GurukulTestBase {
	
	private Branch branch;
	private Staff staff;
	private String browser;
	StaffsPage staffsPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Valid Branch Name and code
        					{"Ajay","Mathematics","MATH","chrome"},
        					{"Prof Daniel","DRAMA","DR","firefox"},
        					{"dggjsdhjfskjdfklsjflkslkfklsjfkjksdjkfhdskjsdfkjh","Music","MUS101","chrome"},
        					{"KK","LiteratureLiteratur","LIT","firefox"},
        					{"Shyamal","Home Economics","ECONOMICS","chrome"}
        					
       				}
        		);
    }


	public StaffsTestViewScreen(String staffName, String branchName, String branchCode, String browser)
	{
		this.branch = new Branch(branchName, branchCode);
		this.staff = new Staff(staffName, branchName);
		this.browser = browser;
	}
	
	@BeforeClass
	public static void startFresh()
	{
		ClearAllRecords();
	}
	
	@Before
	public void setup()
	{
		// Add the branches prerequisite for the Staff
		WebDriver localDriver = WebDriverUtils.getDriver(browser);
		WebDriverWait localDriverWait = WebDriverUtils.getWebDriverWait(localDriver);
		BranchesPage localBranchesPage = new LoginPage(localDriver, localDriverWait).getBranchesPage("admin", "admin");
		localBranchesPage.addBranch(branch);
		localDriver.quit();
		
		localDriver = WebDriverUtils.getDriver(browser);
		localDriverWait = WebDriverUtils.getWebDriverWait(localDriver);
		StaffsPage localStaffsPage = new LoginPage(localDriver, localDriverWait).getStaffsPage("admin", "admin");
		localStaffsPage.addStaff(staff);
		localDriver.quit();
		
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		staffsPage = new LoginPage(driver, driverwait).getStaffsPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests the view screen functionality for existing staff in the staff page
	@Test
	public void testStaffViewScreen()
	{
		Staff staffViewed = staffsPage.getStaffInViewScreenForStaff(staff);
		// Ensure the branch displayed is same as added.
		assertTrue(staffViewed.equals(staff));
	}

}
