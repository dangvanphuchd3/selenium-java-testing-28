package javaTester;

public class Topic_06_String {

	public static void main(String[] args) {
		String url = "http://the-internet.herokuapp.com/basic_auth";
		
		String username = "admin";
		String password = "admin";
		
		System.out.println(url);
		
		String [] arrayUrl = url.split("//");
		// 1 - http:
		// 2 - the-internet.herokuapp.com/basic_auth
		
		url = arrayUrl[0] + "//" + username + ":" + password + "@" + arrayUrl[1];
		
		System.out.println(url);
	}

}
