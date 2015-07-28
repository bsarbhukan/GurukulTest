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
public class BranchesTestAddSuccessful extends GurukulTestBase {
	
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
        					//Valid Branch Name is with atleast 5 upper+lower case alphabet and valid Branch Code
        					{"Physics","PHY","firefox"},
        					//Duplicate entry check
        					{"Physics","PHY","chrome"},
        					//Branch Code should accept maximum 10 characters
	        				{"Mathematics","MATHMATHMA","chrome"},
        					//Branch Name should accept maximum 20 characters
        					{"DIGITALSIGNAL PROCES","DSP","firefox"},
        					//Branch Name accepts minimum 5 character alpha input
        					{"Socia","SOC","chrome"},
        					//Branch Code accepts minimum 2 character Upper case alpha input
	        				{"hisTory","HI","firefox"},
	        				//Branch Code accepts numeric input
	        				{"Chemistry","123","chrome"},
	        				//Branch Code accepts alphanumeric input
	        				{"Physics","19PHY","firefox"}
       				}
        		);
    }


	public BranchesTestAddSuccessful(String subject, String subjectCode, String browser)
	{
		this.branchName = subject;
		this.branchCode = subjectCode;
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
	
	// Tests addition of branches, all input are valid and hence addition should succeed.
	@Test
	public void testAdditionOfBranchSuccess()
	{
		branchesPage.addBranch(new Branch(branchName, branchCode));
		assertTrue(branchesPage.getBranches().contains(new Branch(branchName, branchCode)));
	}

}
