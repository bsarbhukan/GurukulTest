// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests;

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
import com.bhagyashri.gurukul.tests.utils.RandomUpperCaseString;
import com.bhagyashri.gurukul.tests.utils.Staff;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class StressTestAddBranchAndStaff extends GurukulTestBase{
	
	private String browser;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
	        				{"chrome"}
	        			
       				}
        		);
    }


	public StressTestAddBranchAndStaff(String browser)
	{
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
	}
	
	
	@After
	public void teardown()
	{
	}
	
	
	@Ignore
	// This is to check the systems behavior by doing stress testing checking 
	// by adding 100 entries for each  branch name/branch code and staff name/branch name
	@Test
	public void testAdditionOfBranchAndStaffStress()
	{
		RandomUpperCaseString randomString = new RandomUpperCaseString(7);
		
		String[] localBranchNames = new String[WebDriverUtils.MAX_STRESS_LIMIT];
		String[] localBranchCodes = new String[WebDriverUtils.MAX_STRESS_LIMIT];
		String[] localStaffNames = new String[WebDriverUtils.MAX_STRESS_LIMIT];
		
		for(int i=0; i < WebDriverUtils.MAX_STRESS_LIMIT; ++i)
		{
			localBranchNames[i] = randomString.nextString();
			localBranchCodes[i] = randomString.nextString();
			localStaffNames[i] = randomString.nextString();
		}
		
		for(int i=0; i < WebDriverUtils.MAX_STRESS_LIMIT; ++i)
		{
			WebDriver localWebDriver = WebDriverUtils.getDriver(browser);
			WebDriverWait localWebDriverWait = WebDriverUtils.getWebDriverWait(localWebDriver);
			BranchesPage localBranchesPage = new LoginPage(localWebDriver, localWebDriverWait).getBranchesPage("admin", "admin");
			localBranchesPage.addBranch(new Branch(localBranchNames[i], localBranchCodes[i]));
			localWebDriver.quit();
		}
		
		WebDriver localWebDriver = WebDriverUtils.getDriver(browser);
		WebDriverWait localWebDriverWait = WebDriverUtils.getWebDriverWait(localWebDriver);
		StaffsPage localStaffsPage = new LoginPage(localWebDriver, localWebDriverWait).getStaffsPage("admin", "admin");
		
		for(int i=0; i < WebDriverUtils.MAX_STRESS_LIMIT; ++i)
		{
			// Adding all staffs without branch name as it causes problems because of pages.
			localStaffsPage.addStaffWithoutBranchSeletion(new Staff(localStaffNames[i], localBranchNames[i]));
		}
		localWebDriver.quit();
	}
	
}
