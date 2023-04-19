package dbmi.hms.harvard.edu.testplans;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.time.temporal.ValueRange;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import dbmi.hms.harvard.edu.authentication.AuthTypes;
import dbmi.hms.harvard.edu.authentication.TestSessionStorage;
import dbmi.hms.harvard.edu.quickstartmodules.QueryBuilder;
//import dbmi.hms.harvard.edu.authentication.AuthTypes;
import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.results.SummaryStatisticsResults;

public class QueryBuilderTestPlan extends Testplan {
	private static final int TIMEOUT = 30;
	private String DragConcept = ".//*[@id='ext-gen157']/div/table/tbody/tr/td";
	private String patientCountSubset2 = "//td[@colspan='2']//table[@width='100%']//tbody//tr//td[@align='center']//table[@class='analysis']//tbody//tr//td[3]";
	private Set<String> subset1;
	private Set<String> subset2;
	private Set<String> subsetmul1;
	private static WebDriver driver;
	private static WebDriverWait wait;
	private String FractalisType;
	private String SearchBoxField = "//input[@placeholder='Search...']";
	private By SearchBox = By.xpath(".//*[@id='filter-list']/div/div/div[1]/div[1]/div[2]/input");
	private String queryBuilderSearchBox = "//input[@class='search-box form-control']";
	private By StudiesTab = By.xpath("//li[@data-toggle='tooltip']/a");
	private By SearchBoxTwo = By.xpath(".//*[@id='filter-list']/div[2]/div/div[1]/div[1]/div[2]/input");
	private By SearchBoxAutocompleteListBox = By
			.xpath("//div[@class='tab-pane active']//div[@class='search-result-list']");
	private static By SearchBoxAutocompleteListBoxItems = By.xpath(
			"//div[@class='tab-pane active']//div[@class='search-result-list']/div/div//span[@class='autocomplete-term']");
	public String downloadFilepath = "D:\\picsure";
	private By SearchBoxSecondAutocompleteListBox = By
			.xpath("//div[@id='examination']//div[@class='search-result-list']");
	private By EmptyFieldByNumeric = By.xpath("//div[contains(text(),'Value invalid! Correct invalid fields.')]");
	private By patientCountValue = By.xpath("//*[@id='patient-count']");
	private String userProfile = "//a[@id='user-profile-btn']";
	private static By SearchBoxSecondAutocompleteListBoxItems = By.xpath("//span[contains(text(),'mean')]");
	private static final Logger LOGGER = Logger.getLogger(QueryBuilderTestPlan.class.getName());
	private static String downloadPath = System.getProperty("dirofdownloadedfiles");
	private String dataAccess = "//a[contains(text(),'Data Access')]";
	// private String dataAccessExploreNow = "//button[contains(text(),'Explore
	// Now')]";

	private String dataAccessExploreOpenAccess = "//button[@data-href='/picsureui/openAccess']";
	private String dataAccessExploreAuthorizedAccess = "//button[@data-href='/picsureui/queryBuilder']";
	private String dataAccessAuthorizationAccess = "//a[@id='query-builder-btn']";
	private String helpTab = "//span[contains(text(),'Help')]";
	private String contactus = "//a[contains(text(),'Contact Us')]";
	private String closingButton = "//button[@class='close']";
	private String aplicationButton = "//button[contains(text(),'Applications')]";
	private String logoutButton = "//a[@id='logout-btn']";
	private String InputNumericValueTextBox = "//input[contains(@class,'constrain-value constrain-value-one form-control value-operator')]";

	public QueryBuilderTestPlan() {
	}

	public Set<String> getSubset1() {
		return subset1;
	}

	public void setSubset1(Set<String> subset1) {
		this.subset1 = subset1;
	}

	public Set<String> getSubset2() {
		return subset2;
	}

	public void setSubset2(Set<String> subset2) {
		this.subset2 = subset2;
	}

	public Set<String> getSubsetmul1() {
		return subsetmul1;
	}

	/*
	 * public void setSubset1Multiple(Set<String> subsetmul1) { this.subsetmul1
	 * = subsetmul1; }
	 */

	public void setSubset1Multiple(Set<String> subsetmul1) {
		this.subsetmul1 = subsetmul1;
	}

	/*
	 * public Set<String> getRelational() { return relational; }
	 * 
	 * public void setRelational(Set<String> relational) { this.relational =
	 * relational; }
	 */

	@SuppressWarnings("deprecation")
	public void launchApp() throws InterruptedException {

		// String browserName = (String) testPlan.get("browser");

		String browser = System.getProperty("browserName").toLowerCase().replaceAll(" ", "");
		System.out.println("The launched browser is " + browser);
		// String browser = browserName.toLowerCase().replaceAll(" ", "");
		switch (browser) {
		/*
		 * case "chrome": DesiredCapabilities capability =
		 * DesiredCapabilities.chrome(); // To Accept SSL certificate
		 * capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); //
		 * setting system property for Chrome browser
		 * System.setProperty("webdriver.chrome.driver",
		 * System.getProperty("googlechromepath")); driver = new
		 * ChromeDriver(capability); driver.manage().window().maximize(); break;
		 */

		case "chrome":

			System.setProperty("webdriver.chrome.driver", System.getProperty("googlechromepath"));
			// String downloadFilepath = "D:\\picsure";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("prefs", chromePrefs);
			chromeOptions.addArguments("--allow-insecure-localhost");
			chromeOptions.addArguments("window-size=1920,1080");
			chromeOptions.setCapability("acceptInsecureCerts", true);
			driver = new ChromeDriver(chromeOptions);
			driver.manage().timeouts().implicitlyWait(17, TimeUnit.SECONDS);

			/*
			 * ChromeOptions chromeOptions = new ChromeOptions();
			 * chromeOptions.addArguments("--allow-insecure-localhost");
			 * chromeOptions.addArguments("window-size=1920,1080");
			 * chromeOptions.setCapability("acceptInsecureCerts", true); driver
			 * = new ChromeDriver(chromeOptions);
			 * driver.manage().timeouts().implicitlyWait(17, TimeUnit.SECONDS);
			 */
			break;

		case "safari":

			// TO -DO

		case "firefox":

			FirefoxOptions options = new FirefoxOptions();
			String userAgent = "DN";
			options.addPreference("general.useragent.override", userAgent);
			System.setProperty("webdriver.gecko.driver", System.getProperty("geckodriverpath"));
			driver = new FirefoxDriver(options);
			driver.manage().window().maximize();

			// WebDriver webDriver = new FirefoxDriver(options);

			// webDriver.get("http://whatsmyuseragent.org");

			/*
			 * FirefoxOptions options = new FirefoxOptions();
			 * options.setCapability("marionette", false); WebDriver driver =
			 * new FirefoxDriver(options); driver = new FirefoxDriver(options);
			 * driver.manage().window().maximize();
			 */
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			break;

		case "chromeheadless":

			System.setProperty("webdriver.chrome.driver", System.getProperty("googlechromepath"));
			HashMap<String, Object> chromePrefsch = new HashMap<String, Object>();
			chromePrefsch.put("profile.default_content_settings.popups", 0);
			chromePrefsch.put("download.default_directory", downloadFilepath);
			ChromeOptions chromeOptionsHeadless = new ChromeOptions();
			chromeOptionsHeadless.addArguments("--disable-gpu");
			chromeOptionsHeadless.addArguments("--start-maximized");
			chromeOptionsHeadless.addArguments("--headless");
			chromeOptionsHeadless.addArguments("--allow-insecure-localhost");
			chromeOptionsHeadless.addArguments("window-size=1920,1080");

			chromeOptionsHeadless.setCapability("acceptInsecureCerts", true);
			chromeOptionsHeadless.setExperimentalOption("prefs", chromePrefsch);
			driver = new ChromeDriver(chromeOptionsHeadless);
			driver.manage().timeouts().implicitlyWait(17, TimeUnit.SECONDS);
			break;

		case "firefoxheadless":
			System.setProperty("webdriver.gecko.driver", System.getProperty("geckodriverpath"));
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			// firefoxBinary.addCommandLineOptions("--headless");
			FirefoxProfile profile = new FirefoxProfile();
			// profile.setPreference("browser.helperApps.neverAsk.openFile",
			// "text/csv,application/csv,application/text,application/json");

			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("security.insecure_password.ui.enabled", false);
			profile.setPreference("security.insecure_field_warning.contextual.enabled", false);
			profile.setPreference("browser.download.dir", "/tmp/");

			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/octet-stream,application/csv,text/csv,application/vnd.ms-excel,application/json");
			DesiredCapabilities dc = DesiredCapabilities.firefox();
			dc.setCapability(FirefoxDriver.PROFILE, profile);
			dc.setCapability("marionette", true);
			dc.setPlatform(Platform.WINDOWS);
			// dc.setPlatform(Platform.LINUX);
			FirefoxOptions opt = new FirefoxOptions();
			opt.merge(dc);
			FirefoxOptions firefoxOptions = new FirefoxOptions(opt);
			firefoxOptions.setBinary(firefoxBinary);
			driver = new FirefoxDriver(firefoxOptions);
			driver.manage().window().maximize();

		}

		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Launching the Browser>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
		LOGGER.info("");
		LOGGER.info("********************************Loading the site****" + testPlan.get("url").toString()
				+ "********************************");
		driver.get(testPlan.get("url").toString());
	}

	public void verifyPicsureUILaunch(Reporter reporter) throws InterruptedException, Exception {

		// String value
		// ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMT05HX1RFUk1fVE9LRU58Z29vZ2xlLW9hdXRoMnwxMDI5NDE0MzE2MjgyOTYyNzIyMDEiLCJuYW1lIjoiZ29vZ2xlLW9hdXRoMnwxMDI5NDE0MzE2MjgyOTYyNzIyMDEiLCJpc3MiOiJlZHUuaGFydmFyZC5obXMuZGJtaS5wc2FtYSIsImV4cCI6MTY0NDU5MTU0NSwiaWF0IjoxNjQxOTk5NTQ1LCJlbWFpbCI6ImF0dWwuc2FuYWdhckBnbWFpbC5jb20iLCJqdGkiOiJ3aGF0ZXZlciJ9.2I2YEOhp-5vRjmHba46oChgSAL8y94VFdFaDHi3wYfQ";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Thread.sleep(5000);
		driver.navigate().refresh();
		wait = new WebDriverWait(driver, 20);
		Thread.sleep(5000);
		// driver.findElement(By.xpath("//a[@id='query-builder-btn']")).click();

		try {
			/*Assert.assertTrue(driver.findElements(By.xpath("//span[@class='a0-button-text a0-button-google-oauth2']"))
					.size() != 0, "Application by picsureui  has launched properly");
			
			*/// span[contains(text(),'with eRA Commons')]
			Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'Login with Google')]"))			.size() != 0, "Application by picsureui  has launched properly");
	
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("screenshotone.png"));

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

			LOGGER.info("---------------------------PicsureUI is launched successfully----------------------------");
			System.out.println("passed");
		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("---------------------------PicsureUI has NOT launched properly----------------------------");
			System.out.println("Failed");
		}

		// driver.navigate().refresh();
	}

	public void verifySessionDehydration(Reporter reporter) throws InterruptedException, Exception {

		BufferedReader br = new BufferedReader(new FileReader("D:\\binaries\\sessionvalue.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null)

		{
			sb.append(line).append("\n");
			line = br.readLine();
		}
		String valuetest = sb.toString();

		System.out.println("whole file as String using BufferedReader and StringBuilder");
		System.out.println(valuetest);

		
		String item = "session";
		String valuefromfile=valuetest.trim();
			
		
		String value = "{\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMT05HX1RFUk1fVE9LRU58Z29vZ2xlLW9hdXRoMnwxMDcxMzQyOTI0MDQ1MDMxMDIwMjkiLCJuYW1lIjoiZ29vZ2xlLW9hdXRoMnwxMDcxMzQyOTI0MDQ1MDMxMDIwMjkiLCJpc3MiOiJlZHUuaGFydmFyZC5obXMuZGJtaS5wc2FtYSIsImV4cCI6MTY4Mzg5Njg4MywiaWF0IjoxNjgxMzA0ODgzLCJlbWFpbCI6IkF0dWxfU2FuYWdhckBobXMuaGFydmFyZC5lZHUiLCJqdGkiOiJ3aGF0ZXZlciJ9.-uvqhbC8_kmRmdDPcnVMXhtDZfChrhXrw_2F4Ca_S7g\"}";
		System.out.println("Testing.."+value);
		
		
		if (valuefromfile.trim().equalsIgnoreCase(value)) {

		 System.out.println("Equal string");
		} 
		else 
		{
			 System.out.println("Not Equal string");
		}

		// AuthTypes authTypes = new AuthTypes();
		// Thread.sleep(5000);
		wait = new WebDriverWait(driver, 30);
		// authTypes.doAuth(driver, testPlan);
		Thread.sleep(7000);
		driver.navigate().refresh();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(String.format("window.sessionStorage.setItem('%s','%s');", item, valuefromfile));
		// System.out.println((String)js.executeScript(String.format("window.sessionStorage.setItem('%s','%s');return
		Thread.sleep(5000);
		//driver.navigate().refresh();
		driver.findElement(By.xpath("//a[@id='query-builder-btn']")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(queryBuilderSearchBox)));
		// driver.navigate().refresh();
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataAccessExploreAuthorizedAccess)));
		Thread.sleep(7000);

		try {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File("screenshothome.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			/*
			 * Assert.assertTrue(driver.findElements(By.xpath(SearchBoxField)).
			 * size() != 0, "Google user is able to Login successfully");
			 */
			Assert.assertTrue(driver.findElements(By.xpath(queryBuilderSearchBox)).size() != 0,
					"Session dehydration is successful");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------Google us"
					+ "er is able to Login successfully in PicsureUI----------------------------");
			System.out.println("passed Login");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("---------------------------Google user Login failed ---------------------------");
			System.out.println(" " + "Login failed");
		}

		// Take a screenshot of the current page
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("screenshot.png"));

		// driver.findElement(By.xpath(dataAccessExploreOpenAccess)).click();

		Thread.sleep(5000);

	}

	public static void searchAndSelectConceptTerm(By searchBoxField, String SearchTerm, String TextToSelect,
			By searchBoxAutocompleteListBox, By searchBox_AutocompleteListBox_Item) throws InterruptedException {

		String searchTerm = (String) testPlan.get(SearchTerm).toString().toLowerCase();
		String textToSelect = (String) testPlan.get(TextToSelect).toString().toLowerCase();
		System.out.println("Value of textToSelect is --------" + textToSelect);
		wait = new WebDriverWait(driver, 40);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(searchBoxField));
			WebElement textBoxElement = driver.findElement(searchBoxField);
			textBoxElement.sendKeys(searchTerm);
			// Thread.sleep(7000);
			String selectAll = Keys.chord(Keys.ENTER, "");
			driver.findElement(searchBoxField).sendKeys(selectAll);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(searchBoxAutocompleteListBox)));
			WebElement autoOptions = driver.findElement(searchBoxAutocompleteListBox);
			List<WebElement> optionsToSelect = autoOptions.findElements(searchBox_AutocompleteListBox_Item);
			System.out.println("Matched Search List contains ....." + optionsToSelect.size());
			Thread.sleep(3000);
			System.out.println("Show the value" + optionsToSelect.get(0).getText());

			for (WebElement option : optionsToSelect) {

				if (option.getText().toLowerCase().contains(textToSelect)) {
					System.out.println("Trying to select: " + textToSelect);
					Actions action = new Actions(driver);
					action.moveToElement(option).perform();
					Thread.sleep(2000);
					action.click(option).perform();
					break;
				}
			}

		} catch (NoSuchElementException e) {
			System.out.println(e.getStackTrace());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	public void verifyQueryBuilderNoValue(Reporter reporter)
			throws InterruptedException, Exception, IllegalAccessException {

		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(4000);
		Select dropdown = new Select(
				driver.findElement(By.xpath("//select[contains(@class,'form-control constrain-type-select')]")));
		dropdown.selectByIndex(0);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(4000);
		String patientCountActual = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query Builder  for   concepts with NO VALUE  is working  fine----------------------------");

		} else {
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);

			LOGGER.info(
					"---------------------------Query Builder  for   concepts with NO VALUE  is having issue----------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyQueryBuilderANDCondition(Reporter reporter) throws InterruptedException, Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		String enterNumberSecond = (String) testPlan.get("NumericValueLessSecond");

		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(4000);

		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		System.out.println("Selecting........................Second Box");
		searchAndSelectConceptTerm(SearchBoxTwo, "SearchTermSecond", "TextToSelectSecond", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumberSecond);
		QueryBuilder.class.newInstance().doRunQuery(driver);

		Thread.sleep(5000);

		String patientCountActual = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query Builder functionality for AND condition multiple search result combination is  working fine----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query Builder  functionlity is  having issue with AND condition----------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyAndLabel(Reporter reporter) throws InterruptedException, Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		String enterNumberSecond = (String) testPlan.get("NumericValueLessSecond");
		String editNumericValue = (String) testPlan.get("EditNumericValue");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(4000);

		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		System.out.println("Selecting........................Second Box");
		searchAndSelectConceptTerm(SearchBoxTwo, "SearchTermSecond", "TextToSelectSecond", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumberSecond);
		QueryBuilder.class.newInstance().doRunQuery(driver);

		Thread.sleep(5000);

		QueryBuilder.class.newInstance().editingQuery(driver);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, editNumericValue);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(5000);

		List<WebElement> l = driver.findElements(By.xpath("//*[contains(text(),'AND')]"));

		if (l.size() == 3) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------AND LABEL is  present after editing----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("---------------------------AND LABEL is hidden after editing----------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyQueryBuilderSearchInCaseSensitivity(Reporter reporter) {

		String searchTerm = (String) testPlan.get("SearchTerm").toString().toLowerCase();
		String searchTermUpper = searchTerm.toUpperCase();

		WebDriverWait wait;
		wait = new WebDriverWait(driver, 7);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(SearchBox));

			WebElement textBoxElement = driver.findElement(SearchBox);

			textBoxElement.sendKeys(searchTerm);
			String selectAll = Keys.chord(Keys.ENTER, "");
			driver.findElement(By.xpath("//input[@class='search-box form-control']")).sendKeys(selectAll);

			WebElement autoOptions = driver.findElement(SearchBoxAutocompleteListBox);
			wait.until(ExpectedConditions.visibilityOf(autoOptions));
			List<WebElement> optionsToSelect = autoOptions.findElements(SearchBoxAutocompleteListBoxItems);

			System.out.println("Number of searched items list" + optionsToSelect.size());

			Thread.sleep(3000);

			int lowercaseSearchResultCount = optionsToSelect.size();

			driver.navigate().refresh();
			wait.until(ExpectedConditions.presenceOfElementLocated(SearchBox));
			WebElement textBoxElementUpper = driver.findElement(SearchBox);
			textBoxElementUpper.sendKeys(searchTermUpper);

			String selectAllTwo = Keys.chord(Keys.ENTER, "");
			driver.findElement(By.xpath("//input[@class='search-box form-control']")).sendKeys(selectAllTwo);

			wait.until(ExpectedConditions.visibilityOf(driver.findElement(SearchBoxAutocompleteListBox)));
			WebElement autoOptionsUpper = driver.findElement(SearchBoxAutocompleteListBox);
			wait.until(ExpectedConditions.visibilityOf(autoOptionsUpper));
			List<WebElement> optionsToSelectUpper = autoOptionsUpper.findElements(SearchBoxAutocompleteListBoxItems);

			Thread.sleep(3000);

			int UppercaseSearchResultCount = optionsToSelect.size();

			if (lowercaseSearchResultCount == UppercaseSearchResultCount) {

				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				LOGGER.info(
						"---------------------------Query Builder  search  is case insensitive----------------------------");
			} else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
				LOGGER.info(
						"---------------------------Query Builder  search  is case sensitive----------------------------");
			}

			driver.navigate().refresh();

		} catch (NoSuchElementException e) {
			System.out.println(e.getStackTrace());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		driver.navigate().refresh();

	}

	public void verifyQueryBuilderByNumericLessThan(Reporter reporter)
			throws InterruptedException, Exception, Throwable {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		String patientCountActual = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric less than is working fine----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric less than has  failed..issue----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyQueryResultMaxvalue(Reporter reporter) throws Exception {

		String enterNumberGreaterLessMax = (String) testPlan.get("NumericValueGreaterLessthanMax");
		driver.findElement(By.xpath(dataAccessAuthorizationAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(3000);
		Select dropdownByNumeric = new Select(driver
				.findElement(By.xpath("//select[contains(@class,'form-control value-type-select value-operator')]")));
		dropdownByNumeric.selectByIndex(2);
		Thread.sleep(10000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumberGreaterLessMax);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(8000);

		/*
		 * WebElement range=driver.findElement(By.xpath(
		 * "//div[@class='constrain-row row value-operator-range-row']/div[1]"
		 * )); String valueRange=range.getText(); //WebElement
		 * Valuerange=driver.findElement(By.xpath("xpathExpression");
		 * 
		 * System.out.println("valuerangeString is "+valueRange); String
		 * MaxValue = valueRange.substring(valueRange.length()-3); int
		 * enterNumberGreater=Integer.parseInt(MaxValue)-1; System.out.println(
		 * "max value is " +enterNumberGreater); String
		 * MaxValueLess=String.valueOf(enterNumberGreater);
		 * QueryBuilder.class.newInstance().enterByNumericValue(driver,
		 * MaxValueLess); Thread.sleep(5000);
		 * QueryBuilder.class.newInstance().doRunQuery(driver);
		 * Thread.sleep(5000);
		 * 
		 * System.out.println("patientCountActual is" + patientCountActual);
		 *
		 */
		String patientCountActual = driver.findElement(patientCountValue).getText();
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric greater  than is working fine----------------------------");

		} else {
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric greater than has failed..issue----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderByNumericValueBetween(Reporter reporter) throws Exception {
		String LowerRange = (String) testPlan.get("NumericValue1");
		String HigherRange = (String) testPlan.get("NumericValue2");

		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);

		Select dropdownByNumeric = new Select(driver
				.findElement(By.xpath("//select[contains(@class,'form-control value-type-select value-operator')]")));
		dropdownByNumeric.selectByIndex(1);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().enterByNumericBetween(driver, LowerRange, HigherRange);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		String patientCountActual = driver.findElement(By.xpath("//*[@id='patient-count']")).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

			LOGGER.info(
					"---------------------------Query result by numeric in between than works fine----------------------------");

		} else {
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric in between has failed...issue----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderSearchInvalidData(Reporter reporter) throws Exception {

		WebDriverWait wait;
		wait = new WebDriverWait(driver, 7);
		String invalidSearchData = (String) testPlan.get("SearchTerm");

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(SearchBox));
			WebElement textBoxElement = driver.findElement(SearchBox);
			textBoxElement.sendKeys(invalidSearchData);
			// QueryBuilder.class.newInstance().enterFromKeyborad();
			String selectAll = Keys.chord(Keys.ENTER, "");
			driver.findElement(SearchBox).sendKeys(selectAll);
			Thread.sleep(5000);
			WebElement noResultFound = driver.findElement(By.xpath("//span[contains(text(),'No Results Found')]"));
			if (noResultFound.isDisplayed()) {
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				LOGGER.info(
						"--------------------------No Result Found as there is NO valid data matching----------------------------");
			} else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
				LOGGER.info(
						"---------------------------Warning message hasn't displayed for invalid search----------------------------");

			}

		} catch (NoSuchElementException e) {
			System.out.println(e.getStackTrace());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderDeletion(Reporter reporter) throws Exception {

		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);
		String enterNumber = (String) testPlan.get("NumericValueLess");
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().deleteButtonQuery(driver);

		try {
			Assert.assertTrue(
					driver.findElements(By.xpath("//span[contains(text(),'Vitamin E, Less than 200')]")).size() == 0,
					"Deletion functionality works fine");
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------Deletion works fine ----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("---------------------------Deletion is NOT happening ----------------------------");

		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderEditing(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		String editNumericValue = (String) testPlan.get("EditNumericValue");
		String patientCountEditedExpected = (String) testPlan.get("PatientCountAfterEditing");
		System.out.println("patientCountExpected" + patientCountEditedExpected);
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(2000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);

		Thread.sleep(3000);

		String patientCountActual = driver.findElement(patientCountValue).getText();
	//	System.out.println("patientCountActual---------------" + patientCountActual);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().editingQuery(driver);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, editNumericValue);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		String patientCountActualAfterEditing = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActualAfterEditing---------------" + patientCountActualAfterEditing);
		if (patientCountActualAfterEditing.equalsIgnoreCase(patientCountEditedExpected)) {
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			System.out.println("Query Builder - Editing functionality is working fine");
			LOGGER.info(
					"---------------------------Query Builder - Editing functionality is working fine----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query Builder - Editing functionality is failed----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderByNumericValidation(Reporter reporter)
			throws InterruptedException, Exception, Throwable {

		String enterNumber = (String) testPlan.get("NumericValueOutofRange");
		String validationTextExpected = "Value invalid! Correct invalid fields.";
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		String validationMessage = driver.findElement(By.xpath("//div[@class='validation-message col-md-12']"))
				.getText();
		System.out.println("ValidatioMessage is " + validationMessage);
		if (validationTextExpected.equals(validationMessage)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			System.out.println("Query Builder - Editing functionality is working fine");
			LOGGER.info(
					"---------------------------Query Builder - Editing functionality is working fine----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query Builder - Editing functionality is failed----------------------------");
		}

		driver.navigate().refresh();

	}

	public void verifyDownloading(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		WebDriverWait wait = new WebDriverWait(driver, 110);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		Thread.sleep(2000);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().doSelectExport(driver);
		Thread.sleep(110000);
		QueryBuilder.class.newInstance().doubleclickData(driver);
		Thread.sleep(10000);
		QueryBuilder.class.newInstance().doubleclickData(driver);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().doubleClickPrepareDownload(driver);
		Thread.sleep(4000);
		QueryBuilder.class.newInstance().downloadButton(driver);
		Thread.sleep(5000);
		File reportDownloadPopup1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(reportDownloadPopup1, new File("reportDownloadPopup1.png"));

		try {

			Assert.assertTrue(QueryBuilder.class.newInstance().isFileDownloaded(downloadPath, "data.csv"),
					"Failed to download Expected Report");
			System.out.println("Report File is downloaded successfully");
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------The report is downloading successfully ----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------The report is NOT downloading , there could be issue with downloading ----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyTheLoadedData(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().doSelectExport(driver);
		Thread.sleep(4000);
		QueryBuilder.class.newInstance().doubleClickPrepareDownload(driver);
		Thread.sleep(4000);
		QueryBuilder.class.newInstance().downloadButton(driver);
		Thread.sleep(4000);
		Assert.assertTrue(QueryBuilder.class.newInstance().isFileDownloaded(downloadPath, "data.csv"),
				"Failed to download Expected document");
		QueryBuilder.class.newInstance();
		// This will load csv file
		File file = new File(downloadPath + "\\" + "data.csv");

		BufferedReader Buff = new BufferedReader(new FileReader(file));
		String downloadedFileColumn = Buff.readLine();
		System.out.println("-----------------" + downloadedFileColumn);
		String[] downloadedFileColumnList = downloadedFileColumn.split(",");
		System.out.println("The second column value is " + downloadedFileColumnList[1]);
		String expectedDatainDownloaded = (String) testPlan.get("expectedDataColumn");
		System.out.println("****************************" + expectedDatainDownloaded);
		try {
			Assert.assertEquals(downloadedFileColumnList[1], expectedDatainDownloaded);
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"--------------------------The downloaded report file columns are matched as expected ----------------------------");
		} catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------The downloaded report file columns are NOT matched : seems  issue ----------------------------");
			// Takescreenshot;
		}

		driver.navigate().refresh();
	}

	public void verifyTheLoadedDataforMultipleConceptsAnd(Reporter reporter) throws Exception {

		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(4000);
		driver.findElement(By
				.xpath("//*[@class='filter-search row']/div[1]/div/div[1]/div/div[1]/div//span[@class='autocomplete-term']"))
				.click();
		driver.findElement(By.xpath(".//*[@id='filter-list']/div/div/div[2]/div/div[3]/span")).click();
		Thread.sleep(3000);
		System.out.println("Selecting........................Second Box");

		searchAndSelectConceptTerm(SearchBoxTwo, "SearchTermSecond", "TextToSelectSecond",
				SearchBoxSecondAutocompleteListBox, SearchBoxSecondAutocompleteListBoxItems);
		Thread.sleep(4000);
		driver.findElement(By
				.xpath("//*[@class='filter-search row']/div[1]/div/div[1]/div/div[1]/div//span[@class='autocomplete-term']"))
				.click();

		driver.findElement(By.xpath(".//span[@class='glyphicon glyphicon-ok btn search-btn constrain-apply-btn']"))
				.click();
		// Comparing the Actual and Expected Patient Count

		Thread.sleep(3000);
		QueryBuilder.class.newInstance().doSelectExport(driver);
		Thread.sleep(2000);
		// QueryBuilder.class.newInstance().clickPrepareDownload(driver);
		QueryBuilder.class.newInstance().doubleClickPrepareDownload(driver);
		Thread.sleep(4000);

		QueryBuilder.class.newInstance().downloadButton(driver);
		Thread.sleep(4000);
		Assert.assertTrue(QueryBuilder.class.newInstance().isFileDownloaded(downloadPath, "data.csv"),
				"Failed to download Expected document");
		QueryBuilder.class.newInstance();
		// Get the list of columns in string array from actual downloaed csv
		// file

		// This will load csv file
		File file = new File(downloadPath + "\\" + "data.csv");
		BufferedReader Buff = new BufferedReader(new FileReader(file));
		String downloadedFileColumn = Buff.readLine();
		int i = 0;
		String[] downloadedFileColumnlist = downloadedFileColumn.split(",");
		for (String e : downloadedFileColumnlist) {
			System.out.println(e);
			i++;

		}

		List<String> ActualColumnsDownloadDataCSV = Arrays.asList(downloadedFileColumnlist);

		List<String> strExpectedDataColList = (List<String>) testPlan.get("myarray");

		try {

			Assert.assertEquals(ActualColumnsDownloadDataCSV, strExpectedDataColList);
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("The columns of data loaded of CSV are  matched with expected");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
		} catch (AssertionError error) {
			LOGGER.error(error);
			LOGGER.info("The columns of data loaded of CSV are  NOT matched with expected");

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);

		}

		driver.navigate().refresh();

	}

	public void verifyQueryBuilderByNumericNoValueMessage(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);

		WebElement NoValue = driver.findElement(EmptyFieldByNumeric);
		String actualTextNoValue = NoValue.getText();
		Thread.sleep(3000);
		try {
			Assert.assertEquals(actualTextNoValue, "Value invalid! Correct invalid fields.");
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

			LOGGER.info("It shows the message for NO value correctly");
			// System.out.println("correct");
		} catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("There is No proper message for novalid data in the textfield");
			// Takescreenshot;
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderByNumericdecimalvalues(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLessDecimal");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		String patientCountActual = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric less than is working fine----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric less than has  failed..issue----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderByNumericInBtnValidationForTextBoxMessage(Reporter reporter) throws Exception {
		String LowerRange = (String) testPlan.get("NumericValue1");
		String HigherRange = (String) testPlan.get("NumericValue2");
		String validationTextExpected = "Value invalid! Correct invalid fields.";
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);

		Select dropdownByNumeric = new Select(driver
				.findElement(By.xpath("//select[contains(@class,'form-control value-type-select value-operator')]")));
		dropdownByNumeric.selectByIndex(1);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().enterByNumericBetween(driver, LowerRange, HigherRange);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		String validationMessage = driver.findElement(By.xpath("//div[@class='validation-message col-md-12']"))
				.getText();
		System.out.println("ValidatioMessage is " + validationMessage);
		if (validationTextExpected.equals(validationMessage)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

			LOGGER.info(
					"---------------------------By Numeric in between validation message displays----------------------------");

		} else {

			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------By Numeric in between validation message doesn't display----------------------------");
		}
		driver.navigate().refresh();
	}

	public void verifyQueryBuilderBack(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		String expectedInputTextBoxValue = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().backButton(driver);
		Thread.sleep(3000);

		try {
			Assert.assertTrue(driver
					.findElements(By
							.xpath("//div[@class='tab-pane active']//div[@class='search-result-list']/div/div//span[@class='autocomplete-term']"))
					.size() != 0, "Clicking BackButton navigates to searched  result Lists ");
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"------------------------Clicking BackButton navigates to searched  result Lists -------------------------------");
		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Clicking back button is not happening as expected ---------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyQueryBuilderSelectDataForExport(Reporter reporter) throws Exception {
		String validationTextExpected = "Value invalid! Correct invalid fields.";
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);
		driver.findElement(By.xpath("//option[contains(text(),'black')]")).click();
	}

	public void verifyUserProfile(Reporter reporter) throws Exception {

		driver.findElement(By.xpath(userProfile)).click();

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);

		try {
			Assert.assertTrue(driver.findElements(By.xpath("//div[@id='user_token_expiration']")).size() != 0,
					"User Profile page loads properly");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------User Profile page loads properly ----------------------------");
			System.out.println("user profile page");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------User Profile page doesn't  load properly----------------------------");

		}
		Thread.sleep(7000);

		driver.findElement(By.xpath(closingButton)).click();

		// .findElement(By.xpath(aplicationButton)).click();

		// driver.findElement(By.linkText("PICSURE")).click();

	}

	public void verifyBDCAutoInclusionColumnReport(Reporter reporter) throws Exception {

		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		QueryBuilder.class.newInstance().doSelectExport(driver);
		Thread.sleep(60000);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		QueryBuilder.class.newInstance().doubleclickData(driver);
		Thread.sleep(10000);
		QueryBuilder.class.newInstance().doubleclickData(driver);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().doubleClickPrepareDownload(driver);
		Thread.sleep(4000);
		QueryBuilder.class.newInstance().downloadButton(driver);

		Assert.assertTrue(QueryBuilder.class.newInstance().isFileDownloaded(downloadPath, "data.csv"),
				"Failed to download Expected document");
		QueryBuilder.class.newInstance();
		// This will load csv file
		File file = new File(downloadPath + "\\" + "data.csv");
		// CSVReader reader = new CSVReader(new FileReader(file));

		BufferedReader Buff = new BufferedReader(new FileReader(file));
		String downloadedFileColumn = Buff.readLine();
		System.out.println("-----------------" + downloadedFileColumn);
		String[] downloadedFileColumnList = downloadedFileColumn.split(",");
		System.out.println("The second column value is " + downloadedFileColumnList[1]);
		String expectedDatainDownloaded = (String) testPlan.get("expectedDataColumn");
		System.out.println("****************************" + expectedDatainDownloaded);
		try {
			Assert.assertEquals(downloadedFileColumnList[1], expectedDatainDownloaded);
			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"--------------------------The downloaded report file columns are matched as expected ----------------------------");
		} catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------The downloaded report file columns are NOT matched : seems  issue ----------------------------");
			// Takescreenshot;
		}

		driver.navigate().refresh();
	}

	public void verifyDataaccessDashboard(Reporter reporter) throws Exception {

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
			Assert.assertTrue(driver.findElements(By.xpath("//button[contains(text(),'Explore Now')]")).size() != 0,
					"Data Access page loads properly");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------Data Access page loads properly ----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Data Access page doesn't  load properly----------------------------");

		}
	}

	public void verifyDataaccessExploreOpenAccess(Reporter reporter) throws Exception, IllegalAccessException {

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreAuthorizedAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
			Assert.assertTrue(driver.findElements(By.xpath(SearchBoxField)).size() != 0,
					"Open Data Access page loads properly");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Clicking on ExploreNow loads Query Builder properly ----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Clicking Explore Now doesn't load QueryBuilder ----------------------------");

		}

	}

	// driver.findElement(By.xpath(closingButton)).click();

	public void verifyHelpContactusPageload(Reporter reporter) throws Exception {

		driver.findElement(By.xpath(helpTab)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(contactus)).click();

		String parent = driver.getWindowHandle();

		Set<String> s = driver.getWindowHandles();

		// Now iterate using Iterator
		Iterator<String> I1 = s.iterator();
		while (I1.hasNext()) {
			String child_window = I1.next();
			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
				System.out.println(driver.switchTo().window(child_window).getTitle());
			}

		}

		try {
			Assert.assertTrue(driver.findElements(By.xpath("//h1[contains(text(),'Contact')]")).size() != 0,
					"Contact us window loads properly");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------Contact us page loads properly ----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("---------------------------ContactUs page doesn't  load properly----------------------------");

		}
		driver.switchTo().window(parent);
	}

	public void verifyLogoutPicsure(Reporter reporter) throws Exception {

		driver.findElement(By.xpath(logoutButton)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
			Assert.assertTrue(driver
					.findElements(By.xpath("//span[contains(text(),'Login with Google')]"))
					.size() != 0, "Application by picsureui  logged out successfully");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------User is logged out successfully----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info("---------------------------PICSURE UI Logout is having problem----------------------------");

		}

	}

	public void verifyAuthorizedAccessPageload(Reporter reporter) throws Exception {
		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement AuthorizedAccessCount = driver.findElement(By.xpath("//div[@id='authorized-participants']"));
		String AuthorizedAccessCountText = AuthorizedAccessCount.getText();
		System.out.println("Text is " + AuthorizedAccessCountText);
		String numberOnly = AuthorizedAccessCountText.replaceAll("[^0-9]", "");
		System.out.println("Text is " + numberOnly);

		driver.findElement(By.xpath(dataAccessExploreAuthorizedAccess)).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(000);

		try {
			Assert.assertTrue(driver.findElements(By.xpath("//a[@id='select-btn']")).size() != 0,
					"Authorized Access page is loaded properly");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------Authorized page has loaded properly---------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------There is issue with Authorized page loading ---------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyQueryScopeStudyOpenAccess(Reporter reporter) throws Exception {

		String ExpStudycountAuthorized = (String) testPlan.get("AuthStudycount");
		String ExpStudycountOpenAccess = (String) testPlan.get("OpenAccessStudycount");
		String TextToSearch = (String) testPlan.get("SearchTerm");

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreAuthorizedAccess)).click();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.presenceOfElementLocated(SearchBox));
		WebElement textBoxElement = driver.findElement(SearchBox);
		textBoxElement.sendKeys(TextToSearch);
		String selectAll = Keys.chord(Keys.ENTER, "");
		driver.findElement(SearchBox).sendKeys(selectAll);
		Thread.sleep(4000);
		List<WebElement> ActualAuthorizedStudy = driver.findElements(StudiesTab);
		int ActualAuthorizedStudyCount = ActualAuthorizedStudy.size();
		System.out.println("Actual...." + ActualAuthorizedStudyCount);

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreOpenAccess)).click();
		Thread.sleep(5000);
		;

		WebElement textBoxElementOpen = driver.findElement(SearchBox);
		textBoxElementOpen.sendKeys(TextToSearch);
		String selectAllOpen = Keys.chord(Keys.ENTER, "");
		driver.findElement(SearchBox).sendKeys(selectAllOpen);
		Thread.sleep(4000);
		List<WebElement> ActualOpenAccessStudy = driver.findElements(StudiesTab);
		int ActualOpenAccessStudyCount = ActualOpenAccessStudy.size();
		System.out.println("Actual...." + ActualOpenAccessStudyCount);
		int ExpStudycountOpenAccessConv = Integer.parseInt(ExpStudycountOpenAccess);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
			Assert.assertTrue(ActualOpenAccessStudyCount == ExpStudycountOpenAccessConv,
					"Open Access Query scope looks good");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info("---------------------------Open Access Query scope looks good----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Open Access Query scope is having issue----------------------------");

		}

	}

	public void verifyAuthorizedAccessPageDataExport(Reporter reporter) throws Exception {
		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreAuthorizedAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String enterNumber = (String) testPlan.get("NumericValueLess");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);
		Thread.sleep(5000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumber);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);

		try {
			Assert.assertTrue(driver.findElements(By.xpath("//a[@id='select-btn']")).size() != 0,
					"Authorized Access page is loaded properly");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Authorized page load shows Data Export Button ---------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------There is issue with Authorized page load ---------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyQueryBuilderByNumericGreaterThan(Reporter reporter) throws Exception {

		String enterNumberGreater = (String) testPlan.get("NumericValueGreater");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(3000);
		Select dropdownByNumeric = new Select(driver
				.findElement(By.xpath("//select[contains(@class,'form-control value-type-select value-operator')]")));
		dropdownByNumeric.selectByIndex(2);
		Thread.sleep(10000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumberGreater);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		String patientCountActual = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric greater  than is working fine----------------------------");

		} else {
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric greater than has failed..issue----------------------------");
		}

		driver.navigate().refresh();
	}

	public void verifyAuthorizedAccessdefaultNoExportButton(Reporter reporter) throws Exception {
		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreAuthorizedAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);

		try {
			// Assert.assertTrue(driver.findElements(By.xpath("//a[@id='select-btn']")).size()
			// == 0,
			// "Authorized Access page is loaded properly");

			Assert.assertFalse(driver.findElement(By.xpath("//a[@id='select-btn']")).isDisplayed());

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Authorized page load doesn't diplay Select Data  Export Button on loading ---------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------There is issue with Authorized page - Data Export Button ---------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyOpenAccesspatientcountdiplsyaforQueryResultBetnOnetoNine(Reporter reporter)
			throws Exception, IllegalAccessException {

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreOpenAccess)).click();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String enterNumberGreater = (String) testPlan.get("NumericValueGreater");
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(3000);
		Select dropdownByNumeric = new Select(driver
				.findElement(By.xpath("//select[contains(@class,'form-control value-type-select value-operator')]")));
		dropdownByNumeric.selectByIndex(2);
		Thread.sleep(10000);
		QueryBuilder.class.newInstance().enterByNumericValue(driver, enterNumberGreater);
		QueryBuilder.class.newInstance().doRunQuery(driver);
		Thread.sleep(3000);
		String patientCountActual = driver.findElement(patientCountValue).getText();
		System.out.println("patientCountActual is" + patientCountActual);
		String patientCountExpected = (String) testPlan.get("PatientCount");
		System.out.println("patientCountExpected" + patientCountExpected);

		if (patientCountActual.equalsIgnoreCase(patientCountExpected)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric greater  than is working fine----------------------------");

		} else {
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------Query result by numeric greater than has failed..issue----------------------------");
		}

		driver.navigate().refresh();

	}

	public void verifyGrantedFunctionalityOnOpenAccess(Reporter reporter) throws Exception, IllegalAccessException {

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreOpenAccess)).click();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(6000);
		driver.findElement(By.xpath("//div[@data-study='phs002299']")).click();

		try {
			Assert.assertTrue(driver
					.findElements(By
							.xpath("//a[@class='col btn btn-default header-btn authorized-access-visible header-navigation active']"))
					.size() != 0, "clicking on granted button opens AuthorizatonAccess");
			// span[contains(text(),'with eRA Commons')]

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------clicking on granted button on openaccess opens AuthorizatonAccess----------------------------");

		}

		catch (AssertionError error) {
			LOGGER.error(error);
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------clicking on granted button on openaccess does not open AuthorizatonAccess----------------------------");

		}

		driver.navigate().refresh();

	}

	public void verifyHoverOverSubjectTooltip(Reporter reporter) throws Exception {

		String TextToSearch = (String) testPlan.get("SearchTerm");
		String ExpectedTooltip = (String) testPlan.get("ExpectedTooltip");

		driver.findElement(By.xpath(dataAccess)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(dataAccessExploreAuthorizedAccess)).click();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.presenceOfElementLocated(SearchBox));
		WebElement textBoxElement = driver.findElement(SearchBox);
		textBoxElement.sendKeys(TextToSearch);
		String selectAll = Keys.chord(Keys.ENTER, "");
		driver.findElement(SearchBox).sendKeys(selectAll);
		Thread.sleep(4000);
		List<WebElement> ActualAuthorizedStudy = driver.findElements(StudiesTab);
		WebElement ele = ActualAuthorizedStudy.get(1);
		String ActualToolTip = ele.getAttribute("title");

		if (ActualToolTip.equalsIgnoreCase(ExpectedTooltip)) {

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			LOGGER.info(
					"---------------------------The tool tip for subject is displaying in full length----------------------------");

		} else {
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);

			LOGGER.info(
					"---------------------------The tool tip for subject is displaying is having issue----------------------------");

		}

		driver.navigate().refresh();

		// System.out.println("Value of second study tab is"
		// +ActualAuthorizedStudy.get(0);

		// System.out.println("Value of study tab is" +FirstElement);
	}

	public void verifyQueryBuilderRestrictByValue(Reporter reporter) throws Exception {
		String validationTextExpected = "Value invalid! Correct invalid fields.";
		searchAndSelectConceptTerm(SearchBox, "SearchTerm", "TextToSelect", SearchBoxAutocompleteListBox,
				SearchBoxAutocompleteListBoxItems);

		Thread.sleep(4000);
		driver.findElement(By.xpath("//option[contains(text(),'black')]")).click();

		/*
		 * Select dropdownByNumeric = new Select(driver .findElement(By.xpath(
		 * "//select[contains(@class,'form-control value-type-select value-operator')]"
		 * ))); dropdownByNumeric.selectByIndex(1); Thread.sleep(3000);
		 * 
		 * QueryBuilder.class.newInstance().doRunQuery(driver); String
		 * validationMessage = driver.findElement(By.xpath(
		 * "//div[@class='validation-message col-md-12']")) .getText();
		 * 
		 * if (validationTextExpected.equals(validationMessage)) {
		 * 
		 * 
		 * SummaryStatisticsResults.class.newInstance().doAssertResultTrue(
		 * driver, testPlan, reporter);
		 * 
		 * LOGGER.info(
		 * "---------------------------By Numeric in between validation message displays----------------------------"
		 * );
		 * 
		 * } else {
		 * 
		 * SummaryStatisticsResults.class.newInstance().doAssertResultFalse(
		 * driver, testPlan, reporter); LOGGER.info(
		 * "---------------------------By Numeric in between validation message doesn't display----------------------------"
		 * ); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * // Thread.sleep(4000); /*
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By .xpath(
		 * "//*[@class='filter-search row']/div[1]/div/div[1]/div/div[1]/div//span[@class='autocomplete-term']"
		 * ))) .click();
		 * 
		 * WebElement listButton = driver .findElement(By.xpath(
		 * "//button[@class='btn btn-default dropdown-toggle value-constraint-btn']"
		 * )); listButton.click(); Thread.sleep(3000);
		 * 
		 * List<WebElement> listOptions = listButton .findElements(By.xpath(
		 * "//ul[@class='constrain-dropdown-menu dropdown-menu']/li"));
		 * 
		 * listOptions.get(1).click(); // Thread.sleep(3000);
		 * 
		 * WebElement NumericOption =
		 * wait.until(ExpectedConditions.presenceOfElementLocated( By.xpath(
		 * ".//*[@id='filter-list']/div/div/div[2]/div/div[2]/div[1]/div/button"
		 * )));
		 * 
		 * NumericOption.click();
		 * 
		 * // Thread.sleep(3000); wait.until(ExpectedConditions
		 * .presenceOfElementLocated(By.xpath(
		 * "//ul[@class='value-dropdown-menu dropdown-menu']/li")));
		 * 
		 * List<WebElement> listNumericOptions = NumericOption
		 * .findElements(By.xpath(
		 * "//ul[@class='value-dropdown-menu dropdown-menu']/li"));
		 * 
		 * // Select the Between option listNumericOptions.get(1).click();
		 * 
		 * // Thread.sleep(2000);
		 * 
		 * QueryBuilder.class.newInstance().enterByNumericBetween(driver,
		 * LowerRange, HigherRange); driver.findElement(By.xpath(
		 * ".//*[@id='filter-list']/div/div/div[2]/div/div[3]/span")).click();
		 */
		// Thread.sleep(3000);

		/*
		 * There should be validation message if the value of first text box is
		 * greater than second one
		 * 
		 * will implement it once the issue gets fixed
		 * 
		 * To-Do
		 * 
		 * 
		 */

		// driver.navigate().refresh();
	}

	public void closeDriver() {
		driver.quit();
	}

}
