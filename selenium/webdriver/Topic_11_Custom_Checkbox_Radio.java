package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Custom_Checkbox_Radio {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
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
		
		// Luôn khởi tạo sau biến driver này
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Case_1() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(3);
		
		/* Case 1 */
		// Thẻ input bị che nên không thao tác được
		// Thẻ input dùng để verify được -> Vì hàm isSelected chỉ work với thẻ input
		
		// Thao tác chọn
		driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).click();
		
		// Verify đã chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).isSelected());
	}

	@Test
	public void TC_02_Case_2() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(3);
		
		/* Case 2 */
		// Thẻ khác với input để click (span/ div/ label...) -> đang hiển thị là được
		// Thẻ này lại không dùng để verify được -> Vì hàm isSelected chỉ work với thẻ input
		
		// Thao tác chọn
		driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div")).click();
		// Or //div[text()='Đăng ký cho người thân']/parent::label
		
		// Verify đã chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div")).isSelected());
		
	}

	@Test
	public void TC_03_Case_3() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(3);
		
		/* Case 3 */
		// Thẻ khác với input để click (span/ div/ label...) -> đang hiển thị là được
		// Dùng thẻ input để verify
		
		// Thao tác chọn
		driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div")).click();
		// Or //div[text()='Đăng ký cho người thân']/parent::label
		
		// Verify đã chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).isSelected());
		
		// Ở trường hợp viết basic/ demo thì được
		// Nếu apply vào 1 framework/ dự án thực tế thì không nên
		// Vì 1 element phải define tới nhiều locator (dễ bị hiểu nhầm/ mất time để maitain/ ko tập trung)
		
	}
	
	@Test
	public void TC_04_Case_4() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(3);
		
		/* Case 4 */
		// Thẻ input bị ẩn nhưng vẫn dùng để click
		// Hàm click() của WebElement nó sẽ không thao tác vào element bị ẩn được
		// => The element must be visible and it must have a height and width greater then 0.
		
		// Nên sẽ dùng 1 hàm click() của Javascrip để click (click vào element ẩn được)
		// Selenium nó cung cấp 1 thư viện để có thể nhúng các đoạn code JS vào kịch bản test được -> JavascriptExecutor
		
		// Thẻ input lại dùng để verify được -> Vì hàm isSelected() nó chỉ work với thẻ input
		
		// Thao tác chọn
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")));
		sleepInSecond(3);
		
		// Verify đã chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).isSelected());
		
	}
	
	@Test
	public void TC_05_Case_5() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(3);
		
		By radioButton = By.cssSelector("div[aria-label='Cần Thơ']");
		By checkbox = By.cssSelector("div[aria-label='Quảng Ngãi']");
		
		// Verify chưa được chọn
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Cần Thơ'][aria-checked='false']")).isDisplayed());
		
		// Thao tác chọn
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(radioButton));
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkbox));
		sleepInSecond(2);
		
		// Verify đã chọn thành công
		// Cách 1:
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Cần Thơ'][aria-checked='true']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Quảng Ngãi'][aria-checked='true']")).isDisplayed());
		
		// Cách 2:
		Assert.assertEquals(driver.findElement(radioButton).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(checkbox).getAttribute("aria-checked"), "true");
		
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
