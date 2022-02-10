package cd_testcases;

import org.openqa.selenium.WebDriver;

import cd_core.excelFunctions;
import cd_pages.searchCarsPage;

public class searchAndFilter {
	
	public static void searchTestCase_1(WebDriver driver, excelFunctions testData) {
		searchCarsPage searchPage = new searchCarsPage(driver);
		searchPage.clickProvince()
		.selectProvince(testData.getCellData("data", 1, 0))
		.clickFilterOption(testData.getCellData("data", 1, 1))
		.selectMake(testData.getCellData("data", 1, 2))
		.selectModel(testData.getCellData("data", 1, 3))
		.validateFilterPills(testData.getCellData("data", 1, 4))
		.clickSort()
		.selectSort(testData.getCellData("data", 1, 5))
		.selectFavorite(3)
		.selectAvailableCar()
		.clickPurchase()
		.clickDelivery()
		.enterAddress(testData.getCellData("data", 1, 6), testData.getCellData("data", 1, 7))
		.selectAddress()
		.clickAddressSave()
		.clickWarranty()
		.selectWarranty(testData.getCellData("data", 1, 8))
        .clickWarrantySave();	
	}

}
