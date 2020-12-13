package Misc;

import java.util.Arrays;
import java.util.Set;

public class MiscMain {

	public static void main(String[] args) {

		int[] set = {1,4,10,3};
		System.out.println(minDif(set));
		
		
		Animal rabbit = new Hare();
		System.out.println(rabbit.getClass().toString());
		rabbit = null;
		 Runtime.getRuntime().gc();
	
	}
	
	private static int minDif(int[] set){
		
		int sum = Arrays.stream(set).sum();
		
		boolean [][] dp = new boolean[set.length][sum/2+1];
		
		for( boolean[] arr: dp) arr[0] = true;
		
		dp[0][set[0]] = true;
		
		for(int e = 1; e < set.length; ++e)
			for(int n = 0; n <= sum/2; n++)
				 dp[e][n] = (dp[e-1][n]) || (set[e] <= n && dp[e-1][n-set[e]]);
				
				
		for(int n = sum/2; n  > 0; --n)
			if(dp[set.length-1][n]) return Math.abs((sum-n) - n);
		
		return 0;
	}
	

}
// finalize method is called on object once 
// before garbage collecting it

abstract class Animal{
	String name;
	
	String getName(){
		return name;
	}
}

class Hare extends Animal{
	Hare(){
		name = "hare";
	}
	
	protected void finalize() throws Throwable
	{
	    System.out.println("Garbage collector called");
	    System.out.println("Object garbage collected : " + this);
	}
}