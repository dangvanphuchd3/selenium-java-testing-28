package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_21_StaticWait {
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
	public void TC_01_Not_Enough_Time() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		// Click button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// Loading icon mất 5s mới biến mất
		// Thiếu thời gian để cho 1 element tiếp theo hoạt động được
		sleepInSecond(3);
		
		// Verify text
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_02_Enough_Time() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// Click button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// Loading icon mất 5s mới biến mất
		// Đủ thời gian để cho 1 element tiếp theo hoạt động được
		sleepInSecond(5);
		
		// Verify text
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_03_More_Time() {	
		driver.get("https://automationfc.github.io/dynamic-loading/");

		// Click button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// Loading icon mất 5s mới biến mất
		// Thừa thời gian để cho 1 element tiếp theo hoạt động được
		sleepInSecond(10);
		
		// Verify text
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
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
