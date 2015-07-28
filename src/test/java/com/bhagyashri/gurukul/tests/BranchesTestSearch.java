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
public class BranchesTestSearch extends GurukulTestBase {
	
	private String queryString;
	private String browser;
	private int numberOfResults;
	private BranchesPage branchesPage;
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	static final Branch[] branches= {
			new Branch("Mathematics","MATHE"),
			new Branch("MATHE","ALGE")
	};
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					// Search Branch Names's list using valid search query string
        			        // First parameter is search query string, second parameter is number of matches
        			        // third parameter is the browser
	        				{"MATHE", 2, "firefox"},
	        				{"Mathematics", 1, "firefox"},
	        				{"ALGE", 1, "firefox"},
	        				{"Civics",0, "firefox"}
       				}
        		);
    }


	public BranchesTestSearch(String queryString, int numberOfResults, String browser)
	{
		this.queryString = queryString;
		this.numberOfResults = numberOfResults;
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
		WebDriver localDriver = WebDriverUtils.getDriver(browser);
		WebDriverWait localdriverwait = WebDriverUtils.getWebDriverWait(localDriver);
		BranchesPage localBranchesPage = new LoginPage(localDriver, localdriverwait).getBranchesPage("admin", "admin");
		for(Branch branch : branches)
		{
			localBranchesPage.addBranch(branch);
		}
		localDriver.quit();
		
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		branchesPage = new LoginPage(driver, driverwait).getBranchesPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
		ClearAllRecords();
	}
	
	// Tests search functionality in branch page
	@Test
	public void testBranchSearch()
	{
		int numberOfBranchesReturned = branchesPage.search(queryString).size();
		assertTrue(numberOfBranchesReturned == numberOfResults);
	}
}
