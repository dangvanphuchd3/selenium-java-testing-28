package testng;

import org.testng.Assert;

public class Topic_01_Assert {

	public static void main(String[] args) {
		String fullName = "Automation Testing";
		
		// Mong đợi 1 điều kiện trả về là đúng (true)
		Assert.assertTrue(fullName.contains("Automation"));
		
		// Mong doi 1 dieu kien tra ve la sai (false)
		Assert.assertFalse(fullName.contains("Manual"));
		
		// Mong doi 2 dieu kien bang nhau
		// Expected Result
		// Actual Result
		Assert.assertEquals(fullName, "Automation Testing");
		//Assert

	}

}
