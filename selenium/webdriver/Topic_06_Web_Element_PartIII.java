package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_PartIII {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailAddress;
	String firstName;
	String lastName;
	String fullName;
	String password;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		rand = new Random();
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		emailAddress = "automation" + rand.nextInt(9999) + "@gmail.com";
		firstName = "Automation";
		lastName = "FC" + rand.nextInt(9999);
		fullName = firstName + " " + lastName;
		password = "12345678";
		
		
	}

	@Test
	public void TC_01_Empty_Email_Password() {
		driver.get("http://live.techpanda.org/");
		
		driver.findElement(By.cssSelector("div .footer a[title='My Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
		
	}

	@Test
	public void TC_02_Invalid_Email() {
		driver.get("http://live.techpanda.org/");
		
		driver.findElement(By.cssSelector("div .footer a[title='My Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("pass")).sendKeys("123456");
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Password_Minimum() {
		driver.get("http://live.techpanda.org/");
		
		driver.findElement(By.cssSelector("div .footer a[title='My Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.id("email")).sendKeys(emailAddress);
		driver.findElement(By.id("pass")).sendKeys("123");
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	@Test
	public void TC_04_Incorrect_Email_Password() {
		driver.get("http://live.techpanda.org/");
		
		driver.findElement(By.cssSelector("div .footer a[title='My Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.id("email")).sendKeys(emailAddress);
		driver.findElement(By.id("pass")).sendKeys("1231231234");
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
	}
	
	@Test
	public void TC_05_Create_New_Account() {
		driver.get("http://live.techpanda.org/");
		
		driver.findElement(By.cssSelector("div .footer a[title='My Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		
		driver.findElement(By.cssSelector("button[title='Register']")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");
		
		String contactInformationText = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		System.out.println(contactInformationText);
		
		Assert.assertTrue(contactInformationText.contains(fullName));
		Assert.assertTrue(contactInformationText.contains(emailAddress));
		
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
		sleepInSecond(5);
		
		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src, 'logo.png')]")).isDisplayed());
	}
	
	@Test
	public void TC_06_Login_Valid_Infor() {
		driver.findElement(By.cssSelector("div .footer a[title='My Account']")).click();
		sleepInSecond(2);
		
		driver.findElement(By.id("email")).sendKeys(emailAddress);
		driver.findElement(By.id("pass")).sendKeys(password);
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		sleepInSecond(2);
		
		String contactInformationText = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		System.out.println(contactInformationText);
		
		Assert.assertTrue(contactInformationText.contains(fullName));
		Assert.assertTrue(contactInformationText.contains(emailAddress));
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
			// 1000 ms = 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
