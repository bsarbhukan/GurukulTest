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
import com.bhagyashri.gurukul.tests.utils.Branch;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class BranchesTestEditSuccessful extends GurukulTestBase {
	
	private String oldBranchName;
	private String oldBranchCode;
	private String newBranchName;
	private String newBranchCode;
	private String browser;
	BranchesPage branchesPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        			        // Editing Branch Name field to valid input
	        				{"Mathematics","MATH","Maths","MATH","firefox"},
	        				// Editing Branch Code field to valid input
	        				{"Home Science","HOME","Home Science","HOMESCI","chrome"},
	        				// Editing Branch Name and Code to the valid input
	        				{"Zoology","ZOO","Algebra","ALG","firefox"}
       				}
        		);
    }


	public BranchesTestEditSuccessful(String oldBranchName, String oldBranchCode, String newBranchName, String newBranchCode, String browser)
	{
		this.oldBranchName = oldBranchName;
		this.oldBranchCode = oldBranchCode;
		this.newBranchName = newBranchName;
		this.newBranchCode = newBranchCode;
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
		// No parameterization of user name or password as there is only one provided.
		branchesPage = new LoginPage(driver, driverwait).getBranchesPage("admin", "admin");
		branchesPage.addBranch(new Branch(oldBranchName, oldBranchCode));
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Test editing existing valid branch name to another valid new branch name. 
	// This expected behavior and hence test succeeds
	@Test
	public void testEditingBranchSuccess()
	{
		branchesPage.editBranch(new Branch(oldBranchName, oldBranchCode), new Branch(newBranchName, newBranchCode));
		// Test that old branch is no more present
		assertTrue(!branchesPage.getBranches().contains(new Branch(oldBranchName, oldBranchCode)));
		// Test that new branch present
	    assertTrue(branchesPage.getBranches().contains(new Branch(newBranchName, newBranchCode)));
	}
}
