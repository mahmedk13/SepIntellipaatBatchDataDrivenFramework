package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Utilities.DataUtils;
import base.TestBase;
import extentreports.ExtentListeners;

public class HotelSearch extends TestBase {
	
	
	
	@BeforeMethod
	public void beforeMethod() {
		init(config.getProperty("browser"), config.getProperty("url"));
	}
	
	@Test(dataProvider="getData", dataProviderClass = DataUtils.class)
	public void hotelSearch(String goingto, String checkin, String checkout, String validateTitle ) {
		click("HotelTab_ID", "Hotel Tab");
		type("Going_XPATH", goingto, "Going To text field");
		type("Checkin_XPATH", checkin, "Checkin date text field");
		type("Checkout_XPATH", checkout, "Checkout date text field");
		click("HotelSearch_XPATH", "Search Button");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String title = driver.getTitle();
		ExtentListeners.test.info("Actual title is "+title);
		ExtentListeners.test.info("Expected title is "+validateTitle);

		Assert.assertEquals(title, validateTitle);
		ExtentListeners.test.pass("User is able to search Hotel");

	
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
