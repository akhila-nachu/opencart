package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger; //predef class from Apache log4j
	public Properties p;
	
	@BeforeClass(groups = {"regression","sanity","master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String br) throws IOException
	{
		//loading properties file
		FileReader file=new FileReader(".//src/test/resources/config.properties"); //open file in reading mode, .// represents current project location
		p=new Properties();
		p.load(file);
		
		//apply logs on testcases
		logger= LogManager.getLogger(this.getClass()); //loading log4j file, we have to pass testcase class name here, this keyword takes classname dynamically
		//getlogger() returns logger type of object
		
		
		//driver=new ChromeDriver();
		//launching browser based on conditions
		//remote environment, getting exec_env from config.properties file, ie GRID
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities(); //predef class to specify os/platform & browser
			//os, getting os from xml file
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WINDOWS);
			}
			else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("No matching OS");
				return;
			}
			//browser
			switch (br.toLowerCase()) {
			case "chrome":capabilities.setBrowserName("chrome");
				break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge");
				break;
			default:System.out.println("No matching browser");
				return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities); //RemoteWD for GRID
		}
		
		//local environment, getting browser name from xml file
		else if (p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
		switch (br.toLowerCase()) 
		{
		case "chrome": driver=new ChromeDriver();	break;
		case "edge": driver=new EdgeDriver(); break;
		case "firefox": driver=new FirefoxDriver();
		default: System.out.println("browser not matched");
			return;
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
		//driver.get("http://localhost/opencartshop/");
		driver.get(p.getProperty("appURL")); //getting the app url value from config.properties file
		driver.manage().window().maximize();
		}
	}
	
	@AfterClass(groups = {"regression","sanity","master"})
	public void tearDown()
	{
		driver.close();
	}
	
	//predef class to generate random test data
	public String randomString() {
		String generatedString=RandomStringUtils.randomAlphabetic(5); //3rd party class, added dependency in pom.xml commons-lang3
		return generatedString;
		}
	public String randomnumber() {
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
		}
	public String randomAlphaNumeric() {
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		return (str+"@"+num);
		}

	public String captureScreen(String tname) throws IOException { //we call this method from ExtentReportManager class by passing name from result obj
		
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()); //ss- method name with timestamp
		
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver; //takes ss
		File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname +"_"+timeStamp+".png"; //name of ss
		File targetFile=new File(targetFilePath); //creating our own file
		
		sourceFile.renameTo(targetFile); //passing sf to tf
		return targetFilePath;
	}
}