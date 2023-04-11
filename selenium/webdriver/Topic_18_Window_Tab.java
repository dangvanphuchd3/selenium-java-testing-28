package webdriver;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JScrollBar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Window_Tab {
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
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_ID() {
		// Step 01: Truy cập vào trang parent page
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Lấy ra id của tab hiện tại
		String parentPageWindowID = driver.getWindowHandle();
		System.out.println("Parent Page = " + parentPageWindowID);
		
		// Step 02: Click link "GOOGLE" -> Switch qua page mới
		// Click vào link "GOOGLE" để bật ra 1 tab mới
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);
		
		/* // Lấy ra tất cả các ID của pages
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		// Dùng vòng lặp duyệt và kiểm tra
		for (String id : allWindowIDs) {
			if (!id.equals(parentPageWindowID)) {
				driver.switchTo().window(id);
				sleepInSecond(2);
			}
		} */
		switchToWindowByPageTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");
		
		// Step 04: Switch về parent window
		driver.switchTo().window(parentPageWindowID);
		
		// Step 05: Click "FACEBOOK" link -> Switch qua tab mới
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(2);
		
		switchToWindowByPageTitle("Facebook – log in or sign up");
		
		// Step 06: Kiểm tra title của Window mới
		Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
		
		// Step 07: Switch về parent window
		driver.switchTo().window(parentPageWindowID);
		
		// Step 08: Click "TIKI" link -> Switch qua tab mới
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		sleepInSecond(2);
		
		switchToWindowByPageTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		
		// Step 09: Kiểm tra title của window mới
		Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		
		// Step 10: Close tất cả cửa sổ/tab ngoại trừ parent window
		// Lấy ra tất cả ID của các tab
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		// Dùng vòng lặp duyệt và kiểm tra
		for (String id : allWindowIDs) {
			// Switch to tab window
			driver.switchTo().window(id);
			sleepInSecond(2);
			
			if(!id.equals(parentPageWindowID)) {
				driver.close();
			}
		}
		
		driver.switchTo().window(parentPageWindowID);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
	}

	//@Test
	public void TC_02_Exercise_02() {
		// Step 01: Truy cập trang
		driver.get("https://skills.kynaenglish.vn/");
		sleepInSecond(5);
		
		// Click vào img Facebook ở footer
		driver.findElement(By.xpath("//img[@alt='facebook']")).click();
		
		// Switch to page Facebook
		switchToWindowByPageTitle("Kyna.vn - Home | Facebook");
		sleepInSecond(2);
		
		// Kiểm tra Url
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
		
		// Switch to page Kyna
		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");
		sleepInSecond(2);
		
		// Click vào img Youtube ở footer
		driver.findElement(By.xpath("//img[@alt='youtube']")).click();
		sleepInSecond(3);
		
		// Switch to page Youtube
		switchToWindowByPageTitle("Kyna.vn - YouTube");
		sleepInSecond(2);
		
		// Kiểm tra Url của page
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
		
		// Switch to page Kyna
		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");
		sleepInSecond(2);
		
		
		// Click vào fanpage Facebook
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'kyna.vn')]")));
		sleepInSecond(2);
		
		driver.findElement(By.cssSelector("a[title='Kyna.vn']")).click();
		sleepInSecond(2);
		
		driver.switchTo().defaultContent();
		sleepInSecond(2);
		
		// Switch to Page Facebook
		// Lấy ra tất cả các ID của pages
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		for (String id : allWindowIDs) {
			// Switch từng ID trước
			driver.switchTo().window(id);
			
			// Lấy ra title của page
			String actualTitle = driver.getCurrentUrl();
			if (actualTitle.equals("https://www.facebook.com/kyna.vn/")) {
				break;
			}
		}	
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn/");
		
		// Switch to page Kyna
		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");
		sleepInSecond(2);
		
		driver.findElement(By.xpath("//img[contains(@src, 'dathongbao.png')]")).click();
		sleepInSecond(3);
		
		switchToWindowByPageTitle("Thông tin website thương mại điện tử - Online.Gov.VN");
		Assert.assertEquals(driver.getCurrentUrl(), "http://online.gov.vn/Home/WebDetails/61473");
		
		// Switch to page Kyna
		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");
		sleepInSecond(2);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		driver.findElement(By.xpath("//img[contains(@src, 'logoCCDV.png')]")).click();
		sleepInSecond(2);
		
		switchToWindowByPageTitle("Thông tin website thương mại điện tử - Online.Gov.VN");
		Assert.assertEquals(driver.getCurrentUrl(), "http://online.gov.vn/Home/WebDetails/60140");
		
		// Step 05: Quay lại parent tab và đóng tất cả các tab còn lại
		// Lấy ra tất cả các ID của pages
		allWindowIDs = driver.getWindowHandles();
		
		for (String id : allWindowIDs) {
			// Switch từng ID trước
			driver.switchTo().window(id);
			
			// Lấy ra title của page
			String actualTitle = driver.getTitle();
			
			// Chạy câu lệnh điều kiện
			if (!actualTitle.equals("Kyna.vn - Học online cùng chuyên gia")) {
				driver.close();
				sleepInSecond(2);
			}
		}
		// Switch to page Kyna
		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");
		sleepInSecond(2);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://skills.kynaenglish.vn/");
	}

	@Test
	public void TC_03_() {
		
	}
	// Dùng được duy nhất cho 2 ID Window/Tab
	public void switchToWindowByID (String otherID) {
		// Lấy ra tất cả các ID của pages
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		// Dùng vòng lặp duyệt và kiểm tra
		for (String id : allWindowIDs) {
			if (!id.equals(otherID)) {
				driver.switchTo().window(id);
				sleepInSecond(2);
			}
		}
	}
	
	// Dùng được cho nhiều hơn 2 ID Wind
	public void switchToWindowByPageTitle(String expectedPageTitle) {
		// Lấy ra tất cả các ID của pages
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		for (String id : allWindowIDs) {
			// Switch từng ID trước
			driver.switchTo().window(id);
			
			// Lấy ra title của page
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(expectedPageTitle)) {
				break;
			}
		}
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
