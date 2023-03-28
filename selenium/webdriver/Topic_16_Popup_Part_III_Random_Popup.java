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

public class Topic_16_Popup_Part_III_Random_Popup {
	WebDriver driver;
	String emailAddress;
	Random rand;
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
		
		emailAddress = "automationFC" + "@gmail.com";
		
	}

	@Test
	public void TC_01_Popup_Random_In_DOM_1() {
		driver.get("https://www.javacodegeeks.com/");
		sleepInSecond(30);
		
		By lePopup = By.cssSelector("div.lepopup-popup-container>div:not([style^='display:none'])");
		
		// Vì nó luôn nằm trong DOM nên có thể dùng hàm isDisplayed() để kiểm tra được
		if (driver.findElement(lePopup).isDisplayed()) {
			// Nhập Email vào
			driver.findElement(By.cssSelector("div.lepopup-input>input")).sendKeys(emailAddress);
			sleepInSecond(3);
			driver.findElement(By.cssSelector("a[data-label='Get the Books'],[data-label='OK']>span")).click();
			sleepInSecond(3);
			
			// Verify
			Assert.assertEquals(driver.findElement(By.cssSelector("div.lepopup-element-html-content>h4")).getText(), "Thank you!");
			
			Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content>p")).getText().contains("Your sign-up request was successful."));
			sleepInSecond(10);
		}
		
		String articleName = "Agile Testing Explained";
		
		driver.findElement(By.cssSelector("input#search-input")).sendKeys(articleName);
		driver.findElement(By.cssSelector("button#search-submit")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Agile Testing Explained']")).getText(), articleName);	
	}

	@Test
	public void TC_02_() {
		
	}

	@Test
	public void TC_03_() {
		
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
