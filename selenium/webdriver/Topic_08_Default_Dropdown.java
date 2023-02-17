package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Default_Dropdown {
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firstName, lastName, emailAddress, companyName, password, day, month, year;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		firstName = "Elon";
		lastName = "Musk";
		emailAddress = "elonmusk" + getRandomNumber() + "@gmail.com";
		companyName = "SpaceX";
		password = "123456789";
		day = "20";
		month = "September";
		year = "1977";
		
	}

	@Test
	public void TC_01_Register_New_Account() {
		driver.get("https://demo.nopcommerce.com/");

		driver.findElement(By.cssSelector("a.ico-register")).click();
		
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		
		/* Cách 1:
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText("18");
		*/
		
		// Cách 2:
		// Ngắn gọn, khuyến khích dùng
		new Select(driver.findElement(By.name("DateOfBirthDay"))).selectByVisibleText(day);
		new Select(driver.findElement(By.name("DateOfBirthMonth"))).selectByVisibleText(month);
		new Select(driver.findElement(By.name("DateOfBirthYear"))).selectByVisibleText(year);
		
		/*
		// Chọn ngày 7
		select.selectByIndex(7);
		====================================================================
		- Sẽ không dùng được nếu item trong dropdown bị thay đổi (thêm/sửa/xóa)
		- Nếu dùng index thì có nhớ được nó là item nào thực tế nếu testcase fail và cần manual test lại
		
		// Chọn ngày 14
		select.selectByValue("14");
		====================================================================
		- Thuộc tính không bắt buộc của item (có/ không có value)
		- Giá trị thuộc tính khó nhớ
		
		// Chọn ngày 18
		select.selectByVisibleText("18");
		====================================================================
		- Vẫn dùng được nếu item trong dropdown bị thay đổi (thêm/ sửa/ xóa)
		- TC fail và cần manual test lại rất dễ
		- Text bắt buộc phải có
		
		*/
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");

	}

	@Test
	public void TC_02_Add_Address() {

	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
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
