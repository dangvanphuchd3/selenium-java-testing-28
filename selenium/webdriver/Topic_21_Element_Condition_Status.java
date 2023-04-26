package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_21_Element_Condition_Status {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 10);
		
	}

	//@Test
	public void TC_01_Visible_Displayed_Visibility() {
		driver.get("https://www.facebook.com/");
		
		// 1. Có trên UI (bắt buộc)
		// 1. Có trong DOM (Bắt buộc)
		
		// Wait cho email address textbox hiển thị
		// Chờ cho email textbox hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		
		driver.findElement(By.id("email")).sendKeys("automationfc98@gmail.com");
	}

	//@Test
	public void TC_02_Invisible_Undisplayed_Invisibility_I() {
		// 2. Không có trên UI (bắt buộc)
		// 2. Có trong DOM
		driver.get("https://www.facebook.com/");
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		// Chờ cho Re-enter Email textbox không hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));
	}
	
	//@Test
	public void TC_02_Invisible_Undisplayed_Invisibility_II() {
		// 2. Không có trên UI (bắt buộc)
		// 2. Không có trong DOM
		driver.get("https://www.facebook.com/");
		
		// Chờ cho Re-enter Email textbox không hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));
	}

	//@Test
	public void TC_03_Presence_I() {
		driver.get("https://www.facebook.com/");
		
		// 1. Có ở trên UI 
		// 1. Có ở trong DOM (Bắt buộc)
		
		// Chờ cho email address textbox presence trong HTML trong vòng 10s
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
	}
	
	//@Test
	public void TC_03_Presence_II() {
		driver.get("https://www.facebook.com/");
		
		// 1. Không có ở trên UI 
		// 1. Chỉ có ở trong DOM (Bắt buộc)
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		// Chờ cho email address textbox không hiển thị trong HTML trong vòng 10s
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.name("reg_email_confirmation__")));
	}
	
	@Test
	public void TC_04_Staleness() {
		driver.get("https://www.facebook.com/");
		
		// 1. Không có ở trên UI (Bắt buộc)
		// 1. Có ở trong DOM
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		//WebElement reEnterEmailAddressTextbox = driver.findElement(By.name("reg_email_confirmation__"));
		
		// Thao tác với Element khác làm cho element re-enter không còn trong DOM
		//...
		
		// Close popup đi
		driver.findElement(By.cssSelector("img._8idr")).click();
		
		// Chờ cho email address textbox không hiển thị trong HTML trong vòng 10s
		// explicitWait.until(ExpectedConditions.stalenessOf(reEnterEmailAddressTextbox));
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
		//driver.quit();
	}
}
