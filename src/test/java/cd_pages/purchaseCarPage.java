package cd_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import cd_core.generalFunctions;

public class purchaseCarPage {
	
	private WebDriver driver;
	private String addressline1;
	private String addressline2;
	
	//Static Locators...
	@FindBy(xpath="//div[contains(text(), 'Calculate Delivery')]")
	private WebElement calculateDeliveryButton;
	
	@FindBy(xpath="//input[@placeholder='Delivery to this address']")
	private WebElement addressInputBox;
	
	@FindBy(xpath="//div[contains(@class,'pcaselected')]")
	private WebElement addressSuggestionSelection;
	
	@FindBy(xpath="//span[contains(text(), 'Save and Confirm')]/parent::button")
	private WebElement addressSaveButton;
	
	@FindBy(xpath="//div[contains(text(), 'Select Warranty')]")
	private WebElement warrantySelectionButton;
	 
	@FindBy(xpath="//button[contains(@class, 'save-and-confirm-btn')]")
	private WebElement warrantySaveButton;
	
	@FindBy(xpath="//p[contains(@class, 'app-cpost-result__address')]/span")
	private WebElement addressPart1;
	
	@FindBy(xpath="//p[contains(@class, 'app-cpost-result__address')]")
	private WebElement addressPart2;
	
	@FindBy(xpath="(//div[contains(@class, 'vehicle-price-cards__item')])[1]")
	private WebElement deliveryButton;
	
	@FindBy(xpath="(//div[contains(@class, 'vehicle-price-cards__item')])[2]")
	private WebElement warrantyButton;
	
	@FindBy(xpath="(//div[contains(@class, 'vehicle-info__text')])[2]/div[1]")
	private WebElement carTitle;
	
	//Dynamic Locators...
	private String warrantySelection = "(//div[contains(text(), '$regex$')]//..//..)[1]";

	public purchaseCarPage(WebDriver driver, String carTitle) {
		// TODO - Need to add an explicit wait till page element is loaded and then perform actions...
		this.driver = driver;
		PageFactory.initElements(driver, this);
		validateCarTitle(carTitle);
	}
	
	public void validateCarTitle(String title) {
		Assert.assertTrue(generalFunctions.getText(driver, carTitle).contains(title));
	}
	
	public purchaseCarPage clickDelivery() {
		generalFunctions.clickElement(driver, calculateDeliveryButton);
		return this;
	}
	public purchaseCarPage enterAddress(String line1, String line2) {
		generalFunctions.waitUntil(2000);
		addressline1 = line1;
		addressline2 = line2;
		generalFunctions.enterText(driver, addressInputBox, line1);
		generalFunctions.waitUntil(2000);
		generalFunctions.enterText(driver, addressInputBox, line2);
		generalFunctions.waitUntil(2000);
		return this;
	}
	public purchaseCarPage selectAddress() {
		generalFunctions.clickElement(driver, addressSuggestionSelection);
		generalFunctions.waitUntil(3000);
		validateAddress(addressline1, addressline2);
		return this;
	}
	public void validateAddress(String address1, String address2) {
		Assert.assertTrue(generalFunctions.getText(driver, addressPart1).contains(address1));
		Assert.assertTrue(generalFunctions.getText(driver, addressPart2).contains(address2));	
	}
	public purchaseCarPage clickAddressSave() {
		generalFunctions.clickElement(driver, addressSaveButton);
		generalFunctions.waitUntil(2000);
		validateDeliverySelection();
		return this;
	}
	public void validateDeliverySelection() {
		String deliveryStyle = generalFunctions.getCssValue(driver, deliveryButton, "border");
		Assert.assertEquals(deliveryStyle, "1px solid rgb(0, 206, 217)");
	}
	public purchaseCarPage clickWarranty() {
		generalFunctions.clickElement(driver, warrantySelectionButton);
		return this;
	}
	public purchaseCarPage selectWarranty(String warranty) {
		WebElement element = generalFunctions.createDynamicLocator(driver, "xpath", warrantySelection, warranty);
		generalFunctions.clickElement(driver, element);
		return this;
	}
	public purchaseCarPage clickWarrantySave() {
		generalFunctions.clickElement(driver, warrantySaveButton);
		generalFunctions.waitUntil(2000);
		validateWarrantySelection();
		generalFunctions.waitUntil(2000);
		return this;
	}
	public void  validateWarrantySelection() {
		String warrantyStyle = generalFunctions.getCssValue(driver, warrantyButton, "border");
		Assert.assertEquals(warrantyStyle, "1px solid rgb(0, 206, 217)");		
	}

}
