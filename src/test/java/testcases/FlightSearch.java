package testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class FlightSearch extends TestBase {
	
	@BeforeSuite
	public void init() {
		super.setUp();
	}
	
	@Parameters("browser")
	@BeforeMethod
	public void launchBrowser(String browser) throws MalformedURLException {
		initializeBrowser(browser, config.getProperty("url"));		
	}
	
	@Test(enabled=false)
	public void verifyHomePage() {
		String actualTitle = driver.getTitle();
		String expectedTitle=config.getProperty("title");
		System.out.println();
		
		Assert.assertEquals(actualTitle, expectedTitle);	
	}
	
	
	@Test(dataProvider="getData", dataProviderClass=DataUtils.class)
	public void searchFlight(String origin, String destination, String departDate, String returnDate, String validateText) {
		
		
		click("FlightTab_CSS", "Flight Tab");
		
		click("OriginBtn_CSS", "Origin input field");
		typeWithEnter("OriginInput_CSS", origin, "Origin input field");

		click("DepartBtn_CSS", "Departure Input field");
		typeWithEnter("DepartInput_CSS", destination, "destination input field");

//		click("DepartDateBtn_CSS", "Departure Date field");
//		DriverManager.getDriver().findElement(By.xpath("//button[@aria-label='"+departDate+"']")).click();
//		click("ApplyDateBtn_CSS", "Apply button on Departure Date");
//		
//		click("ReturnDateBtn_CSS", "Return Date field");
//		DriverManager.getDriver().findElement(By.xpath("//button[@aria-label='"+returnDate+"']")).click();
//		click("ApplyDateBtn_CSS", "Apply button on Return Date");
//		
		System.out.println(departDate);
		System.out.println(returnDate);
		click("SearchBtn_CSS", "Search Button");

		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String actualValidateText = DriverManager.getDriver().findElement(By.xpath(OR.getProperty("ValidateText_Xpath"))).getText();
		Assert.assertEquals(actualValidateText, validateText);

	}
	
	@AfterMethod
	public void tearDown() {
		closeBrowser();
	}
	
	
	

	
	

}
