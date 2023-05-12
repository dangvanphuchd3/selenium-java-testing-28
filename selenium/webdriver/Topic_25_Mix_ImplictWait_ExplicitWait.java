package webdriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_25_Mix_ImplictWait_ExplicitWait {
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
		
	}

	//@Test
	public void TC_01_Element_Found() {
		// Element có xuất hiện và không cần chờ hết timeout
		// Dù có set cả 2 loại wait thì không ảnh hưởng
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 10);
		
		// Implicit Wait: Chi apply cho việc findElement/ findElements
		// Explicit Wait: Cho các điều kiện của element
		
		driver.get("https://www.facebook.com/");
		
		// Explicit
		System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
		System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());
		
		// Implicit
		System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
		driver.findElement(By.cssSelector("input#email"));
		System.out.println("Thời gian kết thúc của implicit: " + getTimeStamp());	
	}

	//@Test
	public void TC_02_Element_Not_Found_Implicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		
		// Implicit
		System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
		try {
			driver.findElement(By.cssSelector("input#selenium"));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của implicit: " + getTimeStamp());
		}
		
		// Output 
		// Có cơ chế tìm lại sau mỗi nửa giây (0.5s)
		// Khi hết timeout sẽ đáng fail testcase này
		// Throw ra 1 exception: NoSuchElement
	}

	//@Test
	public void TC_03_Element_Not_Found_Implicit_Explicit() {
		// 3.1 - Implicit = Explicit
		// 3.2 - Implicit < Explicit
		// 3.3 - Implicit > Explicit
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 5);
		
		driver.get("https://www.facebook.com/");
		
		// Implicit
		System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
		try {
			driver.findElement(By.cssSelector("input#selenium"));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của implicit: " + getTimeStamp());
		}
		
		// Explicit
		System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#selenium")));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());
		}
	}
	
	//@Test
	public void TC_04_Element_Not_Explicit_By() {
		explicitWait = new WebDriverWait(driver, 5);
		
		driver.get("https://www.facebook.com/");
		
		// Explicit - By là tham số nhận vào của hàm Explicit - visibilityOfElementLocated (By)
		// Implicit = 0
		// Tổng time = explicit
		System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#selenium")));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());
		}
	}
	
	@Test
	public void TC_05_Element_Not_Explicit_WebElement() {
		explicitWait = new WebDriverWait(driver, 5);
		
		driver.get("https://www.facebook.com/");
		
		System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
		try {
			explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input#selenium"))));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());
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
	
	public String getTimeStamp() {
		Date date = new Date();
		return date.toString();
	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
		
		// Show ra time-stamp tại thời điểm gọi hàm này
		// time-stamp = ngày-giờ-phút-giây
	}
}
