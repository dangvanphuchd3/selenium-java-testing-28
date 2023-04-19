package webdriver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Upload_File_AutoIT {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	
	String beachFileName = "beach.jpg";
	String computerFileName = "computer.jpg";
	String mountainFileName = "mountain.jpg";
	
	String beachFilePath = projectPath + "\\uploadFiles\\" + beachFileName;
	String computerFilePath = projectPath + "\\uploadFiles\\" + computerFileName;
	String mountainFilePath = projectPath + "\\uploadFiles\\" + mountainFileName;
	
	String autoITFirefoxOneTimePath = projectPath + "\\autoIT\\firefoxUploadOneTime.exe";
	String autoITChromeOneTimePath = projectPath + "\\autoIT\\chromeUploadOneTime.exe";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		jsExecutor = (JavascriptExecutor) driver;
		
	}

	@Test
	public void TC_01_One_File_Per_Time() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		// Click để bật Open File Dialog lên
		driver.findElement(By.cssSelector("span.btn-success")).click();
		sleepInSecond(2);
		
		// Load file lên
		Runtime.getRuntime().exec(new String[] {autoITFirefoxOneTimePath, beachFilePath});
		
		// Verify file được load lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + beachFileName + "']")).isDisplayed());
		
		// Click upload
		List<WebElement> uploadButton = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : uploadButton) {
			button.click();
			sleepInSecond(3);
		}
		
		// Verify upload thành công (Link)
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + beachFileName + "']")).isDisplayed());
		
		// Verify upload thành công (image)
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'" + beachFileName + "')]"));
	}

	@Test
	public void TC_02_() {
		
	}

	@Test
	public void TC_03_() {
		
	}
	
	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}
	
	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
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
