package dbmi.hms.harvard.edu.authentication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

public class AuthTypes {
	private static WebDriverWait wait;
	private static String username = System.getProperty("username");
	private static String password = System.getProperty("password");
	
	Map<String, String> authTypes = new HashMap<String, String>() {
		{
			put("HMS", "Harvard Medical School");

		}
	};

	public void doAuth(WebDriver driver, Map testPlan) throws InterruptedException, IOException {
		switch (testPlan.get("authmethod").toString()) {
		case "HMS":
			doHMSAuth(driver, testPlan);
		case "PUB":
			dopublicauth(driver, testPlan);
			break;
		case "GOOGLELOGIN":
			doGoogleLogin(driver, testPlan);
			break;
		case "ADMINLOGIN":
			doAdminLogin(driver, testPlan);
			break;
		case "ERACOMMON":
			doEraCommonLogin(driver, testPlan);
			break;
	
			
		}
	}

	private void doEraCommonLogin(WebDriver driver, Map testPlan) throws InterruptedException, IOException {
		
try {
			
			System.out.println("**********************Login with  eRA Common account***************");
			String usernamebox = "//input[@id='USER']";
			String passwordbox = "//input[@id='PASSWORD']";
			String signinButton = "//button[@type='submit']";
			String yesAuthorize ="//button[@id='yes']";
			String IAgree ="//input[@name='_eventId_proceed']";
				
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 40);
			File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='fence-login-btn']"))).click();
			
			
			FileUtils.copyFile(file, new File("screensusername.png"));
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(usernamebox))).sendKeys(username);
			
			File filePass = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(filePass, new File("screenpassword.png"));
					
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordbox))).sendKeys(password);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(signinButton))).click();
						
		    
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(IAgree))).click();
		    
		    File IAgreeScreen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				
		
		    FileUtils.copyFile(IAgreeScreen, new File("IAgree.png"));
		    
		    
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(yesAuthorize))).click();
					
			//actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();

			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	private void doAdminLogin(WebDriver driver, Map testPlan) throws InterruptedException {
		try {
			
			String usernamebox = ".//*[@id='j_username']";
			String passwordbox = ".//*[@id='j_password']";
			String submitbutton = ".//*[@id='loginButton']";
			//String analyzeMenu = ".//*[@id='menuLinks']/tbody/tr/th[3]";
			String analyzeMenu =".//*[@class='menuVisited']";
			driver.findElement(By.xpath(usernamebox)).sendKeys(testPlan.get("username").toString());
			driver.findElement(By.xpath(passwordbox)).sendKeys(testPlan.get("password").toString());
			//Thread.sleep(10000);
			driver.findElement(By.xpath(submitbutton)).click();
			Thread.sleep(20000);
			if (driver.findElements(By.xpath(".//span[text()='Comparison']")).size() != 0) {
				System.out.println("The default page is Dataset Explorer");
							}
			else{
			driver.findElement(By.xpath(analyzeMenu)).click();
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doGoogleLogin(WebDriver driver, Map testPlan) throws InterruptedException, IOException {

		try {
			
			System.out.println("**********************Login with  Google account***************");
			String usernamebox = ".//input[@type='email']";
			String nextButtonUser =".//*[@id='identifierNext']";
			String passwordbox = ".//input[@type='password']";
			String nextButtonPass = ".//*[@id='passwordNext']";
			String submitbutton = "//*[@id='ctl00_ContentPlaceHolder1_SubmitButton']";
	        
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(5000);
			wait = new WebDriverWait(driver, 40);
		
			File googleLogin = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Login with Google')]"))).click();
					
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        FileUtils.copyFile(file, new File("screenshotclickLogin.png"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(usernamebox))).sendKeys(username);
		
			File capUserName = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(capUserName, new File("usernamescreenshot.png"));
					
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nextButtonUser))).click();		
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordbox))).sendKeys(password);
	        
	        File capPasswd = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(capPasswd, new File("passwordscreenshot.png"));
			Thread.sleep(7000);
			
		
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doHMSAuth(WebDriver driver, Map testPlan) {

		try {

			String usernamebox = ".//*[@id='ctl00_ContentPlaceHolder1_UsernameTextBox']";
			String passwordbox = "//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']";
			String submitbutton = "//*[@id='ctl00_ContentPlaceHolder1_SubmitButton']";

			driver.findElement(By.xpath(usernamebox)).sendKeys(testPlan.get("username").toString());
			driver.findElement(By.xpath(passwordbox)).sendKeys(testPlan.get("password").toString());

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")).sendKeys(PASSWORD);
	}

	public void dopublicauth(WebDriver driver, Map testPlan) {
		try {

			String publicLogin = ".//*[@id='public']";
			driver.findElement(By.xpath(publicLogin)).click();

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")).sendKeys(PASSWORD);
	}

	public Map<String, String> getAuthTypes() {
		return authTypes;
	}

	public void setAuthTypes(Map<String, String> authTypes) {
		this.authTypes = authTypes;
	}

}
