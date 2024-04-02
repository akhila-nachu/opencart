package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups= {"regression","master"})
	public void verify_account_registration()
	{
		logger.debug("application logs started");
		logger.info("***** starting TC_001_AccountRegistrationTest *****"); //info log
		
		try {
			
		HomePage hp=new HomePage(driver);
		
		hp.clickMyAccount();
		logger.info("clicked on my account link");
		hp.clickRegister();
		logger.info("clicked on register link");
		
		logger.info("entering customer details");
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		regpage.setFirstName(randomString().toUpperCase()); //creates random data
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
		
		String pwd=randomAlphaNumeric();
		regpage.setPassword(pwd);
		regpage.checkNewsletter();
		regpage.checkPPolicy();
		regpage.clickcontinue();
		logger.info("submitted customer details");
		String confmsg= regpage.getConfirmationMsg();
		logger.info("validating expected message");
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			logger.info("test passed");
			Assert.assertTrue(true);
		}
		else {
			logger.error("test failed");
			Assert.fail(); 
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch (Exception e) {
			logger.error("test failed"); //error log
			Assert.fail();
		}
		logger.debug("application logs ended");
		logger.info("finished executing testcase");
	}
}