package cd_core;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class testListeners implements ITestListener {
	
	private WebDriver driver;
	
	@Override		
    public void onTestFailure(ITestResult result) {
		String methodName=result.getName().toString().trim();
		Reporter.log("Test failed at" + methodName);
		ITestContext context = result.getTestContext();
		driver = (WebDriver)context.getAttribute("WebDriver");
		generalFunctions.captureScreenshot(driver, methodName);
    }
	@Override
	public void onTestSuccess(ITestResult result) {
		ITestContext context = result.getTestContext();
		driver = (WebDriver) context.getAttribute("WebDriver");
		generalFunctions.showAlert(driver, "Test completed successfully!!!");
	}
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
}
