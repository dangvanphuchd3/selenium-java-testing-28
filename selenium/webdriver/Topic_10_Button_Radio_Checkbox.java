package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Button_Radio_Checkbox {
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
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		
		By loginButton = By.cssSelector("button.fhs-btn-login");
		
		// Verify login button là disable
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		
		String loginButtonBackground = driver.findElement(loginButton).getCssValue("background-image");
		Assert.assertTrue(loginButtonBackground.contains("rgb(224, 224, 224"));
		System.out.println(loginButtonBackground);
		
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("0987666777");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456789");
		sleepInSecond(2);
		
		// Verify login button is enable
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		
		loginButtonBackground = driver.findElement(loginButton).getCssValue("background-color");
		
		Color loginButtonBackgroundColour = Color.fromString(loginButtonBackground);
		Assert.assertEquals(loginButtonBackgroundColour.asHex().toUpperCase(), "#C92127");
	}

	@Test
	public void TC_02_Default_Checkbox_Radio_Single() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		// Click chọn 1 checkbox
		driver.findElement(By.xpath("//label[contains(text(),'Cancer')]/preceding-sibling::input")).click();
		
		// Click chọn 1 radio
		driver.findElement(By.xpath("//label[contains(text(),'Never')]/preceding-sibling::input")).click();
		
		// Verify các checkbox/ radio đã được chọn rồi
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Cancer')]/preceding-sibling::input")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Never')]/preceding-sibling::input")).isSelected());
		
		// Checkbox có thể tự bỏ chọn được
		driver.findElement(By.xpath("//label[contains(text(),'Cancer')]/preceding-sibling::input")).click();
		
		// Verify các checkbox đã được bỏ chọn
		Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(),'Cancer')]/preceding-sibling::input")).isSelected());
		
		// Radio không thể tự bỏ chọn được
		driver.findElement(By.xpath("//label[contains(text(),'Never')]/preceding-sibling::input")).click();
		
		// Verify các radio vẫn được chọn
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Never')]/preceding-sibling::input")).isSelected());
	}

	@Test
	public void TC_03_Default_Checkbox_Radio_Multiple() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		List<WebElement> allCheckboxes = driver.findElements(By.cssSelector("input.form-checkbox"));
		
		// Dùng vòng lặp để duyệt qua và click vào tất cả các checkbox này
		for (WebElement checkbox : allCheckboxes) {
			checkbox.click();
		}
		
		// Verify tất cả các check box được chọn thành công
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertTrue(checkbox.isSelected());
		}
		
		// Nếu như gặp 1 checkbox có tên là X thì mới click
		for (WebElement checkbox : allCheckboxes) {
			if (checkbox.getAttribute("value").equals("Lung Disease")) {
				checkbox.click();
			}
		}
	}
	
	@Test
	public void TC_04_Default_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		// Chọn checkbox
		if (!driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected()) {
			driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).click();
		}
		
		// Verify checkbox đã được chọn
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
		
		// Bỏ chọn nó
		if (driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected()) {
			driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).click();
		}
		// Verify checkbox chưa được chọn
		Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
	}
	
	public void checkToCheckbox (By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}
	
	public void uncheckToCheckbox (By by) {
		if (driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
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
