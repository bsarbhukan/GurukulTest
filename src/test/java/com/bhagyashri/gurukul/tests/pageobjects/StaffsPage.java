// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.utils.Staff;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

//This class provides abstraction over the URL http://localhost:8080/#/staff through
//page object model. 
public class StaffsPage extends LoadableComponent<BranchesPage> {
	@FindBy(xpath = "//button[@data-target='#saveStaffModal']")
	private WebElement addButton;
	
	private static String url = "http://localhost:8080/#/staff";
	private String title = "Staffs";
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	public StaffsPage(WebDriver driver, WebDriverWait driverwait) {
		this.driver = driver;
		this.driverwait = driverwait;
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
		driver.get(url);
	}
	
	@Override
	protected void isLoaded()  {
		assertTrue(driver.getTitle().equals(title));
	}

	public static void selectValue(WebElement element,String value){
		  try {
		    List<WebElement> dropDownValues=new Select(element).getOptions();
		    for (    WebElement option : dropDownValues) {
		      if (option.getText().equals(value)) {
		        option.click();
		      }
		    }
		  }
		 catch (  Exception e) {
		    e.printStackTrace();
		  }
		}
	
	// This function to add a valid staff
	public void addStaff(Staff staff) {
		
		addButton.click();
		// Wait for the visibility of the dialog.
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']")));
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='related_branch']")));

		WebElement staffName = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement staffBranchChoice = driver.findElement(By.xpath("//select[@name='related_branch']"));
		staffName.clear();
		staffName.sendKeys(staff.getName());
		
		driverwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		selectValue(staffBranchChoice, staff.getBranch());		
		

		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		saveButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
	}
	
	// This function checks addition of the staff name without the branch name
	public void addStaffWithoutBranchSeletion(Staff staff) {
		
		addButton.click();
		// Wait for the visibility of the dialog.
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']")));
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='related_branch']")));

		WebElement staffName = driver.findElement(By.xpath("//input[@name='name']"));
		staffName.sendKeys(staff.getName());
		
		driverwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));

		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		saveButton.click();
		
		// Wait till the dialog goes away.
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
	}
	
	// This function tries to enter invalid input for the staff
	public void addStaffWithWrongInput(Staff staff) {
		
		addButton.click();
		// Wait for the visibility of the dialog.
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']")));
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='related_branch']")));

		WebElement staffName = driver.findElement(By.xpath("//input[@name='name']"));
		staffName.sendKeys(staff.getName());
		
		//Wait for some time to ensure that we give enough time for the button to be enabled.
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		
		// The save button should be disabled, if not throw exception  
		if(saveButton.isEnabled())
		{
			throw new RuntimeException("Wrong input too accepted!!");
		}
		
		WebElement cancelButton = driver.findElement(By.xpath("//button[@data-dismiss='modal']"));
		cancelButton.click();
		
		// Wait till the dialog goes away.
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
	}
	
	// This function is to remove the valid staff from the staff page
	public void removeStaff(Staff staff) {
		int index = getStaffIndex(staff);
		if (index < 0)
			return;

		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[3]";

		WebElement deletButton = driver.findElement(By.xpath(selector));
		deletButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@translate='entity.delete.title']")));
		

		WebElement deleteConfirm = driver.findElement(By.cssSelector("button.btn.btn-danger"));
		deleteConfirm.click();

		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='entity.delete.title']")));
	}
	
	// This function is to edit the existing staff to new staff
	public void editStaff(Staff oldStaff, Staff newStaff) {
		int index = getStaffIndex(oldStaff);
		if (index < 0)
			return;

		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[2]";

		WebElement editButton = driver.findElement(By.xpath(selector));
		editButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
		
		WebElement nameField = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement branchField = driver.findElement(By.xpath("//select[@ng-model='staff.related_branchId']"));
		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		nameField.clear();
		nameField.sendKeys(newStaff.getName());
		selectValue(branchField, newStaff.getBranch());
		
		driverwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		saveButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
	}
	
	// This function is intended to edit existing valid staff to another invalid staff input
	public void editInvalidStaff(Staff oldStaff, Staff newStaff) {
		int index = getStaffIndex(oldStaff);
		if (index < 0)
			return;

		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[2]";

		WebElement editButton = driver.findElement(By.xpath(selector));
		editButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
		
		WebElement nameField = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement branchField = driver.findElement(By.xpath("//select[@ng-model='staff.related_branchId']"));
		nameField.clear();
		nameField.sendKeys(newStaff.getName());
		selectValue(branchField, newStaff.getBranch());

		Boolean isSubmitEnabled = (driver.findElements(By.id("//button[@type='submit']")).size() > 0);
		if(isSubmitEnabled)
		{
			throw new RuntimeException("Invalid data allowed in Staff editing field");
		}
		
		WebElement cancelButton = driver.findElement(By.xpath("//button[@data-dismiss='modal']"));
		cancelButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.staff.home.createOrEditLabel']")));
	}
	
	// This function is to view the staff screen
	public Staff getStaffInViewScreenForStaff(Staff staff)
	{
		// Find if the branch exists in the view
		int index = getStaffIndex(staff);
		if (index < 0)
			return new Staff("","");
		
		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[1]";
		WebElement viewButton = driver.findElement(By.xpath(selector));
		viewButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='gurukulaApp.staff.detail.title']")));
		

		WebElement nameField = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/input"));
		WebElement branchField = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]/input"));
		WebElement backButton = driver.findElement(By.xpath("//button[@type='submit']"));
		String name = nameField.getAttribute("value");
		String branch = branchField.getAttribute("value");
		
		backButton.click();
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@translate='gurukulaApp.staff.home.title']")));
		return new Staff(name, branch);
	}
	
	// This function returns the list of the staff for the given search query string
	public List<Staff> search(String inputText)
	{
		WebElement searchQuery = driver.findElement(By.xpath("//input[@id='searchQuery']"));
		WebElement searchButton = driver.findElement(By.xpath("//button[@ng-click='search()']"));
		
		searchQuery.clear();
		searchQuery.sendKeys(inputText);
		searchButton.click();
		
		// Wait for the branches to be retrieved.
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return getStaffs();
	}


	// This function returns a list of staff representing all the staff in the staff page
	public List<Staff> getStaffs() {
		List<Staff> staffs = new ArrayList<Staff>();
		List<WebElement> names = driver.findElements(By.xpath("//tr[@class='ng-scope']/td[2]"));
		List<WebElement> branches = driver.findElements(By.xpath("//tr[@class='ng-scope']/td[3]"));
		for (int i = 0; i < names.size(); i++) {
			staffs.add(new Staff(names.get(i).getText(), branches.get(i).getText()));
		}
		return staffs;
	}

	// This function returns the index for a given staff
	public int getStaffIndex(Staff staff) {
		List<Staff> staffs = getStaffs();
		int index = staffs.indexOf(staff);

		if (index >= 0) {
			return (1 + index);
		} else {
			return -1;
		}
	}
	
	// This is to delete all the staff entries to be called before each test class
	public void deleteAll()
	{
		List<Staff> staffs = getStaffs();
		for(Staff staff:staffs)
		{
			removeStaff(staff);
		}
	}
}
