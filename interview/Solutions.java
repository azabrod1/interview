package interview;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.ranges.RangeException;

public class Solutions {
	static int printed = 0;
	
	
	/**Function for computing x^n efficiently */
	public static long power(int x, int n){
		
		if(n == 1)
			return x;
		else if(n == 0)
			return 1;
		
		if((n & 1) == 0)
			return power(x*x, n/2);
		
		return x*power(x*x, n/2);		
		
	}
	
	public static int multiply(int x, int y){
		if(y == 0)
			return 0;
		int offset = 0;
		while(y != 1){
			if((y&1) == 1)
				offset += x;
			x += x;
			y /= 2;
		}
			
		
		return x + offset;
		
	}
	//x*x*x*...*x y times  , x^5  = (x^2)^2*x, x^6 = (x^2)^3 = (x^4)^1*x, x^6 = (x^2)^3 = x^4*
	public static long exp(int x, int y){
		if(y == 0)  return 1;
		if(x ==  0) return 0;
		
		long offset = 1; long base = x;
	
		while(y > 1){
			if((y & 1) == 1)
				offset *= base;
			base*=base;
			
			y /= 2;
		}
		
		
		return base*offset;
	}
	
	public static String longestSubString(String s1, String s2){
		
		short[][] counts = new short[s2.length()][s1.length()];
		char c2;
		
		c2 = s2.charAt(0);
		for(int c = 0; c < s1.length(); ++c)
			counts[0][c] = (short) (c2 == s1.charAt(c)? 1 : 0);
		
		
		for(int r = 1; r < s2.length(); ++r){
			c2 = s2.charAt(r);
			counts[r][0] = c2 == s1.charAt(0)? 1  : counts[r-1][0];
			for(int c = 1; c < s1.length(); ++c)
				counts[r][c] = (short) (c2 == s1.charAt(c)? counts[r-1][c-1] + 1 : (short) Math.max(counts[r-1][c], counts[r][c-1]));
		}
		if (counts[s2.length()-1][s1.length()-1] ==  0)
			return "";

		StringBuilder s = new StringBuilder();
		
		int r = s2.length()-1, c = s1.length() - 1;
		
	
		while(r != 0  && c != 0){
			if(s1.charAt(c)  ==  s2.charAt(r)){
				s.append(s1.charAt(c));
				--r; --c;
			}
			else if(counts[r-1][c]  >= counts[r][c-1])
				--r;
			else
				--c;
			
		}
				
		s.append(s1.charAt(c));
		
		return s.reverse().toString();
	
		
	}
	
	
	
	public static char firstNonRepeating(String s){
		int posChars = 1 << Character.SIZE;
		int [] duplicate = new int[posChars];
		
		for(int c = 0; c < s.length(); ++c)
			duplicate[s.charAt(c)]++;
			
		for(int c = 0; c < s.length(); ++c){
			char letter = s.charAt(c);
			if(duplicate[letter] == 1)
				return letter;
		}

		return 0;
	}
	
	public static int rand7(){
		Random random = new Random();
		int first5 = random.nextInt(5);
		int second5 = random.nextInt(5);

		int[][] result = { 
	        { 1, 2, 3, 4, 5 },
	        { 6, 7, 1, 2, 3 },
	        { 4, 5, 6, 7, 1 },
	        { 2, 3, 4, 5, 6 },
	        { 7, 0, 0, 0, 0 }
	    };
		
		if(result[first5][second5] != 0)
			return result[first5][second5] - 1;
		else 
			return rand7();
		

	}
	
	public static int nextLargest(int num){
		
		int[] list = new int[32];
		int size = 0;
		while(num != 0){
			list[size++] = num % 10;
			num /= 10;
		}
		if(size == 1)
			return num;
		
		int place = 0;
		
		while(list[place] <= list[++place]) { if(place >= size - 1) return -1;}
		
		int nextSmallest  = Integer.MAX_VALUE; int indexNextSmallest = -1;
		int intTOReplace = list[place];
		for(int i = 0; i < place; ++i){
			if(list[i] < nextSmallest && list[i] > intTOReplace){
				indexNextSmallest = i;
				nextSmallest = list[indexNextSmallest];
			}
			
		} 
		list[place] = nextSmallest;
		list[indexNextSmallest] = intTOReplace;
		
		for(int i = 0; i < place; ++i){
			int maxIndex = i; 
			for(int j = i; j < place; ++j){
				if(list[j] > list[maxIndex])
					maxIndex = j;
			}
			int temp = list[i];
			list[i] = list[maxIndex];
			list[maxIndex] = temp;
		}
		
		int toReturn = 0;
		
		for(int digit =0; digit < size; ++digit){
			toReturn += list[digit]* Math.pow(10, digit);
		}
			
		
		
		
		return toReturn;
	}
	
	public static void stackSort(Stack<Integer> s){
		if(s.size() < 2)
			return;
		
		Integer e = s.pop();
		if(s.empty()){
			s.push(e);
			return;
		}
		
		stackSort(s);
		putBack(s, e);
		
	}
	
	private static void putBack(Stack<Integer> s, Integer e){
		
		if(s.isEmpty() || s.peek() >= e){
			s.push(e); return;
		}
		Integer putBackLater = s.pop();
		putBack(s, e);
		s.push(putBackLater);
		
	}
	
	
	
	
	
	protected class Interval<T extends Comparable<T>> implements Comparable<Interval<T>>{

		public final T start, end;
		
		public Interval(T start, T end){
			this.start = start;
			this.end   = end;
		}
	

		@Override
		public int compareTo(Interval<T> o) {
			return start.compareTo(o.start);
		}
		
		
	}
	
	
	public static <T extends Comparable<T>> boolean overlaps(Interval<T>[] interval){
		
		Arrays.sort(interval);
		
		for(int i = 0; i < interval.length - 1; ++i){
			if(interval[i].end.compareTo(interval[i+1].start) > 0)
				return true;
			
		}
		
		return false;
		
	}
	/*Quick sort varient to find mth largest element*/
	
	public static int nth(int[] array, int n){
		if(n < 1 || n >= array.length )
			throw new RangeException((short) 0,"REQUIRED: 0<n<len(array)");
		
		--n;
		int start = 0;
		int end = array.length - 1;
		int p;
		
		while(true){
			p = partition2(array, start, end);
			if(p == n)
				return array[p];
			if(p < n)
				start = p + 1;
			else 
				end   = p - 1;
		}
		
	}

	public static int partition2(int[] array, int start, int end){

		int pivot = array[end];
		int utilityVar;
		int left = start; int right = end - 1;

		while(left <= right){
			if(array[left] <= pivot)
				++left;
			else{
				if(array[right] >= pivot)
					--right;
				else{
					utilityVar = array[right];
					array[right] = array[left];
					array[left] = utilityVar;
					++left;
				}
			}
		}

		array[end] = array[left];
		array[left] = pivot;
		return left;

	}
	
	public static String reversed(String str){
		
		if(str == "")
			return str;
		
		StringBuilder s = new StringBuilder();
		char candidate;
		int c = str.length() - 1;
		
		while(str.charAt(c) == ' '){--c; if(c == 0) return "";}
		
		while(c >= 0){
			candidate = str.charAt(c);
			if(candidate != ' ' || str.charAt(c-1) != ' ')
				s.append(candidate);
			--c;
		}
		
		
		System.out.println(s.toString());
		
		int start = 0; c = 0;
		
		StringBuilder toReturn = new StringBuilder();
		
		while(c < s.length()){
			if(s.charAt(c) != ' ')
				++c;
			else{
				for(int i = 1; i + start <= c; ++i){
					toReturn.append(s.charAt(c-i)); 
				} toReturn.append(' ');  c = start = c+1;
				
			}
		}
		
		for(int i = 1; i + start <= c; ++i){
			toReturn.append(s.charAt(c-i)); 
		} toReturn.append(' ');  c = start = c+1;
		
		return toReturn.toString();		
		
	}
	
	public static void countingSort(int[] array, int lo, int hi){
		
		int[] counts = new int[hi-lo+1];
		
		for(int i = 0; i < array.length; ++i)
			counts[array[i] - lo]++;
		
		int pos = 0;
		
		for(int i = 0; i < counts.length; ++i){
			if(counts[i] != 0)
				for(int j = 0; j < counts[i]; ++j)
					array[pos++] = i + lo;
			
		}

		
	}
	
	public static ArrayList<Integer> mergeA(ArrayList<Integer> a1, ArrayList<Integer> a2){
		
		HashSet<Integer> dups = new HashSet<Integer>(a1);
		int numD = 0;
		ArrayList<Integer> toReturn =  new ArrayList<Integer>(a1);
		
		for(int i = 0; i < a2.size(); ++i)
			if(dups.contains(a2.get(i)))
				++numD;
			else 
				toReturn.add(a2.get(i));
		
		System.out.println("duplicates: " + numD);
		
		return toReturn; 

		
	}
	
	public static int indexBSearch(List<Integer> list, int goalOffset){
		int mid, midVal, lo = 0, hi = list.size() - 1; 

		while(lo <= hi){

			mid = (hi + lo)/2; 
			midVal =  list.get(mid) - mid; 

			if(midVal == goalOffset)
				return mid;
			//If "too negative", take upper half 
			else if(midVal < goalOffset)
				lo = mid + 1;
			else
				hi =  mid - 1;

		}

		return -1;

	}
	
	public static boolean subsetSum(int[] nums, int sum){
		if(sum == 0) return true;
		
		boolean [][]  isSum = new boolean[nums.length][sum+1];
		
		//Empty set means sum can add to 0
		for (boolean[] row : isSum)
			row[0]  = true;
		
		isSum[0][nums[0]] = true;
		for(int r = 1;  r < nums.length; ++r)
			for(int c = 1; c <= sum; ++c)
				if(isSum[r-1][c] || (c >= nums[r] && isSum[r-1][c-nums[r]])) 
					isSum[r][c] = true;
			
			return isSum[nums.length-1][sum];
	}
	
	public static boolean sameSum(int[] set){
		int ttlSum = 0;
		for(int element: set)
			ttlSum += element;
				
		boolean[][] isSum = new boolean[set.length][ttlSum+1];
		
		isSum[0][ttlSum-set[0]] 					     = true; //Take element out of set 1 and don't place anywhere
		if(2*set[0] <= ttlSum) isSum[0][ttlSum-2*set[0]] = true; //Take element out of set 1 and place into set 2
		isSum[0][ttlSum] 							     = true; //Leave element in set 1
		
		
		for(int r = 1; r < set.length; ++r){
			int element = set[r];
			for(int c = 0; c < ttlSum+1; ++c)
				if(isSum[r-1][c]){
					isSum[r][c] = true;
					if(c >= element){
						isSum[r][c-element] = true;
						if(c >= element*2)
							isSum[r][c-2*element] = true;
					}
						
				}

		}
		int turns = 0; int c = 0;
		if(isSum[set.length-1][0])
			for(int r = set.length -2; r >= 0; --r)
				if(r != 0 && isSum[r][c])
					return true;
				
				else if((c+2*set[r+1] <= ttlSum) && isSum[r][c+2*set[r+1]]){
					System.out.println(r);
					return true;
				}
				else c+= set[r+1];
				

		
		return false;
	}
	
	public static void reverseWords(char[] string){
		int lo = 0, high;  char temp;
		
		while(lo < string.length){
			if(string[lo] == ' ') {++lo; continue;}
			high = lo;
			while(string[++high] != ' '){
				if(high == string.length-1) return;
			}
			for(int idx = 0; idx < (high - lo)/2; ++idx){
				temp = string[lo+idx];
				string[lo+idx] = string[high-1-idx];
				string[high-1-idx] = temp;
			}
			lo = high+1;
		}
		
		
		
		

	}
	
	
	public static int[] largest(int [] array, int n){
		int len = array.length;
		if(n < 1 || n > len )
			throw new RangeException((short) 0,"REQUIRED: 0<n<len(array)");
		
		int cutOff =  len - n;
		
		int start = 0;
		int end = len - 1;
		int p;
		
		while(true){
			p = partition2(array, start, end);
			if(p == cutOff){
				int[] answer = new int[n];
				while(n > 0){
					answer[n-1] = array[cutOff + n - 1];
					--n;
				}
				return answer;
			}
				
			if(p < cutOff)
				start = p + 1;
			else 
				end   = p - 1;
		}
		
	}
	
	public static long unboundedBS(int[] array, int key){
		
		try{
			if(array[0] == key)
				return 0;
		} catch(ArrayIndexOutOfBoundsException exception){
			return -1;
		}
		

		long upperBound = findUpperBound(array, key);

		if(upperBound < 0)
			return -upperBound;


		return modifiedBinary(array, key, (upperBound >> 1) + 1 , upperBound - 1);

	}

	private static long modifiedBinary(int[] array, int key, long lowerBound, long upperBound){

		if(lowerBound > upperBound)
			return -1;
		
		long middle = (upperBound+lowerBound)/2;
		int candidate;

		try{
			candidate = array[(int) middle];
		}catch(ArrayIndexOutOfBoundsException e){
			return modifiedBinary(array, key, lowerBound , middle - 1);
		}
		
		if(key == candidate)
			return middle;
		
		if(key < candidate)
			return modifiedBinary(array, key, lowerBound, middle - 1);
	
		return modifiedBinary(array, key, middle + 1, upperBound);
	}
	
	public static String filter(String original, String charsToCheck){
		
		StringBuilder result = new StringBuilder();
		charsToCheck.toLowerCase();
		
		Set<Character> banned = new HashSet<Character>();
		for(char ch : charsToCheck.toCharArray())
			banned.add(ch);
		
		
		for(char ch : original.toCharArray()){
			if(!banned.contains(Character.toLowerCase(ch)))
				result.append(ch);
		}
		
		return result.toString();
		
	}
	
	public static void permutation(String alphabet){
		ArrayList<Character> notUsed = new ArrayList<Character>();
		for(int i = 0; i < alphabet.length(); ++i)
			notUsed.add(alphabet.charAt(i));
		
		char[] soFar = new char[alphabet.length()];
		permute(notUsed, soFar);
		printed = 0;
	}
	
	//Use depth first search to save space
	private static void permute(ArrayList<Character> notUsed, char[] soFar){
		int charsLeft = notUsed.size();
		int nextIndex = soFar.length - charsLeft;
		
		if(charsLeft == 0){
			System.out.print(++printed + ". ");
			for(char ch : soFar)
				System.out.print(ch);
			System.out.println();
		}
		
		for(int i = 0; i < charsLeft; ++i){
			Character toInsert = notUsed.remove(i);
			soFar[nextIndex] = toInsert;
			permute(notUsed, soFar);
			notUsed.add(i, toInsert);
		}
		
	
		
	}
	
	public static long powers(long x, long n){
		
		if(n == 0)
			return 1;
		else if(n == 1)
			return x;
		
		long op = powers(x, n/2);
		
		return op* op *  (n%2 == 0? 1 : x);
	
	}
	
	
	
	
	
	
	
	
	public static String reverseWords(String original){
		if(original == null || original == "")
			return original;
	
		int right = original.length() - 1; int curr = right; char candidate;
		int left;
		
		char [] transform = original.toCharArray();
		
		boolean inWord = false;
		
		while(curr >= 0){
			candidate = transform[curr];
			if(inWord){
				if(!Character.isLetterOrDigit(candidate)){
					left = curr + 1;
					while(left < right){
						candidate = transform[left];
						transform[left++] = transform[right];
						transform[right--] = candidate;	
					} transform[curr] = ' '; inWord = false; //DO WE NEED??
				}
			} 
			else{
				if(Character.isLetterOrDigit(candidate)){ //First letter of a word
					right = curr; inWord = true;
				} 

			}
			--curr;
		}

		if(inWord){
			left = 0;
			do{
				candidate = transform[left];
				transform[left++] = transform[right];
				transform[right--] = candidate;	

			}while(left < right);
		}
		
		return String.copyValueOf(transform);
		
	}
	
	 public static String MergeStrings(String[] strings) {
		 //Populate array of "counts"; how many time each variable appears
		 //This way it is already sorted. A similar approach would be to use a java treemap
		 //to keep counts. insertions/updates would be a bit slower but it would take
		 //less memory in the case of very few short strings. But i think optimizing for
		 //long inputs and many strings is more important
		 int[] char_count = new int [26]; 
		 
		 for(String str: strings)
			for(int ch = 0; ch < str.length(); ++ch)
				if(str.charAt(ch) > 96 && str.charAt(ch) < 96+26 )
					char_count[str.charAt(ch) - 97]++;
		 
		//Very important to use buffered string builder, string concats are expensive !!
		 StringBuilder sb = new StringBuilder(); 
		 
		 for(int ch = 0; ch < 26; ++ch){
			 for(int i = 0; i < char_count[ch]; ++i)
				 sb.append((char) (ch+97));
			 
		 }
		 Arrays.sort(strings, (String a, String b) -> {
			 if(a.length() != b.length())
				 return a.length() - b.length();
			 
			 return a.compareTo(b);
		 }
		
		);
		 
		 List<String> s = Arrays.asList(strings);
		 
		 
		 
		 return sb.toString();
	  }
	
	
	
	 public void maxStack(){
		 Scanner sc = new Scanner(System.in);
		    int n = sc.nextInt();
		    int max = Integer.MIN_VALUE;
		    Stack<Integer> stack = new Stack<Integer>();
		    Stack<Integer> maxStack = new Stack<Integer>();


		    while (n > 0) {
		        int choice = sc.nextInt();
		        if(choice == 1) {
		            int val = sc.nextInt();
		       
		            stack.add(val); 
		            if(val >= max)
		                maxStack.add(val);
		            
		        } else if(choice == 2) {
		          
		            int top = stack.peek();
		            
		            
		            
		            
		        } else if(choice == 3) {
		            if(!stack.isEmpty()) {
		            }
		        }

		        n--;
		    }
		    sc.close();    
	 }
	 
	 static int count_palindromes(int[] elements, int target) {
	       int left = 0, right = elements.length-1;
	       while(left<right){
	    	   int middle = (left+right+1)/2;
	    	   if(elements[middle] > target)
	    		   right = middle -1;
	    	   else
	    		   left = middle +1;
	       }
	       
	        if(elements[right] == target)
	        	return right;
	        return -1;
	      
	    }
	 static void sports1(){
	 Scanner scanner = new Scanner(System.in);
     
     while (scanner.hasNextLine()){
          String line = scanner.nextLine();
           Pattern p = Pattern.compile("\\[(.*?)\\]");
           Matcher m = p.matcher(line);

             while(m.find()) {
             System.out.println(m.group(1));
             }
             
       //  System.out.println(line);
         
         
         
     }

     scanner.close();
	 }
	 
	 public static void testJobs(){
		 
		 Job[] jobs = {new Job("A", 3, 5), new Job("B", 1, 1), new Job("B", 3, 4) , new Job("B", 3, 8), new Job("B", 2, 5), new Job("B", 5, 5)};
		 
		 
	 }
	 
	 
	 
	 
	 public static class Job{
		 String name;
		 int deadline, pay;
		 public Job(String _name, int _deadline, int _pay){
			 this.name = _name; this.deadline = _deadline; this.pay = _pay;
		 }
		 
		 public static Comparator<Job> payComp = new Comparator<Job>(){

			@Override
			public int compare(Job o1, Job o2) {
				return Integer.compare(o1.pay, o2.pay);
			}
			 
		 };
	 }
	 
	 
	 public static int bestSchedule(Job[] jobs){
		 
		 Arrays.sort(jobs, Job.payComp);
		 
		 
		 
	
		 
		 return -2;
	 }
	 
	 
		
	
	public static double entropy(double[] probs){
		
		double entropy = 0;
		
		for(double prob : probs){
			entropy -= prob * Math.log(prob)/Math.log(2);
			
		}
		
		return entropy;
	}
	
	/**Question: Given array of numbers 1 to N with 2 of them missing, find the two missing numbers*/

	public static int[] twoMissing(int[] array){
		
		int N = array.length + 2; 
		
		int arraySum = 0;
		
		for(int element : array) arraySum += element;
		
			
		//Find the sum and average of the missing elements
		int sumMissing = (N*(N+1)/2 - arraySum); 
		int avgMissing = sumMissing /2;

		
		/*Let the average of the missing elements be X. Split up the list into a list of elements from 1 to X
		 * and a list of elements from X to N. We know only one of the missing elements must be in first list.
		 * So now we have simplified our problem to the case of only one missing element. Find the missing smaller element and then
		 * find the larger one by computing sumMissing - smallerElement
		 */
	
		int sumLowerHalf = 0; 

		
		for(int element: array){
			if(element <= avgMissing)
				sumLowerHalf += element;  	
		}
		
		int firstNum = avgMissing * (avgMissing+1)/2 - sumLowerHalf;
		
		int[] result = {firstNum, sumMissing - firstNum};
		
		return result;
		
	}
	
	
	
	
	
	/*Binary search on array with upper bound upper and lower bound lower*/
	private static long findUpperBound(int[] array, int key){
		
		long curr = 1;
		int candidate;
		
		do{
			try{
				candidate = array[(int) curr];
				if(key == candidate) //Return negative to inform caller that this is index of key and we can stop
					return -curr;
				if(key < candidate) //Now we have an upper bound 
					return curr;
				else
					curr = curr << 1;
				
			}catch(ArrayIndexOutOfBoundsException e){ //This will also give us an upper bound 
				return curr;
			}
			
		}while(true);
		
	}
	
	public static void eggFloors(final int F){
		
		if(F < 1){
			System.err.println("The number of floors should be positive");
			
		}

		double _step = (-1 +  Math.sqrt(1+(8*(F))))/2;

		int step = (int) Math.ceil(_step);  
		
		List<Integer> floors = new ArrayList<Integer>();
		
		int floor = step;
		
		while(floor < F){
			
			floors.add(floor);
			floor += --step;
		}
		
		floors.add(F);
		
		System.out.println(floors.toString());
		
		eggDrops(floors.toArray(new Integer[floors.size()]));

	}
	
	
	private static void eggDrops(Integer[] checkPoints){
		
		Scanner io = new Scanner(System.in);
		int threshold, floor, step,  comparisons;
		
		System.out.print("Enter threshold: ");
		threshold = io.nextInt();
	
		while(threshold > 0){	
		
			comparisons = 0;
			for(step = 0; step < checkPoints.length; ++step){
				++comparisons;
				if(checkPoints[step] >= threshold)
					break;
			}
			
			//if(step == checkPoints.length)
			//	floor = checkPoints[checkPoints.length-1];
			
			//else{
				
				floor = (step == 0)? 1 : (checkPoints[step - 1] + 1);
				
				while(floor < checkPoints[step]){ ++comparisons;
					if(floor >= threshold)
						break;
					else ++floor;
				}
						
		//	}
				
	   System.out.printf("The threshold floor is %d\n", floor);
	   System.err.println("comparisons:   " + comparisons);

	   System.out.print("Enter threshold: ");
		threshold = io.nextInt();
		}
		io.close();
		System.out.println("Exitting...");
			
	}


	
	public static int[] twoSum(int[] array, int goalSum){
		
		HashSet<Integer>  entries = new HashSet<Integer>();
		
		for(int entry: array){
			if(entries.contains(goalSum - entry))
				return new int[] {entry, goalSum - entry};
			else
				entries.add(entry);
		}
		
		return null;
	}
	
	public static int[] twoSum2(int[] array, int goal){
		
		int i = 0, j = array.length - 1;
		
		while(i <= j){
			int sum = array[i] + array[j];
			if(sum == goal) return new int[] {array[i], array[j]};
			else if(sum > goal) --j;
			else ++i;
		}
	

		return null;
	}
	
	
	public static double sqrt(double num, double delta){
		
		if(num < 0) return -1;

		double curr = num/3;
		
		double error = curr*curr - num;
		
		int guesses = 0;
		
		while(error > delta || error < -delta){
			
			curr = curr - (error)/(2*curr);
			
			error = curr*curr - num;
			
			++guesses;
			
		}
		
		System.err.println("Guesses: " + guesses);
		return curr;
		
	}
	
	//x^15 = ((x^2)^7)*x = 
	public static double exponent(double num, int exp){
		
		double carryOver = 1;
		while(exp > 1){
			if((exp & 1) == 1)
				carryOver *= num;
			num *= num;
			exp /= 2;
			
		
		}
		
		
		return num*carryOver;
		
	}
	
	public static double sqrt(double num){
		
		return sqrt(num, 0.0001);
		
	}
	
	public static double nthRoot(double x, int n, double delta){
		
		if(x < 0 || n <= 0)  return -1;
		double guess = x /n;
		double error = Math.abs(exponent(guess, n) - x);
		
		while(error > delta){
			guess  -= error/(n*exponent(guess, n-1));
			error = Math.abs(exponent(guess, n) - x);
		}
		
		
		return guess;
		
	}
	
	public static int[] divide(int n, int d){
		int result = 0; int curr = 1;
		
		while(n > d){
			d <<= 1;
			curr <<= 1;
		}
		
		while(curr > 0){
			if(n >= d){
				n-=d;
				result += curr;
			}
			d >>= 1;
			curr >>= 1;
		}
		
			
		return new int[]{result, n};
		
	}
	
	
	public static String sortWords(String sentence){
				
		String[] words = sentence.split(" ");
		
		Arrays.sort(words, (String x, String y) -> x.compareToIgnoreCase(y));
		
		StringBuilder sb = new StringBuilder();
		for(String word : words)
			sb.append(word + ' ');
		
		
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	public static Deque<Integer> stackSort(Deque<Integer> stack1){
		
		if(stack1.isEmpty()) return new ArrayDeque<Integer>();
		
		Deque<Integer> stack2 = new ArrayDeque<Integer>(stack1.size());
		
		stack2.push(stack1.pop());
		
		int curr;
		
		while(!stack1.isEmpty()){
			curr = stack1.pop();
			if(curr < stack2.peek())
				stack2.push(curr);
			else{
				//Put elements smaller than curr back to stack1
				int moves = 0;
				while(!stack2.isEmpty() && stack2.peek() < curr){
					stack1.push(stack2.pop());
					++moves;
				}
				
				stack2.push(curr);
				
				for(;moves > 0; --moves)
					stack2.push(stack1.pop());
			
			}
			
		}
		
		
		/*
		10000
		123
		43
		355
		
		122
		1231
		2000
		12313   
		*/

		
		
		return stack2;
	}
	
	
	public static void mergeSort(int[] array){
		if(array == null || array.length < 2) return;
		
		int[][] arrays  = {array, Arrays.copyOf(array, array.length)}; 
				
		_sort(arrays, 0, array.length, true);

	}
	
	private static void _sort(int[][] arrays, int l, int h, boolean prim){
		
		if(h == l+1) return;
		
		int m = (h + l)/2;
		
		_sort(arrays, l, m, !prim); _sort(arrays, m, h, !prim); 
		
		int[] into,  from;
		
		if(prim){
			into = arrays[0]; from = arrays[1];
		}
		else {
			into = arrays[1]; from = arrays[0];}

		
		int x = l; int y = m; int index = l;
		
		while(x < m && y < h)
			into[index++]  = (from[x] <= from[y])? from[x++]  : from[y++];
			
	    while(x < m)
	    	into[index++] = from[x++];
	    
	    while(y < h)
	    	into[index++] = from[y++];
	  
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


