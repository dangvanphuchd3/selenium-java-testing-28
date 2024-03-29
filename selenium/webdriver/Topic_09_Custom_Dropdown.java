package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
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
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_JQuery() {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		
		// Muốn chọn item cho Speed dropdown
		selectItemInDropdown("span#speed-button", "ul#speed-menu div[role='option']", "Slower");
		sleepInSecond(3);
		
		selectItemInDropdown("span#speed-button", "ul#speed-menu div[role='option']", "Fast");
		sleepInSecond(3);
		
		selectItemInDropdown("span#speed-button", "ul#speed-menu div[role='option']", "Slow");
		sleepInSecond(3);
		
		/* CHỌN LẦN ĐÂU TIÊN */
		// 1 - Click vào 1 thẻ bất kỳ để làm sao cho nó xổ ra hết các item của dropdown
		driver.findElement(By.cssSelector("span#speed-button")).click();
		
		// 2 - Chờ cho tất cả các item được load ra thành công
		// Lấy Locator đại diện cho tất cả các items
		// Lấy đến thẻ chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#speed-menu div[role='option']")));
		
		// Đưa hết item trong dropdown vào 1 List
		List<WebElement> speedDropdownItems  = driver.findElements(By.cssSelector("ul#speed-menu div[role='option']"));
		
		// 3 - Tìm item xem đúng cái đang cần hay không (dùng vòng lặp duyệt qua)
		for (WebElement tempItem : speedDropdownItems) {
			String itemText = tempItem.getText().trim();
			System.out.println(itemText);
			
			// 4 - Kiểm tra text của item đúng với cái mình mong muốn
			if (itemText.equals("Medium")) {
				// 5 - Click vào item đó
				tempItem.click();
				
				// Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
				break;
			}
		}
		
		// 3.1 - Nếu nó nằm trong khoảng nhìn thấy của User không cần phải scroll xuống
		// 3.2 - Nếu nó không nằm trong khoảng nhìn thấy của User thì cần scroll xuống đến item đó

	}

	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemInDropdown("div[role='listbox']", "div[role='option'] span", "Elliot Fu");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div[role='alert']")).getText(), "Elliot Fu");
	}

	@Test
	public void TC_03_VeuJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItemInDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText().trim(), "Third Option");
	}
	
	@Test
	public void TC_04_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		enterAndSelectItemInDropdown("input.search", "div[role='listbox'] span", "Angola");
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div[role='alert']")).getText().trim(), "Angola");
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
	
	// Tránh lặp lại code nhiều lần chỉ cần gọi hàm ra để dùng
	// Đi kèm với tham số
	// Nếu truyền cứng 1 giá trị vào trong hàm = vô nghĩa
	// Nên define để dùng đi dùng lại nhiều lần
	public void selectItemInDropdown(String parentCss, String allItemCss, String expectedTextItem) {
		// 1 - Click vào 1 thẻ bất kỳ để làm sao cho nó xổ ra hết các item của dropdown
		driver.findElement(By.cssSelector(parentCss)).click();
		
		// 2 - Chờ cho tất cả các item được load ra thành công
		// Lấy Locator đại diện cho tất cả các items
		// Lấy đến thẻ chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemCss)));
		
		// Đưa hết item trong dropdown vào 1 List
		List<WebElement> speedDropdownItems  = driver.findElements(By.cssSelector(allItemCss));
		
		// 3 - Tìm item xem đúng cái đang cần hay không (dùng vòng lặp duyệt qua)
		for (WebElement tempItem : speedDropdownItems) {
			String itemText = tempItem.getText();
			System.out.println(itemText);
			
			// 4 - Kiểm tra text của item đúng với cái mình mong muốn
			if (itemText.equals(expectedTextItem)) {
				// 5 - Click vào item đó
				tempItem.click();
				
				// Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
				break;
			}
		}
	}
	
	public void enterAndSelectItemInDropdown (String textboxCss, String allItemCss, String expectedTextItem) {
		// 1 - Nhập expected text item vào - xổ ra các item matching
		driver.findElement(By.cssSelector(textboxCss)).sendKeys(expectedTextItem);
		sleepInSecond(1);
		
		// 2 - Chờ cho tất cả các item được load ra thành công
		// Lấy Locator đại diện cho tất cả các items
		// Lấy đến thẻ chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemCss)));
		
		// Đưa hết item trong dropdown vào 1 List
		List<WebElement> speedDropdownItems  = driver.findElements(By.cssSelector(allItemCss));
		
		// 3 - Tìm item xem đúng cái đang cần hay không (dùng vòng lặp duyệt qua)
		for (WebElement tempItem : speedDropdownItems) {
			String itemText = tempItem.getText().trim();
			System.out.println(itemText);
			// 4 - Kiểm tra text của item đúng với cái mình mong muốn
			if (itemText.equals(expectedTextItem)) {
				sleepInSecond(1);
				// 5 - Click vào item đó
				tempItem.click();
				
				// Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
				break;
			}
		}
	}
}
