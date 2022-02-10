package cd_core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


public class generalFunctions {
	private enum browsers {chrome, firefox, edge};

	public static WebDriver intializeDriver(String browserName) {
		//TODO - Need to handle all browsers.....
		browsers browser = browsers.valueOf(browserName);
		String dir = System.getProperty("user.dir");
		WebDriver driver;
		switch (browser) {
		case chrome:
			System.setProperty("webdriver.chrome.driver", (dir + "\\drivers\\chromedriver.exe"));
			driver = new ChromeDriver();
			break;
		default:
			System.setProperty("webdriver.chrome.driver", "user.dir" + "C:\\Users\\krish\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();

		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}
	public static void navigateTo(WebDriver driver, String url) {
		driver.get(url);
	}
	public static void enterText(WebDriver driver, WebElement element, String text) {
		highlightElement(driver, element);
		element.sendKeys(text);
	}
	public static void highlightElement(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: Red; border: 3px solid Red;");
			waitUntil(500);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",element, "");
		}
		catch(Exception e) {
			Reporter.log("Base Class - Failed on highlighting the element");
		}
	}
	public static void clickElement(WebDriver driver, WebElement element) {
		highlightElement(driver, element);
		element.click();
	}
	public static void waitForElementVisibility(WebDriver driver, WebElement element, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		element = wait.until(ExpectedConditions.visibilityOf(element));
	}
	public static void validateImage(WebDriver driver, WebElement element) {
		highlightElement(driver, element);
		Assert.assertNotEquals(element.getAttribute("naturalWidth"), "0");
	}
	public static WebElement createDynamicLocator(WebDriver driver, String type, String locator, String dynamicValue) {
		//TODO - Need to handle all type of locators....
		WebElement element = null;
		locator = locator.replace("$regex$", dynamicValue);
		switch(type) {
		case "xpath":
			element = driver.findElement(By.xpath(locator));
			break;
		}
		return element;
	}
	public static boolean isElementPresent(WebDriver driver, String path) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean elementPresent = (driver.findElements(By.xpath(path)).size() > 0);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return elementPresent;
	}
	public static String getText(WebDriver driver, WebElement element) {
		highlightElement(driver, element);
		return element.getText().trim();
	}
	public static String getCssValue(WebDriver driver, WebElement element, String propertyName) {
		highlightElement(driver, element);
		return element.getCssValue(propertyName).trim();
	}
	public static void waitUntil(int time) {
		try {
			Thread.sleep(time);	
		}
		catch(Exception e) {
			Reporter.log("Base Class - Error on wait function");
		}
	}
	public static void moveElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element);
		action.build().perform();
	}
	public static void showAlert(WebDriver driver, String message) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert( '" + message + "' );");
		waitUntil(3000);
	}
	public static void captureScreenshot(WebDriver driver, String methodName) {
		String copyPath = System.getProperty("user.dir") + "\\test-output\\screenshots";
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		copyPath += "\\" + methodName + timeStamp + ".png"; 
		TakesScreenshot scrShot = (TakesScreenshot)driver;
		File screenshot=scrShot.getScreenshotAs(OutputType.FILE);
		File copyTo=new File(copyPath);
		try {
			FileUtils.copyFile(screenshot, copyTo);
		}
		catch(Exception e) {
			Reporter.log("Error in copying the screenshot to output folder");
		}  
	}
}
