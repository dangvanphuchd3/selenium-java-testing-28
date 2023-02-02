package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_PartII {
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
		
	}

	@Test
	public void TC_01_Url() {
		driver.get("http://live.techpanda.org/");
		
		// Page Login
		// Xpath
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		/* Css
		driver.findElement(By.cssSelector("div[class='footer'] a[title='My Account']")).click();
		*/
		// Thời gian Load trang
		sleepInSecond(2);
		
		// Get current url và verify
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		
		// Click vào button Create an Account
		// Xpath
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		/* Css
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		*/
		// Thời gian Load trang
		sleepInSecond(2);
		
		// Get current url và verify
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
			
	}

	@Test
	public void TC_02_Title() {
		// Step 01: Truy cập vào trang http://live.techpanda.org/
		driver.get("http://live.techpanda.org/");
		
		// Step 02: Click MY ACCOUNT link tại footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		
		// Step 03: Verify title cỉa Login Page = Customer Login
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		
		// Step 04: Click CREATE AN ACCOUNT button
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		
		// Step 05: Verify title của Register Page = Create New Customer Account
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
		
	}

	@Test
	public void TC_03_Navigate() {
		// Step 01: Truy cập vào trang http://live.techpanda.org/
		driver.get("http://live.techpanda.org/");
		
		// Step 02: Click MY ACCOUNT link tại footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		
		// Step 03: Click CREATE AN ACCOUNT button
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		
		// Step 04: Verify url của Register Page = http://live.techpanda.org/index.php/customer/account/create/
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
		
		// Step 05: Back lại trang Login Page
		driver.navigate().back();
		sleepInSecond(2);
		
		// Step 06: Verify url của Login Page = http://live.techpanda.org/index.php/customer/account/login/
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		
		// Step 07: Forward tới trang Register Page
		driver.navigate().forward();
		sleepInSecond(2);
		
		// Step 08: Verify title cỉa Register Page = Create New Customer Account
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	
	@Test
	public void TC_04_Page_Source_HTML() {
		// Step 01: Truy cập vào trang http://live.techpanda.org/
		driver.get("http://live.techpanda.org/");
		
		// Step 02: Click MY ACCOUNT link tại footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		
		// Step 03: Verify Login Page chứa text: Login or Create an Account
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));
		
		// Step 04: Click CREATE AN ACCOUNT button
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		
		// Step 05: Verify Register Page chứa text Create an Account
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
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
