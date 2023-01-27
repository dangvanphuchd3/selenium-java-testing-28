package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_05_Web_Browser {
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
		
		
		//Tương tác với Browser thông qua biến WebDriver driver
		//Tương tác với Element thông qua biến WebElement element
		
	}

	@Test
	public void TC_01_() {
		//Java Document (Cách sử dụng hàm này như thế nào)
		//>=2: Nó sẽ đóng tab/ window mà nó đang đứng
		//=1: Nó cũng đóng Browser
		driver.close();//*
		
		//Không quan tâm bao nhiêu tab/ window -> Đóng Browser
		driver.quit();//**
		
		// - Có thể lưu vào 1 biến để sử dụng cho các step sau -> dùng lại nhiều lần
		//Clean Code
		//Làm sao để code đúng/ chạy được
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email'"));
		emailTextbox.clear();
		emailTextbox.sendKeys("");
		
		//Bad code
		driver.findElement(By.xpath("//input[@id='email'")).clear();
		driver.findElement(By.xpath("//input[@id='email'")).sendKeys("");
		
		// - Có thể sử dụng luôn không cần tạo biến
		driver.findElement(By.xpath("//button[@id='login'")).click();
		
		//Tìm nhiều element
		List<WebElement> checkboxes = driver.findElements(By.xpath(""));
		
		//Mở ra Url bất kỳ
		driver.get("https://www.facebook.com/");
		
		//Click vào link tiếng Việt
		
		//Trả về Url của page hiện tại
		driver.getCurrentUrl();
		
		// - Có thể sử dụng luôn (không cần sử dụng biến)
		Assert.assertEquals(driver.getCurrentUrl(), "https://vi-vn.facebook.com/");
		
		// Trả về source của HTML của page hiện tại
		// Verify tương đối
		driver.getPageSource();
		Assert.assertTrue(driver.getPageSource().contains("Facebook"));
		
		// Trả về title của page hiện tại
		Assert.assertEquals(driver.getTitle(), "Facebook");
		
		// Lấy ra được ID của Window/ Tab mà driver đang đứng (active)
		String loginWindowID = driver.getWindowHandle();
		
		// Lấy ra ID của tất cả Window/ Tab
		Set<String> allIDs = driver.getWindowHandles();
		
		// Cookie/ Cache
		Options opt = driver.manage();
		
		//Login thành công -> Lưu lại
		opt.getCookies();
		// Testcase khác -> set cookie vào lại -> Không cần phải login nữa
		
		opt.logs();
		
		// Khoảng thời gian chờ element xuất hiện
		Timeouts time = opt.timeouts();
		time.implicitlyWait(5, TimeUnit.SECONDS);
		
		// Khoảng thời gian chờ page load xong trong vòng x giây
		time.pageLoadTimeout(5, TimeUnit.SECONDS);
		
		// Khoảng thời gian chờ scrip được thực thi trong vòng x giây
		time.setScriptTimeout(5, TimeUnit.SECONDS);
		
		Window win = opt.window();
		win.fullscreen();
		win.maximize();
		
		// Test FUI: Functional
		// Test GUI: Font/ Size/ Color/ Position/ Location/ ...
		win.getPosition();
		win.getSize();
		
		Navigation nav = driver.navigate();
		nav.back();
		nav.refresh();
		nav.forward();
		nav.to("https://www.facebook.com/");
		
		TargetLocator tar = driver.switchTo();
		tar.alert();
		tar.frame("");
		tar.window("");
	}

	@Test
	public void TC_02_() {
		
	}

	@Test
	public void TC_03_() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
