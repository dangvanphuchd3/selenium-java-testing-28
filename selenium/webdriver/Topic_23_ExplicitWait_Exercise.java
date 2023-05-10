package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_23_ExplicitWait_Exercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;
	
	String beachFileName = "beach.jpg";
	String computerFileName = "computer.jpg";
	String mountainFileName = "mountain.jpg";
	
	String beachFilePath = projectPath + "\\uploadFiles\\" + beachFileName;
	String computerFilePath = projectPath + "\\uploadFiles\\" + computerFileName;
	String mountainFilePath = projectPath + "\\uploadFiles\\" + mountainFileName;

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
	public void TC_01_Ajax_Loading() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		explicitWait = new WebDriverWait(driver, 15);
		
		// Wait cho Date Picker được hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.RadCalendar")));
		
		// Wait cho ngày 19 được phép click
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='19']")));
		
		// Click vào ngày 19
		driver.findElement(By.xpath("//a[text()='19']")).click();
		
		// Wait cho Ajax icon loading biến mất (invisible)
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar1']>div.raDiv")));
		
		// Wail cho ngày vừa được click là được phép click trở lại
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class='rcSelected']/a[text()='19']")));
		
		// Verify cho Selected Dates là "Friday, May 19, 2023"
		Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")).getText(), "Friday, May 19, 2023");
		
	}

	@Test
	public void TC_02_Upload_File() {
		driver.get("https://gofile.io/uploadFiles");
		
		explicitWait = new WebDriverWait(driver, 120);
		
		// Wait cho Add Files button được visible
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#filesUpload button.filesUploadButton")));
		
		// Upload file
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(beachFilePath + "\n" + computerFilePath);
		
		// Wait cho các loading icon của từng file biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress-bar"))));
		
		// Wait cho Upload message thành công được visible
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-auto text-center']/div[text()='Your files have been successfully uploaded']")));
		
		// Verify message này displayed
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-auto text-center']/div[text()='Your files have been successfully uploaded']")).isDisplayed());
		
		// Wait + click link download được visible
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.mainUploadSuccessLink a.ajaxLink"))).click();
		
		// Click vào button Showfile
		// driver.findElement(By.cssSelector("div.mainUploadSuccessLink a.ajaxLink")).click();
		
		// Wait cho file name vs button download/ play hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span.contentName")));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='contentName']/parent::a/parent::div/following-sibling::div//span[text()='Download']")));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='contentName']/parent::a/parent::div/following-sibling::div//span[text()='Play']")));
		
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
