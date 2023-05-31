package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_27_Wait_Page_Ready {
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
		
		explicitWait = new WebDriverWait(driver, 30);
	}

	//@Test
	public void TC_01_Orange_HRM_API() {
		driver.get("https://api.orangehrm.com/");
		
		// Wait cho icon loading biến mất
		// Vì khi nó biến mất thì trang sẽ load hết dữ liệu về thành công
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loader>div.spinner")));
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#project h1")).getText(), "OrangeHRM REST API Documentation");
	}

	@Test
	public void TC_02_Admin_NopCommerce() {
		driver.get("https://admin-demo.nopcommerce.com");
		
		driver.findElement(By.cssSelector("input#Email")).clear();
		driver.findElement(By.cssSelector("input#Email")).sendKeys("admin@yourstore.com");
		
		driver.findElement(By.cssSelector("input#Password")).clear();
		driver.findElement(By.cssSelector("input#Password")).sendKeys("admin");
		
		// Click chuyển trang: Từ Login vào Dashboard
		driver.findElement(By.cssSelector("button.login-button")).click();
		
		// Cách 1: Dùng explicitWait để wait đến khi element có thể click được
		// explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']")));
		// -> Failed không dùng được
		
		// Cách 2: Wait cho ajax loading biến mất trên màn hình
		// explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#ajaxBusy")));
		// -> Success
		// Có thể viết thành 1 hàm để sử dụng
		// Assert.assertTrue(waitForAjaxBusyLoadingInvisible());
		
		// Cách 3: Sử dụng hàm để kiểm tra page ready
		Assert.assertTrue(isPageLoadedSuccess());
		
		// Click chuyển trang: Từ Dashboard về Login
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		
		// Assert.assertTrue(waitForAjaxBusyLoadingInvisible());
		Assert.assertTrue(isPageLoadedSuccess());
		
		Assert.assertEquals(driver.getTitle(), "Your store. Login");
	}

	@Test
	public void TC_03_() {
		
	}
	
	public boolean isPageLoadedSuccess() {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	
	public boolean waitForAjaxBusyLoadingInvisible() {
		return explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#ajaxBusy")));
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
