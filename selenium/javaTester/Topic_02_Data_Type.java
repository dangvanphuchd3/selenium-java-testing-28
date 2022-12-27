package javaTester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Topic_02_Data_Type {

	public static void main(String[] args) {
		//1. Kiểu dữ liệu nguyên thủy (Primitive)
		
		//1.1. Số nguyên: byte short int long (Không có phần thập phân)
		//Kích thước, độ rộng để lưu trữ dữ liệu
		byte bNumber = 127;
		
		short sNumber = 32000;
		
		int iNumber = 499233299;
		
		long lNumber = 83294;
		
		//1.2. Số thực: float double (Có phần thập phân)
		float studentPoint = 9.5f;
		
		double employeeSalary = 35.6d;
		
		//1.3. Logic: boolean
		boolean status = true; //Nam
		status = false; //Nữ
		
		//1.4. Ký tự: char
		char a = 'A';
		
		//2. Kiểu dữ liệu tham chiếu (Reference)
		//Class
		FirefoxDriver driver = new FirefoxDriver();
		
		//Interface
		WebElement firtNameTextbox;
		
		//String
		String firtName = "Automation Testing";
		
		//Object
		Object people;
		
		//Array
		String[] studentName = {"Nguyễn Văn An", "Lê Văn Hùng", "Nguyễn Thị Loan"};
		
		//Collection: List/Set/Queue
		List<WebElement> checkboxes = (List<WebElement>) driver.findElement(By.cssSelector(""));
		
		//Map
		Map<String, Integer> student = new HashMap<>();

	}

}
