package webdriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Alert {
	WebDriver driver;
	WebDriverWait explicitWait;
	Alert alert;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String authenFirefox = projectPath + "\\autoIT\\authen_firefox.exe";
	String authenChrome = projectPath + "\\autoIT\\authen_chrome.exe";
	String username = "admin";
	String password = "admin";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(3);
		
		/* Không dùng cách 1
		// 1 - Có thể switch qua và tương tác luôn
		driver.switchTo().alert();
		*/
		
		// 2 - Cần wait trước khi nào nó xuất hiện thì mới switch qua và tương tác
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// Tương tác
		// alert.getText();
		
		// Verify alert title đúng như mong đợi
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		
		// Accept cái alert này
		alert.accept();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
		
	}

	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(3);
		
		// 2 - Cần wait trước khi nào nó xuất hiện thì mới switch qua và tương tác
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// Verify alert title đúng như mong đợi
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		
		// Cancel Alert
		alert.dismiss();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
		
	}

	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(3);
		
		// 2 - Cần wait trước khi nào nó xuất hiện thì mới switch qua và tương tác
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// Verify alert title đúng như mong đợi
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		
		// Nhập giá trị
		String courseName = "Fullstack Selenium Java";
		alert.sendKeys(courseName);
		sleepInSecond(2);
		
		// Accept Alert
		alert.accept();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + courseName);
	}
	
	@Test
	public void TC_04_Authentication_Alert_I() {
		// Truyền trực tiếp Username/ Password vào trong chính Url này -> Tự động SignIn luôn
		// http:// + Username : Password @ the-internet.herokuapp.com/basic_auth
		driver.get(passUserAndPassToUrl("http://the-internet.herokuapp.com/basic_auth", "admin", "admin"));
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
		
	}
	
	@Test
	public void TC_04_Authentication_Alert_II() {
		driver.get("http://the-internet.herokuapp.com/");
		
		String authenUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		
		driver.get(passUserAndPassToUrl(authenUrl, "admin", "admin"));
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
		
	}
	
	@Test
	public void TC_04_Authentication_Alert_III() throws IOException {
		// AutoIT chỉ chạy trên window ko chạy trên Mac, Linux
		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] {authenFirefox, username, password});
		} else if (driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] {authenChrome, username, password});
		}
		
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		sleepInSecond(10);
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
		
	}
	
	
	public String passUserAndPassToUrl (String url, String userName, String password) {
		// Url: http://the-internet.herokuapp.com/basic_auth
		// Username: admin
		// Password: admin
		String[] arrayUrl = url.split("//");
		return arrayUrl[0] + "//" + userName + ":" + password + "@" + arrayUrl[1];
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
