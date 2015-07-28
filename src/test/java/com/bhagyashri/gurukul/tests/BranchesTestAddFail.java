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
public class BranchesTestAddFail extends GurukulTestBase {
	
	private String branchName;
	private String branchCode;
	private String browser;
	BranchesPage branchesPage;
	WebDriver driver;
	WebDriverWait driverwait;
	
	@Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(
        		new Object[][] {
        					//Branch Name field should not accept numeric input
	        				{"132","MATH","chrome"},
	        				//Branch Name field should not be less than 5 characters
	        				{"Soc","SOC","chrome"},
	        				//Branch Code field should not accept input less than 2 characters
	        				{"History","H","firefox"},
	        				// Branch name field should not accept special characters
	        				{"#$%^(*$&","CHE","chrome"},
	        				//Branch code field should not accept lower case alpha inputs
	        				{"Social Science","ssci","chrome"},
	        				//Branch Name field should not take alphanumeric input
	        				{"12345CHEMISTRY","CHE1234","chrome"},
	        				//Branch Name field should not take combination of alphanumeric and special character input
	        				{"Botany@1","BOT","chrome"},
	        				//Branch code field should not accept lower case alpha input and special characters
	        				{"Biology","Bi*&","chrome"},
	        				//Branch Name field should not be longer than 20 characters
	        				{"PhysicsPhysicsPhysics","PHY","firefox"},
	        				//Branch code field should be longer than 10 characters
	        				{"ENGLISH","ENGENGENGENG","firefox"},
	        				//Branch code field should not accept lower case alphabets
	        	    		{"ZooLoGY","zOo","firefox"},
	        				//Inputting sql injection input to the Branch Name field to check the security vulnerability
	        				{";select * from branches;--","SQL","chrome"},
	        	    		//Inputting XSS injection input to the Branch Name field to check the security vulnerability
	        				{"<script>alert(document.cookie);</script>","XSS","chrome"}
	        				
       				}
        		);
    }


	public BranchesTestAddFail(String subject, String subjectCode, String browser)
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
	
	// Tests all the cases where branch addition is unallowed and any attempt to add such a branch
	// should fail.
	@Test
	public void testAdditionOfBranchFail()
	{
		branchesPage.addInvalidBranch(new Branch(branchName, branchCode));
		assertTrue(!branchesPage.getBranches().contains(new Branch(branchName, branchCode)));
	}

}
