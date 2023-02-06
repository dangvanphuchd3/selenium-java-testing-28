package javaTester;

import java.util.Random;

public class Topic_05_Random {

	public static void main(String[] args) {
		// utilities = tiện ích
		// Data Type: Class/ Interface/ Collection/ String/ Float/..
		Random rand = new Random();
		System.out.println(rand.nextFloat());
		System.out.println(rand.nextDouble());
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextLong());
		
		System.out.println("automation" + rand.nextInt(99999)+"@gmail.com");
	}

}
