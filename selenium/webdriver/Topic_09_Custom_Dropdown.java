package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Custom_Dropdown {
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
	public void TC_01_() {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		
		// 1 - Click vào 1 thẻ bất kỳ để làm sao cho nó xổ ra hết các item của dropdown
		// 2 - Chờ cho tất cả các item được load ra thành công
		// 3 - Tìm item xem đúng cái đang cần hay không
		// 3.1 - Nếu nó nằm trong khoảng nhìn thấy của User không cần phải scroll xuống
		// 3.2 - Nếu nó không nằm trong khoảng nhìn thấy của User thì cần scroll xuống đến item đó
		// 4 - Kiểm tra text của item đúng với cái mình mong muốn
		// 5 - Click vào item đó
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
