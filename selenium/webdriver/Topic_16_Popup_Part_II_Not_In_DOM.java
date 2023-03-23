package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
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

	//@Test
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
	public void TC_02_Fixed_Not_In_DOM_Facebook() {
		driver.get("https://www.facebook.com/");
		
		By createAccountPopup = By.xpath("//div[text()='Sign Up']/parent::div/parent::div");
		
		//Verify Create Account Popup is not display
		Assert.assertEquals(driver.findElements(createAccountPopup).size(), 0);
		
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		sleepInSecond(2);
		
		// Verify Create Account is displayed
		Assert.assertEquals(driver.findElements(createAccountPopup).size(), 1);
		
		driver.findElement(By.name("firstname")).sendKeys("Automation");
		driver.findElement(By.name("lastname")).sendKeys("FC");
		driver.findElement(By.name("reg_email__")).sendKeys("12345678");
		driver.findElement(By.name("reg_passwd__")).sendKeys("12345678");
		new Select(driver.findElement(By.id("day"))).selectByVisibleText("21");
		new Select(driver.findElement(By.id("month"))).selectByVisibleText("Nov");
		new Select(driver.findElement(By.id("year"))).selectByVisibleText("1998");
		
		driver.findElement(By.xpath("//label[text()='Male']/following-sibling::input")).click();
		
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElements(createAccountPopup).size(), 0);
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
