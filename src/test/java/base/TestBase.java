package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import base.DriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import extentreports.ExtentListeners;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	
	public static FileInputStream fis;
	public WebDriver driver=null;
	
	
	
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
	
	public void initializeBrowser(String browser, String url) {
		
			if(config.getProperty("isGrid").equalsIgnoreCase("true")) {
			DesiredCapabilities caps =null;
			if(browser.equalsIgnoreCase("chrome")) {
				caps=DesiredCapabilities.chrome();
				caps.setBrowserName("chrome");
				caps.setPlatform(Platform.ANY);
			}else if(browser.equalsIgnoreCase("firefox")){
				caps=DesiredCapabilities.firefox();
				caps.setBrowserName("firefox");
				caps.setPlatform(Platform.ANY);
			}else if(browser.equalsIgnoreCase("ie")) {
				caps=DesiredCapabilities.internetExplorer();
				caps.setBrowserName("iexplore");
				caps.setPlatform(Platform.WIN10);
			}else {
				System.out.println("incorrect browser");
			}
			
			try {
				driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else {

			if(browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}else if(browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}else {
				System.out.println("browser not defined");
			}
		}
		
		
		DriverManager.setWebDriver(driver);
		DriverManager.getDriver().get(url);
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	
	}
	
	public void click(String locator, String elementName) {
		
		if(locator.endsWith("_CSS")) {
			DriverManager.getDriver().findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			DriverManager.getDriver().findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_ID")) {
			DriverManager.getDriver().findElement(By.id(OR.getProperty(locator))).click();
		}
		
		ExtentListeners.test.info("Clicking on element: "+elementName);
		
		
	}
	
	public void type(String locator, String value, String elementName) {

		if(locator.endsWith("_CSS")) {
			DriverManager.getDriver().findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			DriverManager.getDriver().findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			DriverManager.getDriver().findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		ExtentListeners.test.info("Entering"+value+" in : "+elementName);



	}
	
	public void typeWithEnter(String locator, String value, String elementName) {
		if(locator.endsWith("_CSS")) {
			DriverManager.getDriver().findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value, Keys.ENTER);
		}else if(locator.endsWith("_Xpath")) {
			DriverManager.getDriver().findElement(By.xpath(OR.getProperty(locator))).sendKeys(value, Keys.ENTER);
		}else if(locator.endsWith("_ID")) {
			DriverManager.getDriver().findElement(By.id(OR.getProperty(locator))).sendKeys(value, Keys.ENTER);
		}else if(locator.endsWith("_Name")) {
			DriverManager.getDriver().findElement(By.name(OR.getProperty(locator))).sendKeys(value, Keys.ENTER);
		}	
		
		ExtentListeners.test.info("Entering "+value+" in "+elementName);

	}
	
	public static String screenshotName;
	public static void captureScreenshot() {
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
	File scrFile = ((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
	try {
		FileUtils.copyFile(scrFile, new File("./reports/"+screenshotName));
	} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	
	
	public void closeBrowser() {
		DriverManager.getDriver().quit();
	}
}
