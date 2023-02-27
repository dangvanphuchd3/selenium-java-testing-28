package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_Locator {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Open Register Page
		driver.get("https://demo.nopcommerce.com/register");
		
	}
	//HTML First Name textbox
	//<input type="text" data-val="true" data-val-required="First name is required." 
	//id="FirstName" name="FirstName">
	
	// Element name (Tagname HTML): input
	// Attribute name: type data
	//Attribute value: First name is required.

	@Test
	public void TC_01_ID() {
		//Thao tác lên element thì đầu tiên phải tìm được element đó: findElement
		//Find theo cái gì: id/ class/ name/ css/ xpath/...
		//Find tìm thấy element rồi thì action lên element đó: click/ sendkey/...
		driver.findElement(By.id("FirstName")).sendKeys("Automation");
	}

	@Test
	public void TC_02_Class() {
		//Open Search Page
		driver.get("https://demo.nopcommerce.com/search");
		
		//Enter text into Search Textbox
		driver.findElement(By.className("search-text")).sendKeys("Windows");
		
	}

	@Test
	public void TC_03_Name() {
		//Click on Advanced Search checkbox
		driver.findElement(By.name("advs")).click();
	}
	
	@Test
	public void TC_04_TagName() {
		//How many input element on the screen
		System.out.println(driver.findElements(By.tagName("input")).size());
	}
	
	@Test
	public void TC_05_LinkText() {
		//Click on Address link (Absolute) - Tuyệt đối 
		driver.findElement(By.linkText("Addresses")).click();
	}
	
	@Test
	public void TC_06_PartialLinkText() {
		//Click on Address link (Relative) - Tuyệt đối
		driver.findElement(By.partialLinkText("vendor account")).click();
	}
	
	@Test
	public void TC_07_Css() {
		//Open Register page again
		driver.get("https://demo.nopcommerce.com/register");
		
		//1
		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Selenium");
		
		//2
		driver.findElement(By.cssSelector("input[id='LastName']")).sendKeys("Locator");
		
		//3
		driver.findElement(By.cssSelector("input[name='Email']")).sendKeys("selenium@gmail.com");
		
	}
	
	@Test
	public void TC_08_XPath() {
		//Open Register page again
		driver.get("https://demo.nopcommerce.com/register");
				
		//1
		driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Selenium");
				
		//2
		driver.findElement(By.xpath("//input[@id='LastName']")).sendKeys("Locator");
				
		//3
		driver.findElement(By.xpath("//label[text()='Email:']/following-sibling::input")).sendKeys("selenium@gmail.com");
	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
