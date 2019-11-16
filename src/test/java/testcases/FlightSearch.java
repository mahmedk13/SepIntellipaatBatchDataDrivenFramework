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
import org.testng.annotations.Test;

import Utilities.DataUtils;
import base.TestBase;
import extentreports.ExtentListeners;

public class FlightSearch extends TestBase {
	
	@BeforeSuite
	public void beforeSuite() {
		super.setUp();
	}
	
	@BeforeMethod
	public void launchBrowser() {
		init(config.getProperty("browser"), config.getProperty("url"));
	}
	
	@Test
	public void verifyHomePage() {
		String actualTitle = driver.getTitle();
		String expectedTitle = config.getProperty("title");
		
		//ExtentListeners.test.info("Expected title is "+expectedTitle);
		//ExtentListeners.test.info("Actual title is "+actualTitle);
		Assert.fail();
		//Assert.assertEquals(actualTitle, expectedTitle);
		//ExtentListeners.test.pass("Actual title and Expected Title are equal");

	
	}
	
	@Test(dataProvider="getData", dataProviderClass = DataUtils.class)
	public void flightSearch(String origin, String departDate, String returnDate, String destination, String validateText) {
		
		click("FlightTab_ID", "Flight Tab");
		type("Origin_XPATH", origin, "Origin text field");
		type("DepartDate_XPATH", departDate, "Departure Date text field");
		type("ReturnDate_XPATH", returnDate, "Return Date text field");
		type("Destination_XPATH", destination, "Destination text field");
		click("FlightSubmit_XPATH", "Search Button");
		
		String text = driver.findElement(By.className("title-city-text")).getText();
		ExtentListeners.test.info("Actual text is "+text);
		ExtentListeners.test.info("Expected text is "+validateText);

		
		Assert.assertTrue(text.contains(validateText));
		ExtentListeners.test.pass("User is able to search flight");
		
		
	
	}
	
	
	
	
	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
	}
	
	
	
	

}
