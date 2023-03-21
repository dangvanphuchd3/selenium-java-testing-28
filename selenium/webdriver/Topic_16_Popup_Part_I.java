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

public class Topic_16_Popup_Part_I {
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
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Fixed_In_DOM() {
		driver.get("https://ngoaingu24h.vn/");
		
		By loginPopup = By.cssSelector("div#modal-login-v1 div.modal-content");
		
		// Verify popup is undisplay
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
		
		// Click button dang nhap
		driver.findElement(By.cssSelector("button.login_")).click();
		
		// Verify popup is display
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		// Nhap thong tin user/password
		driver.findElement(By.xpath("//div[@class='modal fade in']//input[@id='account-input']")).sendKeys("automationfc");
		driver.findElement(By.xpath("//div[@class='modal fade in']//input[@id='password-input']")).sendKeys("automationfc");
		
		// Click button Dang nhap
		driver.findElement(By.xpath("//div[@class='modal fade in']//button[@data-text='Đăng nhập']")).click();
		sleepInSecond(3);
		
		// Verify message hien thi
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='modal fade in']//div[@class='row error-login-panel']")).getText(), "Tài khoản không tồn tại!");
		
	}

	@Test
	public void TC_02_Fixed_In_DOM_KyNa() {
		driver.get("https://skills.kynaenglish.vn/");
		
		By loginPopup = By.cssSelector("div#k-popup-account-login");
		
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
		
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
		
		driver.findElement(By.cssSelector("button#btn-submit-login")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");
		
		driver.findElement(By.cssSelector("button.k-popup-account-close")).click();
		sleepInSecond(2);
		
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
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
