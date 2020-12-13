package interview;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Strings {


	public static String common(String A, String B){

		int[][] counts = new int[A.length()][B.length()];

		char firstLetter = A.charAt(0);
		for(int b = 0; b < B.length(); ++b)
			counts[0][b] = (firstLetter == B.charAt(b))? 1 : 0;

		firstLetter = B.charAt(0);
		for(int a = 0; a < A.length(); ++a)
			counts[a][0] = (firstLetter == A.charAt(a))? 1 : 0;


		int maxLen = 0;
		int a_end = -1, b_end = -1;

		for(int a = 1; a < A.length(); ++a){
			for(int b = 1; b < B.length(); ++b){

				//Do not need to initialize to zero in java since java arrays start at 0.

				if(A.charAt(a) == B.charAt(b)){
					counts[a][b] = counts[a-1][b-1] + 1;
					if(counts[a][b] > maxLen){
						maxLen = counts[a][b];
						a_end = a; b_end = b;
					}	
				}
			}
		}
		
		if(maxLen == 0) return ""; 
		
		StringBuilder toReturn = new StringBuilder();
		
		for(int c = 0; c < maxLen; ++c)
			toReturn.append(A.charAt(a_end--));
		
		return toReturn.reverse().toString();
		
	}
	
	
	public static char firstNonRepeating(String string){
		
		if(string == null || string == "") return 0;
		LinkedHashMap<Character, Boolean> map = new LinkedHashMap<Character, Boolean>();
		
		for(char c : string.toCharArray())
			if(!map.containsKey(c))
				map.put(c, false);
			else map.put(c, true);
		
		Iterator<Map.Entry<Character, Boolean>> it = map.entrySet().iterator();

		while(it.hasNext()){
		    Map.Entry<Character, Boolean> pair = it.next();
		    if(!pair.getValue()) return pair.getKey();
			
		}
		return 0;
		
		
		
	}
	

	
	
	
}
