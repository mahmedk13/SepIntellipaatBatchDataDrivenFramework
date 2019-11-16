package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import extentreports.ExtentListeners;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	
	public static FileInputStream fis;
	public static WebDriver driver=null;
	
	public void setUp() {
		try {
			fis = new FileInputStream("./src/test/resources/propertyfiles/config.properties");
			config.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			fis = new FileInputStream("./src/test/resources/propertyfiles/OR.properties");

			OR.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void init(String browser, String url) {
		
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else {
			System.out.println("browser not defined");
		}
		
		driver.get(url);
		driver.manage().window().maximize();
		
	}
	
	public void click(String locator, String elementName) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		
		ExtentListeners.test.info("Clicking on element: "+elementName);
		
		
	}
	
	public void type(String locator, String value, String elementName) {

		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		ExtentListeners.test.info("Entering"+value+" in : "+elementName);



	}
	
	public static String screenshotName;
	public static void captureScreenshot() {
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("./reports/"+screenshotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
