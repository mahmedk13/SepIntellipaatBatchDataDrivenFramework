package extentreports;

import java.io.IOException;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.TestBase;

public class ExtentListeners implements ITestListener {
	
	static Date d = new Date();
	static String filename = "Extent.html";
	
	private static ExtentReports extent = ExtentManager.createInstance(".\\reports\\"+filename);
	public static ExtentTest test;
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		String methodName = result.getMethod().getMethodName();
		String text = "<b>"+"Test Case: "+methodName.toUpperCase()+" PASSED"+"</b";
		Markup m = MarkupHelper.createLabel(text, ExtentColor.GREEN);
		test.pass(m);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		
		TestBase.captureScreenshot();
		
		try {
			test.fail("<b>"+"font color="+"red"+"Screenshot of failure"+"</font>"+"</b>", MediaEntityBuilder.createScreenCaptureFromPath(TestBase.screenshotName).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String methodName = result.getMethod().getMethodName();
		String text = "<b>"+"Test Case: "+methodName.toUpperCase()+" FAILED"+"</b";
		Markup m = MarkupHelper.createLabel(text, ExtentColor.RED);
		test.fail(m);
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
		if(extent!=null) {
			extent.flush();
		}
		
	}

}
