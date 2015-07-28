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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bhagyashri.gurukul.tests.utils.Branch;
import com.bhagyashri.gurukul.tests.utils.WebDriverUtils;

// This class provides abstraction over the URL http://localhost:8080/#/branch through
// page object model. 
public class BranchesPage extends LoadableComponent<BranchesPage> {
	@FindBy(xpath = "//button[@data-target='#saveBranchModal']")
	private WebElement addButton;
	
	private static String url = "http://localhost:8080/#/branch";
	private String title = "Branches";
	private WebDriver driver;
	private WebDriverWait driverwait;
	
	
	public BranchesPage(WebDriver driver, WebDriverWait driverwait) {
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

	// This function adds new Branch Name and branch code. In Branch page, first it clears the Branch Name and
	// Branch Code. Internally it calls the function getName() to get the Branch Name and getCode() to 
	// get the Branch Code.
	// After entering Branch Name and Code, it waits for the "Save" button to get visible.
	// After "Save" button is visible, it clicks on the "Save" button to save the changes.
	// Also this function waits till the time text "Create or edit a Branch" is invisible
	public void addBranch(Branch branch) {
		addButton.click();
		// Wait for the visibility of the dialog.
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']")));
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='code']")));

		WebElement branchName = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement branchCode = driver.findElement(By.xpath("//input[@name='code']"));
		branchName.clear();
		branchCode.clear();
		branchName.sendKeys(branch.getName());
		branchCode.sendKeys(branch.getCode());

		driverwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));

		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		saveButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.branch.home.createOrEditLabel']")));
	}
	
	// This function edits existing old Branch Name and updates to new Branch Name
	// If no branch name is present in the Branch view then no action and control returns
	// For existing branch in the branch view, it parses each row to get an index and then checks for 
	// each cell in that row to reach the Edit button (second button in the table)
	// Then it clicks on the Edit button and waits for the text message "Create or edit a Branch" to become visible
	// Branch Name, code and save buttons are identified using locators. Old values in Branch name and code field are 
	// cleared and new branch name and code values are entered by calling getName() and getCode() functions respectively. 
	// After clicking on the "Save" button, it waits till "Delete" button becomes invisible.
	public void editBranch(Branch oldBranch, Branch newBranch)
	{
		// Find if the branch exists in the view
		int index = getBranchIndex(oldBranch);
		if (index < 0)
			return;
		
		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[2]";
		WebElement editButton = driver.findElement(By.xpath(selector));
		editButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.branch.home.createOrEditLabel']")));
		

		WebElement nameField = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement codeField = driver.findElement(By.xpath("//input[@name='code']"));
		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		nameField.clear();
		nameField.sendKeys(newBranch.getName());
		codeField.clear();
		codeField.sendKeys(newBranch.getCode());
		saveButton.click();

		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='entity.delete.title']")));
	}
	
	// This function throws a runtime exception if user tries to edit branch name by giving invalid inputs.
	// First, functions checks for the existing branch name if any. If no branch name is present in the Branch view 
	// then no action and control returns. For existing branch in the branch view, it parses each row to get an index 
	// and then checks for each cell in that row to reach the Edit button (second button in the table)
	// Then it clicks on the Edit button and waits for the text message "Create or edit a Branch" to become visible.
	// For invalid branch name or code, "Save" button will not be enabled, function will take control to the branch page
	// by clicking on "Cancel" button.
	public void editInvalidBranch(Branch oldBranch, Branch newBranch)
	{
		// Find if the branch exists in the view
		int index = getBranchIndex(oldBranch);
		if (index < 0)
			return;
		
		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[2]";
		WebElement editButton = driver.findElement(By.xpath(selector));
		editButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.branch.home.createOrEditLabel']")));
		

		WebElement nameField = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement codeField = driver.findElement(By.xpath("//input[@name='code']"));
		nameField.clear();
		nameField.sendKeys(newBranch.getName());
		codeField.clear();
		codeField.sendKeys(newBranch.getCode());

		WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		// The save button should be disabled, if not throw exception  
		if(saveButton.isEnabled())
		{
			throw new RuntimeException("Wrong input too accepted!!");
		}

		// Hit cancel button and go back to the branches view.
		WebElement cancelButton = driver.findElement(By.xpath("//button[@data-dismiss='modal']"));
		cancelButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='entity.delete.title']")));
	}
	
	// This function is implemented to verify that invalid input cannot be entered for the branch name and code.
	// In branch page, it clicks on "Create a new Branch" and waits till Name and Code field becomes visible on the page.
	// Once they are visible, branch name and code are entered by calling getName() and getCode()
	// Explicit wait WAIT_TIME = 1000ms is used to ensure that enough time for the "Save" button to be enabled.
	// If "Save" button is not enabled it means invalid input is entered in either or both the fields Name and Code 
	// and runtime exception is thrown.For such invalid input, "Cancel" button is clicked and web driver waits till 
	// the text "Create or edit a Branch" becomes invisible and focus goes to the branch page.
	public void addInvalidBranch(Branch branch) {
		addButton.click();
		// Wait for the visibility of the dialog.
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']")));
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='code']")));

		WebElement branchName = driver.findElement(By.xpath("//input[@name='name']"));
		WebElement branchCode = driver.findElement(By.xpath("//input[@name='code']"));
		branchName.sendKeys(branch.getName());
		branchCode.sendKeys(branch.getCode());
		
		
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
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='gurukulaApp.branch.home.createOrEditLabel']")));
	}

	// This function is for deleting a branch is present any. This function checks for an index for a 
	// specific branch object.No action if no branch is present in the branches page. For an existing valid branch, 
	// this function parses its index and then traverses to the Delete button for that branch.
	// Clicks on Delete button and waits for the Delete confirmation pop up to appear. 
	// After clicking on delete button in delete confirmation pop up window web driver waits 
	// for the pop up window to become invisible.
	public void removeBranch(Branch branch) {
		int index = getBranchIndex(branch);
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
	
	// This function checks that branch having staff assigned cannot be removed.
	// This function parses index for the branch object and traverses to the Delete button for that branch.
	// It clicks on the Delete button and waits for the Delete confirmation pop up to appear. 
	// After clicking on the Delete button in the pop-up window, it waits for the Delete button to become enabled.
	// If Delete button is not enabled then it clicks on the "Cancel" button and it waits till the view goes back 
	// to the branch page.
	public void removeDependendBranch(Branch branch) {
		int index = getBranchIndex(branch);
		if (index < 0)
			return;

		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[3]";

		WebElement deletButton = driver.findElement(By.xpath(selector));
		deletButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@translate='entity.delete.title']")));
		

		WebElement deleteConfirm = driver.findElement(By.cssSelector("button.btn.btn-danger"));
		deleteConfirm.click();
		
		//Wait for some time to ensure that we give enough time for the button to be enabled.
		try {
			Thread.sleep(WebDriverUtils.WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement cancelButton = driver.findElement(By.cssSelector("form[name=\"deleteForm\"] > div.modal-footer > button.btn.btn-default"));
		cancelButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@type='submit']")));
	}
	
	// This function is to view the existing branch details.
	// No action needed if no branch is present.
	// This function parses for an index for a particular branch object and traverses in that 
	// row to get the View button. It clicks on the View button and waits for branch detail view to open. 
	// getAttribute() reads the value of the branch and code fields.
	// After clicking on the "Back" button it waits focus goes to the branch page. 
	// This function returns the branch name and code.
	
	public Branch getBranchInViewScreenForBranch(Branch branch)
	{
		// Find if the branch exists in the view
		int index = getBranchIndex(branch);
		if (index < 0)
			return new Branch("", "");
		
		String selector = "//tr[@class='ng-scope'][" + index + "]/td[4]/button[1]";
		WebElement viewButton = driver.findElement(By.xpath(selector));
		viewButton.click();
		
		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@translate='gurukulaApp.branch.detail.title']")));
		

		WebElement nameField = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/input"));
		WebElement codeField = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]/input"));
		WebElement backButton = driver.findElement(By.xpath("//button[@type='submit']"));
		String name = nameField.getAttribute("value");
		String code = codeField.getAttribute("value");
		
		backButton.click();
		driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[@translate='entity.delete.title']")));
		return new Branch(name, code);
	}
	
	// This function returns the list of the branches as a search result.
	// In branch view, locators are used to locate the searchQuery and searchButton. 
	// It enters the search query string and waits to retrieve the list of the branches. 
	// This function returns the list of the branches.
	public List<Branch> search(String inputText)
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
		
		return getBranches();
	}
	
	// This function returns the list of branches
	public List<Branch> getBranches() {
		List<Branch> branches = new ArrayList<Branch>();
		List<WebElement> subjects = driver.findElements(By.xpath("//tr[@class='ng-scope']/td[2]"));
		List<WebElement> subjectCodes = driver.findElements(By.xpath("//tr[@class='ng-scope']/td[3]"));
		for (int i = 0; i < subjects.size(); i++) {
			branches.add(new Branch(subjects.get(i).getText(), subjectCodes.get(i).getText()));
		}
		return branches;
	}

	// This function returns an index for an existing branch otherwise it returns -1.
	public int getBranchIndex(Branch branch) {
		List<Branch> branches = getBranches();
		int index = branches.indexOf(branch);

		if (index >= 0) {
			return (1 + index);
		} else {
			return -1;
		}
	}
	
	// Need to be sure that before deleting all branches, all staffs are deleted.
	public void deleteAll()
	{
		List<Branch> branches = getBranches();
		for(Branch branch:branches)
		{
			removeBranch(branch);
		}
	}
}
