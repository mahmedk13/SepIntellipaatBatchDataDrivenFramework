package testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Utilities.DataUtils;
import base.DriverManager;
import base.TestBase;
import extentreports.ExtentListeners;

public class FlightSearch extends TestBase {
	
	@BeforeSuite
	public void beforeSuite() {
		super.setUp();
	}
	
	@Parameters("browser")
	@BeforeMethod
	public void launchBrowser(String browser) {
		init(browser, config.getProperty("url"));
		//adding this comment for git learning purpose
	}
	
	@Test(enabled=false)
	public void verifyHomePage() {
		//String actualTitle = driver.getTitle();
		String expectedTitle = config.getProperty("title");
		
		//ExtentListeners.test.info("Expected title is "+expectedTitle);
		//ExtentListeners.test.info("Actual title is "+actualTitle);
		Assert.fail();
		//Assert.assertEquals(actualTitle, expectedTitle);
		//ExtentListeners.test.pass("Actual title and Expected Title are equal");

	
	}
	
	@Test(dataProvider="getData", dataProviderClass = DataUtils.class)
	public void flightSearch(String origin, String departDate, String returnDate, String destination, String validateText) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		click("FlightTab_ID", "Flight Tab");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		type("Origin_XPATH", origin, "Origin text field");
		type("DepartDate_XPATH", departDate, "Departure Date text field");
		type("ReturnDate_XPATH", returnDate, "Return Date text field");
		type("Destination_XPATH", destination, "Destination text field");
		click("FlightSubmit_XPATH", "Search Button");
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = DriverManager.getDriver().findElement(By.className("title-city-text")).getText();
		ExtentListeners.test.info("Actual text is "+text);
		ExtentListeners.test.info("Expected text is "+validateText);

		
		Assert.assertTrue(text.contains(validateText));
		ExtentListeners.test.pass("User is able to search flight");
		
		
	
	}
	
	
	
	
	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(5000);
		closeBrowser();
		
	}
	
	
	
	

}
