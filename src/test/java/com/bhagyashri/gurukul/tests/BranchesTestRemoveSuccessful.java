// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class BranchesTestRemoveSuccessful extends GurukulTestBase {
	
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
        					//Branch Name field should accept upper and lower case alpha inputs and 
        					//Branch code field should accept the upper case alpha inputs
	        				{"Mathematics","MATH","firefox"},
	        				//Branch Name field should accept only upper alpha inputs and 
        					//Branch code field should accept the numeric input
	        				{"GEOMETRY","101","firefox"},
	        				//Branch Name field should accept maximum length (20 character) alpha input 
        					//Branch code field should accept the upper case alpha inputs
	        				{"foodsciencefoodscien","FOOD12","chrome"},
        					//Branch code field should accept the maximum length (10 characters) input
	        				{"Civics","CIVCIVCIVC","firefox"},
	        				//Branch code field should accept the alphanumeric input
	        				{"Electrical","11EE3","chrome"}
       				}
        		);
    }


	public BranchesTestRemoveSuccessful(String branchName, String branchCode, String browser)
	{
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
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		branchesPage = new LoginPage(driver, driverwait).getBranchesPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests that Independent branch (branch which is not assigned to any staff member) 
	// can be deleted successfully
	@Test
	public void testRemovingBranchSuccess()
	{
		branchesPage.addBranch(new Branch(branchName, branchCode));
		assertTrue(branchesPage.getBranches().contains(new Branch(branchName, branchCode)));
		branchesPage.removeBranch(new Branch(branchName, branchCode));
		assertTrue(!branchesPage.getBranches().contains(new Branch(branchName, branchCode)));
	}
}
