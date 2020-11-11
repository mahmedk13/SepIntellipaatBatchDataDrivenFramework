package testcases;


import java.net.MalformedURLException;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Utilities.DataUtils;
import base.DriverManager;
import base.TestBase;

public class HotelSearch extends TestBase {
	
	@Parameters("browser")
	@BeforeMethod
	public void launchBrowser(String browser) throws MalformedURLException {
		initializeBrowser(browser, config.getProperty("url"));		
	}
	
	@Test(dataProvider="getData", dataProviderClass=DataUtils.class)
	public void searchHotel(String goingTo, String checkinDate, String checkoutDate, String title) {
		click("HotelTab_CSS", "Hotel Tab");
		
		click("GoingToBtn_CSS", "Going to field");
		typeWithEnter("GoingToInput_CSS", goingTo, "Going to input field");

//		click("CheckinDateBtn_CSS", "Checkin date btn");
//		DriverManager.getDriver().findElement(By.xpath("//button[contains(@aria-label,'"+checkinDate+"')]")).click();
//		click("ApplyDateBtn_CSS", "Apply date");
		System.out.println(checkinDate);
//		
//		click("CheckoutDateBtn_CSS", "Checkout date");
//		DriverManager.getDriver().findElement(By.xpath("//button[contains(@aria-label,'"+checkoutDate+"')]")).click();
//		click("ApplyDateBtn_CSS", "Apply date");
		System.out.println(checkoutDate);
		
		click("SearchBtn_CSS", "Search btn");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	 
	   //Assert.assertTrue(DriverManager.getDriver().getTitle().contains("Hyderabad"));
	}
	
	@AfterMethod
	public void tearDown() {
		closeBrowser();
	}
	
	
	
	

}
