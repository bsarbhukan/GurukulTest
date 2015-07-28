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
public class SatffTestEditSuccessful extends GurukulTestBase {
	
	private String oldStaffName;
	private String oldBranchName;
	private String newStaffName;
	private String newBranchName;
	private String branchCode;
	private String browser;
	StaffsPage staffsPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        			        // Edit Staff Name with another valid input
	        				{"Ajay","Mathematics","Asima","Mathematics","MATH","chrome"},
	        				{"Prof Kulkarni","Biography","KK","Biography","BIO12","chrome"},
	        				//Edit Staff Name to the valid input and also edit the Branch name to another already added branch
	        				{"T","Architect","M","Mathematics","ARC1","firefox"},
	        				//System should allow the Staff Name field to the input having maximum characters (50 characters)
	        				{"H I J","Modern Studies","Prof KaushikProf KaushikProf KaushikProf Kaushikh","Biography","MOD1","firefox"}
       				}
        		);
    }


	public SatffTestEditSuccessful(String oldStaffName, String oldBranchName, String newStaffName, String newBranchName, String branchCode, String browser)
	{
		this.oldStaffName = oldStaffName;
		this.oldBranchName = oldBranchName;
		this.newBranchName = newBranchName;
		this.newStaffName = newStaffName;
		this.branchCode = branchCode;
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
		BranchesPage branchesPage = new LoginPage(localDriver, localDriverWait).getBranchesPage("admin", "admin");
		branchesPage.addBranch(new Branch(oldBranchName, branchCode));
		localDriver.quit();
		
		localDriver = WebDriverUtils.getDriver(browser);
		localDriverWait = WebDriverUtils.getWebDriverWait(localDriver);
		StaffsPage localStaffsPage = new LoginPage(localDriver, localDriverWait).getStaffsPage("admin", "admin");
		localStaffsPage.addStaff(new Staff(oldStaffName,oldBranchName));
		localDriver.quit();
		
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		// No parameterization of user name or password as there is only one provided.
		staffsPage = new LoginPage(driver, driverwait).getStaffsPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests that existing valid staff name can be edited to another valid input
	@Test
	public void testEditingStaffSuccess()
	{
		staffsPage.editStaff(new Staff(oldStaffName, oldBranchName), new Staff(newStaffName, newBranchName));
		// Test that old Staff is no more present
		assertTrue(!staffsPage.getStaffs().contains(new Staff(oldStaffName, oldBranchName)));
		// Test that new Staff present
	    assertTrue(staffsPage.getStaffs().contains(new Staff(newStaffName, newBranchName)));
	}
}
