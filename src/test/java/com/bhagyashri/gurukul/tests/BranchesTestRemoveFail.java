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


// This class tests the case that we should be unable to delete a branch if there is a
// staff that teaches this branch. 
@RunWith(value = Parameterized.class)
public class BranchesTestRemoveFail extends GurukulTestBase {
	
	private String staffName;
	private String branchName;
	private String branchCode;
	private String browser;
	BranchesPage branchesPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
	        				//Staff Name field should accept lower and upper case alpha numeric
        					{"Ajay","Mathematics","MATH","chrome"},
	        				//Staff Name field should accept the space character
	        				{"Prof Subramani","Social Science","SOC","firefox"},
	        				//Staff Name field should accept only upper case letters
	        				{"JACK","History","HIST","chrome"},
	        				//Staff Name field should accept only lower case alpha input
	        				{"simone","Chemistry","CHE","firefox"},
	        				//Branch Name field should accept combination of upper and lower case alpha input
	        				{"John","SOCIAL science","CHE","chrome"},
	        				//Branch code field accepts upper case alpha inputs having 2 characters 
	        				{"Mrs Nareda","Zoology","ZO","firefox"},
	        				//Branch code field should accept numeric input 
	        				{"Mr Mathew","Algebra","1567","chrome"}
       				}
        		);
    }


	public BranchesTestRemoveFail(String staffName,String branchName, String branchCode, String browser)
	{
		this.staffName = staffName;
		this.branchName = branchName;
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
		WebDriver localdriver = WebDriverUtils.getDriver(browser);
		WebDriverWait localdriverwait = WebDriverUtils.getWebDriverWait(localdriver);
		BranchesPage localBranchesPage = new LoginPage(localdriver, localdriverwait).getBranchesPage("admin", "admin");
		localBranchesPage.addBranch(new Branch(branchName,branchCode));
		localdriver.quit();
		
		localdriver = WebDriverUtils.getDriver(browser);
		localdriverwait = WebDriverUtils.getWebDriverWait(localdriver);
		StaffsPage staffPage = new LoginPage(localdriver, localdriverwait).getStaffsPage("admin", "admin");
		staffPage.addStaff(new Staff(staffName,branchName));
		localdriver.quit();
		
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		branchesPage = new LoginPage(driver, driverwait).getBranchesPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// This test tests that branch cannot be removed if it has assigned staff in the staff page.
	@Test
	public void testRemovingBranchFail()
	{
		branchesPage.removeDependendBranch(new Branch(branchName, branchCode));
		assertTrue(branchesPage.getBranches().contains(new Branch(branchName, branchCode)));
	}
}
