package cd_testExecution;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cd_core.excelFunctions;
import cd_core.generalFunctions;
import cd_testcases.searchAndFilter;

public class searchFunctionality {
	
	private WebDriver driver;
	private excelFunctions testData;
	
	@BeforeTest
	public void beforeTest() {
		String filePath = System.getProperty("user.dir") + "\\dataSheets\\TestData.xls";
		testData = new excelFunctions(filePath);
	}
	@BeforeMethod
	public void beforeEachTest(ITestContext context) {
		driver = generalFunctions.intializeDriver("chrome");
		context.setAttribute("WebDriver", driver);
		generalFunctions.navigateTo(driver, cd_constants.prodUrl);
	}
	
	@Test(description = "Search Test Case 1")
	public void searchTestCases() {
		searchAndFilter.searchTestCase_1(driver, testData);
	}
	
	@AfterMethod
	public void afterEachTest() {
		driver.quit();
	}
	
	@AfterTest
	public void afterTest() {
		testData.closeWorkBook();
	}
 
}
