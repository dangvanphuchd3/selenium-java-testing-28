package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Popup_Part_II_Not_In_DOM {
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
		
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(new FirefoxProfile());
		options.addPreference("dom.webnotifications.enabled", false);
		driver = new FirefoxDriver(options);
		
		driver.manage().window().maximize();
		
		//implicitlyWait ảnh hưởng đến tìm Elements
		// findElement/findElements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Fixed_Not_In_DOM_Tiki() {
		driver.get("https://tiki.vn/");
		
		// By: chưa đi tìm Element
		By loginPopup = By.cssSelector("div.ReactModal__Content");
		
		// Verify nó chưa hiển thị khi chưa click vào button login
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);
		
		// Click bật login popup lên
		driver.findElement(By.cssSelector("div[data-view-id *='header_account']")).click();
		sleepInSecond(3);
		
		// Verify popup login hiển thị
		Assert.assertEquals(driver.findElements(loginPopup).size(), 1);
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

		//Nhập dữ liệu
		driver.findElement(By.cssSelector("input[name='tel']")).sendKeys("0123456789");
		
		// Click button Tiep Tuc
		driver.findElement(By.xpath("//button[text()='Tiếp Tục']")).click();
		sleepInSecond(2);
		
		// Verify message
		Assert.assertEquals(driver.findElement(By.cssSelector("span.error-mess")).getText(), "Số điện thoại không đúng định dạng");
		
		// Close popup
		driver.findElement(By.cssSelector("img.close-img")).click();
		sleepInSecond(2);
		
		// Verify popup không hiển thị
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);
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
