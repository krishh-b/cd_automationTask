package cd_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import cd_core.generalFunctions;

public class commonHeader {
	
	private WebDriver driver;
	
	//Static locators....
	@FindBy(xpath="//img[@alt='Canada Drives Logo']")
	private WebElement headerLogo;
	
	@FindBy(xpath="//span[contains(@class, 'v-badge__wrapper')]/span")
	private WebElement favoriteCount;
	
	public commonHeader(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void validateHeaderImage() {
		generalFunctions.validateImage(driver, headerLogo);
	}
	public void validateFavoriteSelection(String count) {
		Assert.assertEquals(generalFunctions.getText(driver, favoriteCount), count);
	}
}
