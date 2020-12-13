package others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Arithmetic {
	private final double[] list;
	private final Random random; //We will be using this a lot
	private final int ttlNums;
	private int  index;
	private ArrayList<Character> answer = new ArrayList<Character>();
	
	
	private Arithmetic(double[] variables, double desiredNum){
		this.list       = variables;
		this.random     = new Random();
		this.ttlNums    = list.length;
		this.index      = 0;
		
		}
	

	public static void solve(double[] variables, double desiredNum){
		Arithmetic solver = new Arithmetic(variables, desiredNum);
		
		int _shuffle = 0;
		double calc = solver.group(0);
		
		while(Math.abs(calc - desiredNum) > 0.01 || Double.isNaN(calc)){
			if((_shuffle++ & 31) == 0) //Shuffle numbers around every 16 times
				solver.shuffle();

			solver.reset();
			calc = solver.group(0);
			
		}
		System.out.print((int )desiredNum +" = ");
		
		for(Character ch : solver.answer){
			System.out.print(ch);
		}
		
		System.out.print("\n\nGuesses needed: " + _shuffle); 

		
	
		System.out.println("\n_______________________________________________________________");

		
	}
	
	private void reset(){
		index = 0; answer.clear();
	}
	
	

	/** We are given a set of positive integers S =  {A,B, C...} that we must use
	 *  in a sequence of operations like A-(C/D)^(B) where the result of this sequence of operations must equal some
	 *  number the user wants
	 *  
	 *  We will generate random strings of operations until we have one that yields the desired result
	 *  
	 *  
	 *  We extend the set {A,B,C,D,...} with {A, -A, B, -B, C, -C, D, -D...} to avoid subtraction, negative exponents and so on
	 *  
	 *  Now we have S = {A, -A, B, -B, C, -C, D, -D...}
	 *  
	 *  To factor in the fact the we can use decimal versions (i.e for X the decimal version is 0.X)
	 *  We extend our set so that we have S = {A, -A, 0.A,-0.A, B, -B, 0.B, -0.B, C, -C, 0.C, -0.C, D, -D, 0.D, -0.D}
	 *  
	 *  To simulate this, Each time we choose a number out of the original set S, 
	 *  We make it negative with some probability p <= 1/2. We make the number a decimal with some probability 
	 *  q << 1/2. We make sure p is less than 1/2 because we want more numbers to be positive than negative on average
	 *  since those are more likely to lead us to the right result. 
	 *  Similarly q should be even smaller because we decimals are not likely to be useful
	 *  too often
	 *  
	 *  
	 *  
	 *  
	 * In the beginning of a sequence, we can have a parenthesis ( or a number #. 
	 * After a number we can have: a closing of a previous parenthesis ')' or an binary operation +, /, *
	 * After a binary operator (i.e. +),    we must have a number or open parenthesis i.e. 5+5 or 5 + (5+2)
	 * After an open parenthesis( , we can have only a number i.e. (7 
	 * After a close parenthesis ), we allow the unitary operation like factorial ! or ^ or a binary operation
	 * 
	 * Note that our set of rules is more limited than real life, for example
	 * we cant have 5! but that is okay because we can have (5)! which is the same thing
	 * Similarly, we can't have 5(7) but that is okay because we can have 5*7
	 * 
	 * We use recursion to simulate "going inside a parenthesis" and we return from the recursive call once the parenthesis is closed
	 * 
	 * For simplicity, we assume the entire sequence of operations is inside parenthesis i.e. (5+3) = 5+3
	 * The depth of the recursion  is equal to the number of right ( parenthesis we are currently in.
	 * 
	 * We start at the beginning of the sequence ( and add numbers/parenthesis and operations based on rules above and probabilities
	 * What we do depends on the last thing we placed in the sequence
	 * 
	 * (   ---->  number, possibly negative with probability p and/or decimal with probability q
	 * #   ----> ) Parenthesis if an open one exists or +,-,/ 
	 * )  -> operations of any kind: +,*,^,! or another )
	 * !  -> operations of any kind: +,*,! follow a unitary operator like ! or ^ )
	 * +,* ->  open parenthesis i.e #+(4+2)  or #+5
	 * 
	 */
	


	private double group(int depth){
		double result  = 0;
		answer.add('(');
	    
		int temp;
		int r = random.nextInt(1024);//Random number we will be using
		double bundle;
		
		if((r&7) == 0) //Start by Recursing down
			bundle = group(depth + 1);

		else{//Start bundle with a number
		
			
			bundle = list[index];
			if(bundle == 0 && (r&1) == 0)
				if(r < 300){
					bundle = -1;  answer.add('-'); answer.add('('); answer.add('0'); answer.add('!'); answer.add(')');
					
				} else{
					bundle = 1; answer.add('0'); answer.add('!');
				}
			
			else{
				
				
				if((r < 430)){
					bundle = - bundle;
					answer.add('-');
				}

				if((r % 11) == 0){
					bundle *= 0.1;
					answer.add('.');
				}

				else if(r % 17 == 0 && ttlNums > index + 1 ){ //Concat
					bundle =(r < 430)? -(Math.abs(list[index+1]) + Math.abs(bundle*10)): Math.abs(list[index+1]) + Math.abs(bundle*10);
					answer.add((char) (list[index++] + 48));
				}
				
				answer.add((char) (list[index] + 48));

			}++index;
		}

		while(true){
			
			r = random.nextInt(1024);

			//if(result + bundle==79) System.out.println("dd");
			
			//Close parenthesis if we ran out of numbers or we have an even random and depth > 0 (we have parenthesis to close)
			
			if(index == ttlNums || ((r&1) == 0 && depth > 0))
				break;

			else{ //Binary op 
				r  = r >> 1;

				temp = r & 7;
				if(temp < 3){ 
					//By PEMDAS, we add the current bundle to the result since an addition sign separates two sides
					result += bundle;
					answer.add('+');
					bundle  = group(depth + 1);
				}
				else if(temp < 6){
					answer.add('*');

					bundle *= group(depth + 1);

				}
				else{
					answer.add('/');
					bundle /= group(depth + 1);
				}

			}


		};

		result += bundle;

		//Apply op an to the result before returning with 25% chance. If it is applied, can apply another
		//with probability 25% again and so on
		answer.add(')');

		r = r >> 3;
			
		while((r & 3) == 0) { // 25% chance
			r = random.nextInt(16);

			if(r < 7){ //Factorial
				if((result > -0.001 && (Math.abs(result - (int) result)) < 0.0001) && result < 7){ //Result must be integer to do factorial 
					result = fact(result);	answer.add('!');
				}

			}
			else if(r < 11){
				if((result > 0 && (Math.abs(result - (int) result)) < 0.0001) && result < 9){ //Result must be integer to do factorial 
					result = doubleFact(result); answer.add('!'); answer.add('!');
				}
			}
			else if(r < 13){
				if(result > -0.001){
					result = Math.sqrt(result); answer.add('^'); answer.add('('); answer.add('.'); answer.add('5'); answer.add(')');
				}

			}
			else{ //Power
				if(index < ttlNums){ 
					answer.add('^');
					result = Math.pow(result, group(depth + 1));	
				}
			}
			random.nextInt(4);
		}
		return result; //Close parenthesis by returning the result

	}


	public void shuffle()
	{
	    double temp;
	    int    index;
	    for (int i = list.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = list[index];
	        list[index] = list[i];
	        list[i] = temp;
	    }
	}
	
	
	private static double fact(double x){
		
		int y = (int) (x+0.5);
		
		if(y == 0 || y == 1)
			return 1;
		if(y == 2)
			return 2;
		if(y == 3)
			return 6;
		if(y == 4)
			return 24;
		if(y == 5)
			return 125;
		if(y == 6)
			return 720;
		
		System.err.println("Error");
		return -4343;

	
	}
	
	private static double doubleFact(double x){
		
		int y = (int) (x+0.5);
		
		switch(y){
		
		case 0:
		case 1: return 1;
		case 2: return 2;
		case 3: return 3;
		case 4: return 8;
		case 5: return 15;
		case 6: return 48;
		case 7: return 105;
		case 8: return 384;
		default:
			System.err.println("Double factorial getting incorrect inputs!!");

		}
		return 1000;
	
	}
	
}
