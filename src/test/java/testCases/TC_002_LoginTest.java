package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass {
	
	@Test(groups = {"sanity", "master"})
	public void verify_Login() {
		
		logger.info("***** starting TC_001_LoginTest *****");
		logger.debug("capturing application debug logs");
		
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("clicked on my account link");
		hp.clickLogin();
		logger.info("clicked on login link");
		
		LoginPage lp=new LoginPage(driver);
		logger.info("entering valid email and password");
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		logger.info("clicked on login button");
		
		MyAccountPage myacc=new MyAccountPage(driver);
		boolean targetpg = myacc.isMyAccountPageExists();
		if (targetpg==true) {
			logger.info("Login Test passed");
			Assert.assertTrue(true);
		}
		else {
			logger.error("Login Test failed");
			Assert.fail();
		}
		}
		catch (Exception e) {
			Assert.fail();
		}
		logger.info("***** finished TC_002_LoginTest *****");
	}
}