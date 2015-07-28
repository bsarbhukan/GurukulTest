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

import com.bhagyashri.gurukul.tests.pageobjects.LoginPage;
import com.bhagyashri.gurukul.tests.pageobjects.StaffsPage;
import com.bhagyashri.gurukul.tests.utils.Staff;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

@RunWith(value = Parameterized.class)
public class StaffAdditionWrongInputTest extends GurukulTestBase{
	
	private String staffName;
	private String branchName;
	private String browser;
	StaffsPage staffsPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Staff Name field  should not accept numeric input
	        				{"123","Mathematics","chrome"},
	        				//Staff Name field  should not accept combination of numbers+special characters
	        				{"57#%","Social Science","firefox"},
	        				//Staff Name field  should not accept combination of numbers+special characters
	        				{"(*@91","History","chrome"},
	        				//sql injection check to check the security vulnerability
	        				{";select * from user;--","Chemistry","chrome"},
	        				//Staff Name field  should not accept special characters
	        				{"","Chemistry","chrome"}
       				}
        		);
    }


	public StaffAdditionWrongInputTest(String staffName,String branchName, String browser)
	{
		this.branchName = branchName;
		this.browser = browser;
		this.staffName = staffName;
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
		staffsPage = new LoginPage(driver, driverwait).getStaffsPage("admin", "admin");
	}
	
	
	@After
	public void teardown()
	{
		driver.quit();
	}
	
    //Test that invalid input is not allowed in the staff name field
	@Test
	public void testAdditionOfStaffWithoutBranch()
	{
		
		staffsPage.addStaffWithWrongInput(new Staff(staffName,branchName));
		assertTrue(!staffsPage.getStaffs().contains(new Staff(staffName, branchName)));
	}
	
}
