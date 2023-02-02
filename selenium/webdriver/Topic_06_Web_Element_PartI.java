package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_PartI {
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
	public void TC_01_WebElement() {
		WebElement element = driver.findElement(By.className(""));
		
		// Dùng cho các textbox/ textarea/ dropdown (Editable)
		// Xóa dữ liệu trước khi nhập text
		element.clear();
		
		// Dùng cho các textbox/ textarea/ dropdown (Editable)
		// Nhập liệu
		element.sendKeys("");
		
		// Click vào các button/ link/ checkbox/ radio/ image/ ...
		element.click();
		
		// Search store
		String searchAttribute = element.getAttribute("placeholder");
		String emailTextboxAttribute = element.getAttribute("value");
		
		//GUI: Font/ Size/ Color/ Location/ Positon/ ... 
		element.getCssValue("background-color");
		
		// Vị trí của element so với web (bên ngoài)
		element.getLocation();
		
		// Kích thước của element (bên trong)
		element.getSize();
		
		// Location + Size
		element.getRect();
		
		// Chụp hình khi testcase fail
		element.getScreenshotAs(OutputType.FILE);
		element.getScreenshotAs(OutputType.BYTES);
		element.getScreenshotAs(OutputType.BASE64);
		
		// Lấy ra tên thẻ HTML
		element.getTagName();
		
		// Lấy text từ Error message/ success message/ label/ header/ ..
		element.getText();
		
		// Khi nào dùng getText - getAttribute
		// Khi value cần lấy nằm bên ngoài -> getText
		// Khi value cần lấy nằm bên trong -> getAttribute
		
		// Verify xem 1 element hiển thị hoặc không
		// Phạm vi: Tất cả các element
		element.isDisplayed();
		Assert.assertTrue(element.isDisplayed());
		Assert.assertFalse(element.isDisplayed());
		
		// Dung de verify xem 1 element duoc chon hay chua
		// Pham vi: Checkbox/ Radio
		Assert.assertTrue(element.isSelected());
		Assert.assertFalse(element.isSelected());
		
		// Các element nằm trong thẻ form
		// Tương ứng với hành vi End User (Enter)
		element.submit();
		
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
