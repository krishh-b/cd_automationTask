package cd_pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import cd_core.generalFunctions;

public class searchCarsPage  {
	
	private WebDriver driver;
	private String makeFilter = "";
	private String modelFilter = "";
	private String selectedCarTitle1 = "";
	private String selectedCarTitle2 = "";
	private String availableCount = "";
	private commonHeader headerObj;
	
	//Static Locators....
	@FindBy(xpath="(//div[contains(@class, 'v-select__selection--comma')])[2]")
	private WebElement provinceDisplay;
	
	@FindBy(xpath="//span[@class='v-chip__content']")
	private WebElement filterChips;
	
	@FindBy(xpath="(//div[contains(@class, 'v-select__selection--comma')])[1]")
	private WebElement sortDisplay;
	
	@FindBy(xpath="//div[@class='vehicle-card']")
	private WebElement carsList;
	
	@FindBy(xpath="//div[@class='vehicle-card']")
	private List<WebElement> totalCarsDisplayed;

	@FindBy(xpath="//div[contains(text(), 'Search Results')]/strong")
	private WebElement totalCountDisplayed;
	
	//Dynamic Locators.....
	private String dropDownSelection = "//div[@role='listbox']/descendant::div[text()='$regex$']";
	private String filter = "//span[text()='$regex$']";
	private String make = "//span[text()='$regex$']";
	private String model = "//span[text()='$regex$']/ancestor::label/preceding-sibling::div";
	private String modelAvailableCount = "//span[text()='$regex$']//following-sibling::span";
	private String favoriteSelection = "(//span[contains(@class, 'fav-icon__heart')])[$regex$]";
	private String carSelection = "(//div[@class='vehicle-card'])[$regex$]";
	private String carTitle1 = "((//div[@class='vehicle-card'])[$regex$]//descendant::div[contains(@class, 'vehicle-card__title')])[1]";
	private String carTitle2 = "((//div[@class='vehicle-card'])[$regex$]//descendant::div[contains(@class, 'vehicle-card__title')])[2]";
	private String carTitle3 = "((//div[@class='vehicle-card'])[$regex$]//div[contains(@class, 'medium-grey--text')])[1]";
	private String carValue = "((//div[@class='vehicle-card'])[$regex$]//descendant::div[contains(@class, 'primary--text')])[1]";
	private String carStatusBadge = "(//div[@class='vehicle-card'])[$regex$]//span[contains(@class, 'vehicle-card__status-badge')]";
	
	public searchCarsPage(WebDriver driver) {
		// TODO - Need to add an explicit wait till page element is loaded and then perform actions...
		this.driver = driver;
		PageFactory.initElements(driver, this);
		headerObj = new commonHeader(driver);
		headerObj.validateHeaderImage();
	}
	public searchCarsPage clickProvince() {
		generalFunctions.clickElement(driver, provinceDisplay);
		return this;
	}
	public searchCarsPage selectProvince(String provinceName) {
		WebElement element = generalFunctions.createDynamicLocator(driver, "xpath", dropDownSelection, provinceName);
		generalFunctions.clickElement(driver, element);
		generalFunctions.waitUntil(3000);
		validateProvinceDisplay(provinceName);
		return this;
	}
	public void validateProvinceDisplay(String provinceName) {
		Assert.assertEquals(generalFunctions.getText(driver, provinceDisplay), provinceName);
	}
	public searchCarsPage clickFilterOption(String filterName) {
		WebElement element = generalFunctions.createDynamicLocator(driver, "xpath", filter, filterName);
		generalFunctions.clickElement(driver, element);
		return this;
	}
	public searchCarsPage selectMake(String makeName) {
		makeFilter = makeName;
		WebElement makeLocator = generalFunctions.createDynamicLocator(driver, "xpath", make, makeName);
		generalFunctions.moveElement(driver, makeLocator);
		generalFunctions.clickElement(driver, makeLocator);
		generalFunctions.waitUntil(2000);
		return this;
	}
	public searchCarsPage selectModel(String modelName) {
		modelFilter = modelName;
		WebElement modelLocator = generalFunctions.createDynamicLocator(driver, "xpath", model, modelName);
		WebElement countLocator = generalFunctions.createDynamicLocator(driver, "xpath", modelAvailableCount, modelName);
		availableCount = generalFunctions.getText(driver, countLocator).replaceAll("[()]", "");
		generalFunctions.moveElement(driver, modelLocator);
		generalFunctions.clickElement(driver, modelLocator);
		generalFunctions.waitUntil(3000);
		validateCarTitle(makeFilter, modelFilter);
		validateCarsCount();
		return this;
	}
	public searchCarsPage validateFilterPills(String filterName) {
		Assert.assertEquals(generalFunctions.getText(driver, filterChips), filterName);
		return this;
	}
	public void  validateCarTitle(String make, String model) {
		for (int i=1; i<=totalCarsDisplayed.size(); i++) {
			WebElement title1Locator = generalFunctions.createDynamicLocator(driver, "xpath", carTitle1, String.valueOf(i));
			WebElement title2Locator = generalFunctions.createDynamicLocator(driver, "xpath", carTitle2, String.valueOf(i));
			generalFunctions.moveElement(driver, title1Locator);
			Assert.assertTrue(generalFunctions.getText(driver, title1Locator).contains(make));
			Assert.assertTrue(generalFunctions.getText(driver, title2Locator).trim().contains(model));		
		}
	}
	public void validateCarsCount() {
		generalFunctions.moveElement(driver, totalCountDisplayed);
		Assert.assertEquals(generalFunctions.getText(driver, totalCountDisplayed), availableCount);
	}
	public searchCarsPage clickSort() {
		generalFunctions.moveElement(driver, sortDisplay);
		generalFunctions.clickElement(driver, sortDisplay);
		return this;
	}
	public searchCarsPage selectSort(String sortOption) {
		WebElement optionLocator = generalFunctions.createDynamicLocator(driver, "xpath", dropDownSelection, sortOption);
		generalFunctions.clickElement(driver, optionLocator);
		generalFunctions.waitUntil(3000);
		validateSortDisplay(sortOption);
		validateSorting();
		return this;
	}
	public void validateSortDisplay(String sortOption) {
		Assert.assertEquals(generalFunctions.getText(driver, sortDisplay), sortOption);
	}
	public void validateSorting() {
		// TODO - Need to handle all type of sorting....
		int count = totalCarsDisplayed.size();
		boolean resetSort = false;
		if (count >= 2) {
			for (int i=2; i<=totalCarsDisplayed.size(); i++) {
				String carBadge = carStatusBadge.replace("$regex$", String.valueOf(i));
				if (i >= 3 && resetSort == false && generalFunctions.isElementPresent(driver, carBadge) == true) { 
					resetSort = true; 
					continue; 
				}
				WebElement price1Locator = generalFunctions.createDynamicLocator(driver, "xpath", carValue, String.valueOf(i-1));
				WebElement price2Locator = generalFunctions.createDynamicLocator(driver, "xpath", carValue, String.valueOf(i));
				generalFunctions.moveElement(driver, price1Locator);
				Assert.assertTrue(Integer.parseInt(generalFunctions.getText(driver, price1Locator).replaceAll("[$,]", "")) <= Integer.parseInt(generalFunctions.getText(driver, price2Locator).replaceAll("[$,]", "")));
			}
		} else {
			Reporter.log("Search Car Page - Not able to validate price sort as number of cars displayed are less than 2");
		}
	}
	public searchCarsPage selectFavorite(Integer count) {
		int totalCount = totalCarsDisplayed.size();
		int favCount = 0;
		for(int i=1; i<=count; i++) {
			if (i<=totalCount) {
				favCount++;
				WebElement favoriteLocator = generalFunctions.createDynamicLocator(driver, "xpath", favoriteSelection, String.valueOf(i));
				generalFunctions.moveElement(driver, favoriteLocator);
				generalFunctions.clickElement(driver, favoriteLocator);
				validateFavoriteColor(favoriteLocator);
			} else {
				Reporter.log("Search Car Page - Cars displayed are less than the paramter value of favorite selection");
			}
		}
		generalFunctions.waitUntil(3000);
		headerObj.validateFavoriteSelection(String.valueOf(favCount));
		return this;
	}
	public void validateFavoriteColor(WebElement element) {
		Assert.assertEquals(generalFunctions.getCssValue(driver, element, "color"), "rgba(255, 128, 125, 1)");
	}
	public carDetailsPage selectAvailableCar() {
		int availableIndex = fetchAvailableCar();
		if (availableIndex > 0) {
			WebElement carLocator = generalFunctions.createDynamicLocator(driver, "xpath", carSelection, String.valueOf(availableIndex));
			fetchCarTitle(availableIndex);
			generalFunctions.moveElement(driver, carLocator);
			generalFunctions.clickElement(driver, carLocator);
		} else {
			Reporter.log("Search Car Page - No cars available for selection");
		}
		return new carDetailsPage(driver, selectedCarTitle1, selectedCarTitle2);
	}
	public Integer fetchAvailableCar() {
		int totalCount = totalCarsDisplayed.size();
		for(int i=1; i<=totalCount; i++) {
			String badgeLocator = carStatusBadge.replace("$regex$", String.valueOf(i));
			if (generalFunctions.isElementPresent(driver, badgeLocator) == false) {
				return i;
			}
		}	
		return 0;
	}
	public void fetchCarTitle(int index) {
		//TODO - Need to write these values into excel...
		WebElement title1Locator = generalFunctions.createDynamicLocator(driver, "xpath", carTitle1, String.valueOf(index));
		WebElement title2Locator = generalFunctions.createDynamicLocator(driver, "xpath", carTitle2, String.valueOf(index));
		WebElement title3Locator = generalFunctions.createDynamicLocator(driver, "xpath", carTitle3, String.valueOf(index));
		selectedCarTitle1 = generalFunctions.getText(driver, title1Locator) + " " + generalFunctions.getText(driver, title2Locator);
		selectedCarTitle2 = generalFunctions.getText(driver, title3Locator);
	}
}
