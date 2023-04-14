package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_19_Javascript_Executor {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;

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
	public void TC_01_Tech_Panda() {
		// Step 01: Truy cập vào trang
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(5);
		
		// Step 02: Sử dụng JE để get domain của page
		// Verify domain = live.techpanda.org
		Assert.assertEquals(getDomainName(), "live.techpanda.org");
		
		// Step 03: Sử dụng JE để get URL của page
		Assert.assertEquals(executeForBrowser("return document.URL;"), "http://live.techpanda.org/");
		
		// Step 04: Open MOBILE page (Sử dụng JE)
		// Highlight vào text
		hightlightElement("//a[text()='Mobile']");
		
		// Click vào text Mobile
		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(3);
		
		// Step 05: Add sản phẩm SAMSUNG GALAXY vào Cart (Click vào button ADD TO CART bằng JE)
		// Highlight button ADD TO CART
		hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
		
		// Click button ADD TO CART
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
		sleepInSecond(5);
		
		// Step 06: Verify message được hiển thị: Samsung Galaxy was added to your shopping cart. (Sử dụng JE - Get inner text)
		Assert.assertTrue(getInnerText().contains("Samsung Galaxy was added to your shopping cart."));
		
		// Step 07: Open Customer Service page (Sử dụng JE)
		// Verify title của page = Customer Service (Sử dụng JE)
		// Highlight link Customer Service
		hightlightElement("//a[text()='Customer Service']");
		
		// Click link Customer Service
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(5);
		
		// Verify title của page
		Assert.assertEquals(getTitlePage(), "Customer Service");
		
		// Step 08: Scroll tới element Newsletter textbox nằm ở cuối page (Sử dụng JE)
		scrollToElementOnDown("//input[@id='newsletter']");
		hightlightElement("//input[@id='newsletter']");
		
		// Step 09: Input Email hợp lên vào Newsletter textbox (Sử dụng JE)
		sendkeyToElementByJS("//input[@id='newsletter']", "automationfc" + getRandomNumber() + "@gmail.com");
		
		// Step 10: Click vào Subcribe button (Sử dụng JE)
		hightlightElement("//button[@title='Subscribe']");
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(5);
		
		// Step 11: Verify text có hiển thị (Sử dụng JE - Get inner text)
		// Thank you for your subscription
		Assert.assertTrue(getInnerText().contains("Thank you for your subscription."));
		
		// Step 12: Navigate tới domain https://demo.guru.com/v4/ (Sử dụng JE)
		// Verify domain sau khi navigate = demo.guru99.com
		navigateToUrlByJS("https://demo.guru99.com/v4/");
		sleepInSecond(5);
		
		Assert.assertEquals(getDomainName(), "demo.guru99.com");
	}

	@Test
	public void TC_02_HTML5_Vlidation_Message() {
		// Khai báo biến
		String firstName = "//input[@id='firstname']";
		String surName = "//input[@id='surname']";
		String emailAddress = "//label[@for='surname']/parent::div/following-sibling::div/div/input[@id='email']";
		String password = "//label[@for='surname']/parent::div/following-sibling::div/div/input[@id='password']";
		String confirmPassword = "//input[@id='password-confirm']";
		String buttonRegister = "//button[contains(text(),'Register')]";
		
		// Step 01: Access vào trang [https://warranty.rode.com/]
		navigateToUrlByJS("https://warranty.rode.com/");
		sleepInSecond(3);
		
		// Step 02: Click Register và verify message hiển thị tại field First Name textbox
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(getElementValidationMessage(firstName), "Please fill out this field.");
		
		// Step 03: Input dữ liệu vào file First Name và click Register - Verify messag tại filed Surname textbox
		sendkeyToElementByJS(firstName, "Phuc");
		
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(getElementValidationMessage(surName), "Please fill out this field.");
		
		// Step 04: Input dữ liệu vào file Surname và click Register - Verify messag tại filed E-Mail Address textbox
		sendkeyToElementByJS(surName, "DV");
		
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(getElementValidationMessage(emailAddress), "Please fill out this field.");
		
		// Step 05: Input sai dữ liệu vào file E-Mail Address và click Register - Verify messag tại filed E-Mail Address textbox
		sendkeyToElementByJS(emailAddress, "phucdv");
		
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(getElementValidationMessage(emailAddress), "Please enter an email address.");
		
		// Step 06: Input dữ liệu vào file E-Mail Address và click Register - Verify messag tại filed Password textbox
		sendkeyToElementByJS(emailAddress, "phucdv@gmail.com");
		
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(getElementValidationMessage(password), "Please fill out this field.");
		
		// Step 07: Input dữ liệu vào file Password và click Register - Verify messag tại filed Confirm Password textbox
		sendkeyToElementByJS(password, "12345678");
		
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(getElementValidationMessage(confirmPassword), "Please fill out this field.");
		
		// Step 08: Input dữ liệu vào file Confirm Password không matching với Password và click Register - Verify messag tại filed Password textbox
		sendkeyToElementByJS(confirmPassword, "123456789");
		
		clickToElementByJS(buttonRegister);
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.xpath("//input[@autocomplete='new-password']/following-sibling::span/strong")).getText(), "The password confirmation does not match.");
	}

	public int getRandomNumber() {
		return new Random().nextInt(9999);
	}
	
	public String getTitlePage() {
		return (String) jsExecutor.executeScript("return document.getElementsByTagName('title')[0].innerHTML;"); 
	}
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}
	
	public String getDomainName() {
		return (String) jsExecutor.executeScript("return document.domain;");
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
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
