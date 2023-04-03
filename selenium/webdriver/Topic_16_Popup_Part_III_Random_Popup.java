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
		driver.manage().window().maximize();
		
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
			sleepInSecond(5);
			
			// Verify
			Assert.assertEquals(driver.findElement(By.cssSelector("div.lepopup-element-html-content>h4")).getText(), "Thank you!");
			System.out.println(driver.findElement(By.cssSelector("div.lepopup-element-html-content>h4")).getText());
			
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
	public void TC_02_Popup_Random_In_DOM_2() {
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(50);
		
		By popup = By.cssSelector("div#tve-p-scroller");
		
		if (driver.findElement(popup).isDisplayed()) {
			// Close popup
			driver.findElement(By.cssSelector("svg.tcb-icon")).click();
			sleepInSecond(3);
		}
		
		driver.findElement(By.xpath("//button[text()='Danh sách khóa học']")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.getTitle(), "Lịch khai giảng các khóa học tại VNK EDU | VNK EDU");
	}

	@Test
	public void TC_03_Popup_Random_Not_In_DOM_3() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(10);
		
		By popup = By.cssSelector("div.popup-content");
		
		if (driver.findElements(popup).size() > 0 && driver.findElements(popup).get(0).isDisplayed()) {
			driver.findElement(By.id("popup-name")).sendKeys("Elon Musk");
			driver.findElement(By.id("popup-email")).sendKeys(emailAddress);
			driver.findElement(By.id("popup-phone")).sendKeys("0398737194");
			sleepInSecond(3);
			
			driver.findElement(By.id("close-popup")).click();
			sleepInSecond(3);
		}
		
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		sleepInSecond(3);
		
		String courseName = "Khóa học thiết kế hệ thống Cơ điện Công trình";
		
		driver.findElement(By.id("search-courses")).sendKeys(courseName);
		driver.findElement(By.id("search-course-button")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.course-content>h4")).getText(), courseName);
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
