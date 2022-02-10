package cd_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import cd_core.generalFunctions;

public class carDetailsPage {
	
	private WebDriver driver;
	private String carTitle1;
	
	//Static locators...
	@FindBy(xpath="(//div[contains(@class, 'swiper-slide swiper-slide-active')])[1]/img")
	private WebElement carImage;
	
	@FindBy(xpath="(//div[contains(@class, 'vehicle-title')])[1]")
	private WebElement vehicleTitle1;
	
	@FindBy(xpath="(//div[contains(@class, 'vehicle-title')])[2]")
	private WebElement vehicleTitle2;
	
	@FindBy(xpath="(//button[contains(@class, 'start-purchase-button')])[3]")
	private WebElement purchaseButton;
	
	@FindBy(xpath="//span[contains(text(), 'Confirm')")
	private WebElement warningConfirmButton;
	
	private String warningDescription = "//div[contains(@class, 'message-text-container')]";
	
	public carDetailsPage(WebDriver driver, String carTitle1, String carTitle2) {
		// TODO - Need to add an explicit wait till page element is loaded and then perform actions...
		this.driver = driver;
		PageFactory.initElements(driver, this);
		validateCarImage();
		validateCarTitle(carTitle1, carTitle2);
	}
	public void validateCarImage() {
		generalFunctions.validateImage(driver, carImage);
	}
	public void validateCarTitle(String title1, String title2) {
		Assert.assertTrue(generalFunctions.getText(driver, vehicleTitle1).contains(title1));
		carTitle1 = title1;
		Assert.assertTrue(generalFunctions.getText(driver, vehicleTitle2).contains(title2));
	}
	public purchaseCarPage clickPurchase() {
		generalFunctions.clickElement(driver, purchaseButton);
		//TODO - Identify the business rule of this warning pop up and set the expected based on rule.
		if (generalFunctions.isElementPresent(driver, warningDescription) == true) {
			generalFunctions.clickElement(driver, warningConfirmButton);
		}
		return new purchaseCarPage(driver, carTitle1);
	}

}
