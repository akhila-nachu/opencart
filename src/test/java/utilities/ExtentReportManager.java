package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener { //ERM is a listener class implements interface
	public ExtentSparkReporter sparkReporter; //UI
	public ExtentReports extent; //provides common info to report
	public ExtentTest test; //for creating actual tests in the reports & updating status

	String repName;

	public void onStart(ITestContext testContext) { //generate an empty report before starting all tests
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); //obj, to create date format
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt); //passing the date format */
		
		//generating report name dynamically along with timestamp using SimpleDateFormat and Date classes, so we can maintain history of reports
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		repName = "Test-Report-" + timeStamp + ".html"; //Test-Report+timeStamp+.html, dynamically generating report name
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); //location to generate the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("opencart Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK); //bg theme
		
		//common info posted into the report
		extent = new ExtentReports(); //creating obj for reports
		extent.attachReporter(sparkReporter); //attaching sparkreporter obj for reports class
		extent.setSystemInfo("Application", "opencart"); //name of app
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); //getting un dynam,this method returns values of env variables like name, dir, os
		extent.setSystemInfo("Environemnt", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os"); //dynamically getting parameters from xml using testContext which gets info from tests
		extent.setSystemInfo("Operating System", os); //adding that os to report
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser"); //dyn getting browser param from currentxml file using testContext method
		extent.setSystemInfo("Browser", browser); //and post that info to report
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups(); //group of tests that are executed 
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) { //here result is a methodname
	
		test = extent.createTest(result.getTestClass().getName()); //creates an entry in report for classname ie, new testcase entry in report
		test.assignCategory(result.getMethod().getGroups()); // to display from which groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed"); //updates pass result of the testcase
		
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL,result.getName()+" got failed"); //fail result
		test.log(Status.INFO, result.getThrowable().getMessage()); //reason for test fail ie, errorr message
		
		try { //captures ss and provide name to it to update test fail
			String imgPath = new BaseClass().captureScreen(result.getName()); //targetFilePath from BaseClass is received here and stored in imgPath
			test.addScreenCaptureFromPath(imgPath); //adds ss to report
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
		
		extent.flush(); //mandotory method to execute all testcases
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName; //opens the report automatically in browser after test execution
		File extentReport = new File(pathOfExtentReport); //passing report path in file obj
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*//to send report to team as soon as its generated
		 * try { URL url = new
		 * URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		 * 
		 * // Create the email message 
		 * ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googlemail.com"); 
		 * email.setSmtpPort(465);
		 * email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password")); 
		 * email.setSSLOnConnect(true);
		 * email.setFrom("pavanoltraining@gmail.com"); //Sender
		 * email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report....");
		 * email.addTo("pavankumar.busyqa@gmail.com"); //Receiver 
		 * email.attach(url, "extent report", "please check report..."); 
		 * email.send(); // send the email 
		 * }
		 * catch(Exception e) { e.printStackTrace(); }
		 */
	}
}