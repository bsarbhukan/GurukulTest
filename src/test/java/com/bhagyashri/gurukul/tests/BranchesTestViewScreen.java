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
public class BranchesTestViewScreen extends GurukulTestBase {
	
	private Branch branch;
	private String browser;
	BranchesPage branchesPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Different combination of inputs for Valid Branch Name and code
        					{"Physics","PHY","firefox"},
        					{"SCIENCE","SCIE","chrome"},
        					{"Atheletic","101","firefox"},
        					{"Biology","BB","firefox"},
        					{"Ahgsbtrhskkjhhdhfdf","Z84748","chrome"},
        					{"civics","CCCCCC1234","firefox"},
       				}
        		);
    }


	public BranchesTestViewScreen(String branchName, String branchCode, String browser)
	{
		this.branch = new Branch(branchName, branchCode);
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
		// No parameterization of user since only one user available 
		branchesPage = new LoginPage(driver, driverwait).getBranchesPage("admin", "admin");
		branchesPage.addBranch(branch);
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests the view screen functionality for existing branch in the branch page
	@Test
	public void testBranchViewScreen()
	{
		Branch branchViewed = branchesPage.getBranchInViewScreenForBranch(branch);
		// Ensure the branch displayed is same as added.
		assertTrue(branchViewed.equals(branch));
	}

}
