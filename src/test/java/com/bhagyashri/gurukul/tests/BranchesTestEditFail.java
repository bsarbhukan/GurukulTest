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
public class BranchesTestEditFail extends GurukulTestBase {
	
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
        			        // System should not allow to edit Branch Name field input to less than 5 characters
	        				{"Mathematics","MATH","Math","Math","firefox"},
	        				// System should not allow to edit Branch Code field input to lower case code not allowed
	        				{"Science","SCI","civics","sci","firefox"},
	        				// System should not allow to edit Branch Name field to the string less than 5 characters
	        				{"Mathematics","MATH","Alg","ALG","chrome"},
	        				// System should not allow to edit Branch Name field with the input greater than maximum characters (20 characters)
	        				{"Geometry","GEO1","geographygeographygeha","101","firefox"},
	        				// System should not allow to edit Branch Code field with the input greater than maximum characters (10 characters)
	        				{"Drawing","127","Still Painting","PAINT00PAINT","firefox"},
	        				//sql injection input to check the security vulnerability
	        				{"Economics","EC",";select * from branches;--","DD20","firefox"},
	        				//xss injection to check the security vulnerability
	        				{"Economics","EC","<script>alert(document.cookie);</script>","DD20","firefox"}
	        				
       				}
        		);
    }


	public BranchesTestEditFail(String oldBranchName, String oldBranchCode, String newBranchName, String newBranchCode, String browser)
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
	
	// Tests all the cases where existing valid branch name is edited to invalid input which is not allowed 
	@Test
	public void testEditingBranchFail()
	{
		branchesPage.editInvalidBranch(new Branch(oldBranchName, oldBranchCode), new Branch(newBranchName, newBranchCode));
		// Test that old branch is present
		assertTrue(branchesPage.getBranches().contains(new Branch(oldBranchName, oldBranchCode)));
		// Test that new branch is not present
	    assertTrue(!branchesPage.getBranches().contains(new Branch(newBranchName, newBranchCode)));
	}
}
