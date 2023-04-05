package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Frame_Iframe {
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
	public void TC_01_Iframe() {
		// Step 01: Truy cập trang Kyna
		driver.get("https://skills.kynaenglish.vn/");
		
		// Step 02: Verify Facebook iframe hiển thị
		// Verify Facebook iframe hien thi
		// Facebook iframe vẫn là 1 element của trang Kyna
		Assert.assertTrue(driver.findElement(By.xpath("//iframe[contains(@src,'kyna.vn')]")).isDisplayed());
		
		// Cần phải switch vào đúng cái thẻ chứa các element đó
		// Có thể switch bằng 3 cách
		/*
		 * Cách 1: Sử dụng index
		 * driver.switchTo().frame(0);
		 */
		
		/*
		 * Cách 2: Sử dụng name, nhưng iframe không có name or id nên không thao tác được
		 */
		
		// Cách 3: Sử dung element
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'kyna.vn')]")));
		
		String facebookLikes = driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText();
		System.out.println(facebookLikes);
		
		// Step 03: Verify số lượng like của Facebook Page
		// Verify số lượng like của facebook page 
		Assert.assertEquals(facebookLikes, "165K likes");
		
		// Switch về main page
		driver.switchTo().defaultContent();
		
		// Switch to iframe chat
		driver.switchTo().frame("cs_chat_iframe");
		
		// Step 04: Click vào chat iframe
		driver.findElement(By.cssSelector("div.button_bar")).click();
		
		// Step 05: Nhập dữ liệu vào các field
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("John Wick");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("098737194");
		new Select(driver.findElement(By.id("serviceSelect"))).selectByVisibleText("HỖ TRỢ KỸ THUẬT");
		driver.findElement(By.name("message")).sendKeys("Tư vấn khóa học Excel");
		sleepInSecond(3);
		
		// Switch to main page
		driver.switchTo().defaultContent();
		
		// Step 06: Sendkey với từ khóa "Excel" và click icon search
		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.cssSelector("button.search-button")).click();
		
		List<WebElement> courseName = driver.findElements(By.cssSelector("div.content>h4"));
		
		for (WebElement course : courseName) {
			Assert.assertTrue(course.getText().contains("Excel"));
		}
	}

	@Test
	public void TC_02_Frame() {
		// Step 01: Truy cập vào trang web
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		// Switch to Frame Login page
		driver.switchTo().frame("login_page");
		
		// Step 02: Input vào Customer ID và click Continue
		// Input vào Customer ID
		driver.findElement(By.xpath("//div[text()='Customer ID/ User ID']/following-sibling::div/input")).sendKeys("automationFC");
		
		// Click Continue
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(3);
		
		// Switch to main page
		driver.switchTo().defaultContent();
		
		// Step 03: Verify Passord textbox hiển thị
		Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
		driver.findElement(By.cssSelector("input#keyboard")).sendKeys("Luvina@123");
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
