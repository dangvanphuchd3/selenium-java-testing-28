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
	String country, state, city, address, postalCode, phoneNumber;

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
		country = "United States";
		state = "Arizona";
		city = "Phoenix";
		address = "1624 Coplin Avenue";
		postalCode = "85032";
		phoneNumber = "602-485-7005";
		
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
		
		driver.findElement(By.cssSelector("a.ico-login")).click();
		sleepInSecond(2);
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.login-button")).click();
		
		driver.findElement(By.className("ico-account")).click();
		
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
		
		Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthDay"))).getFirstSelectedOption().getText(), day);
		Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthMonth"))).getFirstSelectedOption().getText(), month);
		Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthYear"))).getFirstSelectedOption().getText(), year);
		
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), emailAddress);
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), companyName);
		

	}

	@Test
	public void TC_02_Add_Address() {
		driver.findElement(By.xpath("//li[@class='customer-addresses inactive']/a")).click();
		driver.findElement(By.cssSelector("button.add-address-button")).click();
		
		driver.findElement(By.id("Address_FirstName")).sendKeys(firstName);
		driver.findElement(By.id("Address_LastName")).sendKeys(lastName);
		driver.findElement(By.id("Address_Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Address_Company")).sendKeys(companyName);
		
		new Select(driver.findElement(By.id("Address_CountryId"))).selectByVisibleText(country);
		new Select(driver.findElement(By.id("Address_StateProvinceId"))).selectByVisibleText(state);
		
		driver.findElement(By.id("Address_City")).sendKeys(city);
		driver.findElement(By.id("Address_Address1")).sendKeys(address);
		driver.findElement(By.id("Address_ZipPostalCode")).sendKeys(postalCode);
		driver.findElement(By.id("Address_PhoneNumber")).sendKeys(phoneNumber);
		
		driver.findElement(By.cssSelector("button.save-address-button")).click();
		
		// Verify message added successfully
		Assert.assertEquals(driver.findElement(By.cssSelector("p.content")).getText(), "The new address has been added successfully.");
		
		// Verify name = firstName + lastName
		Assert.assertEquals(driver.findElement(By.cssSelector("li.name")).getText(), firstName + " " + lastName);
		
		// Verify emailAddress
		Assert.assertTrue(driver.findElement(By.cssSelector("li.email")).getText().contains(emailAddress));
		
		// Verify phoneNumber
		Assert.assertTrue(driver.findElement(By.cssSelector("li.phone")).getText().contains(phoneNumber));
		
		// Verify company
		Assert.assertEquals(driver.findElement(By.cssSelector("li.company")).getText(), companyName);
		
		// Verify Address
		Assert.assertEquals(driver.findElement(By.cssSelector("li.address1")).getText(), address);
		
		// Verify city, state, zip
		Assert.assertTrue(driver.findElement(By.cssSelector("li.city-state-zip")).getText().contains(city));
		Assert.assertTrue(driver.findElement(By.cssSelector("li.city-state-zip")).getText().contains(state));
		Assert.assertTrue(driver.findElement(By.cssSelector("li.city-state-zip")).getText().contains(postalCode));
		
		// Verify country
		Assert.assertEquals(driver.findElement(By.cssSelector("li.country")).getText(), country);
		
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
