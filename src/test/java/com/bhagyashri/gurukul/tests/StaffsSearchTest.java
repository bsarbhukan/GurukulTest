// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
public class StaffsSearchTest extends GurukulTestBase{
	
	private String queryString;
	private int numberOfMatches;
	private String browser;
	private StaffsPage staffsPage;
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	private static final String[][] testData = new String [][] { 
		{ "Bhagyashri Sarbhukan", "Social Science", "MATH"},
        { "Bhagyashri Sahoo", "Algebra","ALG"},
        { "Sikata Sahoo", "Political Science", "PLT"}
     };
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					// Valid inputs with the different combination
        			        // First parameter is the search query string
        			        // Second parameter is the number of matches found
        			        // Third parameter is the browser
	        				{"Bhagyashri",2,"firefox"},
	        				{"Sikata",1,"chrome"},
	        				{"Sahoo",2,"firefox"},
	        				{"Sarbhukan",1,"chrome"},
	        				// Branch name is used as search query string
	        				{"Science",2,"firefox"},
	        				{"Algebra",1,"chrome"}
       				}
        		);
    }


	public StaffsSearchTest(String queryString,int numberOfMatches, String browser)
	{
		this.queryString = queryString;
		this.numberOfMatches = numberOfMatches;
		this.browser = browser;
	}
	
	@BeforeClass
	public static void startFresh()
	{
		ClearAllRecords();
		
		for(int i=0; i<testData.length; i++)
		{
			WebDriver localWebDriver = WebDriverUtils.getDriver("firefox");
			WebDriverWait localWebDriverWait = WebDriverUtils.getWebDriverWait(localWebDriver);
			BranchesPage localbranchesPage = new LoginPage(localWebDriver, localWebDriverWait).getBranchesPage("admin", "admin");
			localbranchesPage.addBranch(new Branch(testData[i][1], testData[i][2]));
			localWebDriver.quit();
		}
		
		for(int i=0; i<testData.length; i++)
		{
			WebDriver localWebDriver = WebDriverUtils.getDriver("firefox");
			WebDriverWait localWebDriverWait = WebDriverUtils.getWebDriverWait(localWebDriver);
			StaffsPage localStaffsPage = new LoginPage(localWebDriver, localWebDriverWait).getStaffsPage("admin", "admin");
			localStaffsPage.addStaff(new Staff(testData[i][0], testData[i][1]));
			localWebDriver.quit();
		}
	}
	
	@Before
	public void setup()
	{	
		driver = WebDriverUtils.getDriver(browser);
		driverwait = WebDriverUtils.getWebDriverWait(driver);
		staffsPage = new LoginPage(driver, driverwait).getStaffsPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
	// Tests that search functionality for the staff page
	@Test
	public void testStaffSearch()
	{
		int numberOfStaffsReturned = staffsPage.search(queryString).size();
		assertTrue(numberOfStaffsReturned == numberOfMatches);
	}
	
	// Tests the staff name field for all the entries in the search result
	@Test
	public void testStaffSearchCheckNonEmptyName()
	{
		List<Staff> staffsReturned = staffsPage.search(queryString);
		
		for(Staff staff : staffsReturned)
		{
			assertTrue(staff.getName().length() > 0);
		}
	}
	
	// Tests the branch name field for all the entries in the search result
	@Test
	public void testStaffSearchCheckNonEmptyBranch()
	{
		List<Staff> staffsReturned = staffsPage.search(queryString);
		
		for(Staff staff : staffsReturned)
		{
			assertTrue(staff.getBranch().length() > 0);
		}
	}
	
}
