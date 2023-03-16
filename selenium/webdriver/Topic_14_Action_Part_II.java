package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Action_Part_II {
	WebDriver driver;
	Actions action;
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
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> listNumber = driver.findElements(By.cssSelector("ol#selectable>li"));
		// Đang chứa 12 số/ item trong list này
		
		// 1 - Click vào số 1 (soutce)
		action.clickAndHold(listNumber.get(0))
		// 2 - vẫn giữ chuột/ chưa nhả ra
			// 3 - Di chuột tới số (target)
			.moveToElement(listNumber.get(7))
			
			// 4 - Nhả chuột trái ra
			.release()
			
			// Execute
			.perform();
		sleepInSecond(2);
		
		List<WebElement> listSelectedNumber = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(listSelectedNumber.size(), 8);
	}

	@Test
	public void TC_02_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		// Chạy cho cả MAC/ Windows
		Keys key = null;
		if (osName.contains("Windows")) {
			key = Keys.CONTROL;
		} else {
			key = Keys.COMMAND;
		}
		
		List<WebElement> listNumber = driver.findElements(By.cssSelector("ol#selectable>li"));
		// Đang chứa 12 số/ item trong list này
		
		// Nhấn Ctrl xuống
		action.keyDown(key).perform();
		// Click chọn các số random
		action.click(listNumber.get(0))
		 .click(listNumber.get(3))
		 .click(listNumber.get(5))
		 .click(listNumber.get(10))
		 .click(listNumber.get(8)).perform();
		
		// Nhả phím Ctrl ra
		action.keyUp(key).perform();
		
		List<WebElement> listSelectedNumber = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(listSelectedNumber.size(), 5);
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
