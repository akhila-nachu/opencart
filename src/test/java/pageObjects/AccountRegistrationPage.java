package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage{
	
	
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//input[@id='input-firstname']") WebElement txtFirstName;
	@FindBy(xpath = "//input[@id='input-lastname']") WebElement txtLastNmae;
	@FindBy(xpath = "//input[@id='input-email']") WebElement txtEmail;
	@FindBy(xpath = "//input[@id='input-password']") WebElement txtPassword;
	@FindBy(xpath = "//input[@id='input-newsletter']") WebElement chkdNewsletter;
	@FindBy(xpath = "//input[@name='agree']") WebElement chkdPolicy;
	@FindBy(xpath = "//button[@type='submit']") WebElement btnContinue; 
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgConfirmation;
	
	public void setFirstName(String fname) {
		txtFirstName.sendKeys(fname);
	}
	public void setLastName(String lname) {
		txtLastNmae.sendKeys(lname);
	}
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}
	public void checkNewsletter() {
		//chkdNewsletter.click();
		
		Actions act=new Actions(driver); //scroll down until it is in view, find and perform click
		act.moveToElement(chkdNewsletter).click().build().perform();
	}
	public void checkPPolicy() {
		//chkdPolicy.click();
		//chkdPolicy.submit();
		//Actions act=new Actions(driver); //scroll down until it is in view, find and perform click
		//act.moveToElement(chkdPolicy).click().build().perform();
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("arguments[0].scrollIntoView(true)", chkdPolicy);
		chkdPolicy.click();
		
		//WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(30));
		//mywait.until(ExpectedConditions.elementToBeClickable(chkdPolicy)).click();
	}
	public void clickcontinue() {
		//btnContinue.click();
		
		//btnContinue.submit();
		  
		//Actions act=new Actions(driver);
		//act.moveToElement(btnContinue).click().perform();
		
		//JavascriptExecutor js=(JavascriptExecutor)driver; //if an element was hidden js force clicks it
		//js.executeScript("arguments[0].click();", btnContinue);
		
		//btnContinue.sendKeys(Keys.RETURN);
		
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(10)); //explicit wait until the element is found
		mywait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}
	public String getConfirmationMsg()
	{
		try {
			return (msgConfirmation.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}