package others;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class YearPuzzle {

	public static void main(String[] args) {
		double[] x = {1,1,3,8};
		YearChallenge.solve(x, 70, 72, 20);
		
	}
	
	
	static class Node
	{
		int data;
		Node next;
		
		Node(int d)
		{
			data = d;
			next = null;
		}
	}
	
	
	private static Node mergeResult(Node node1, Node node2)
    {
		Node toReturn = null;
        if(node1 == null){
            if(node2.next != null){
            	Node next = node2.next;
            	toReturn = mergeResult(null, node2.next);
            	next.next  = node2;
               
            }
            node2.next = null;
            return toReturn;
          }
        
        if(node2 == null){
            if(node1.next != null){
            	Node next = node1.next;
            	toReturn = mergeResult(null, node1.next);
            	next.next  = node1;
               
            }
            node1.next = null;
            return toReturn;
          }
        
      
        Node min, max;
        
        if(node1.data >= node2.data){
            max = node1; min = node2; 
        }
        
        else{
            min = node1; max = node2;
        }
        
        Node next = min.next == null? max : (min.next.data <= max.data)? min.next : max;
        toReturn = mergeResult(min.next, max);
        next.next = min;
        min.next = null;
        return toReturn;

    }
	
	long nChooseK(int N, int K) {
		
		Scanner io = new Scanner(System.in);
		
		
	    BigInteger toReturn = BigInteger.ONE;
	    for (int k = 0; k < K; k++) 
	        toReturn = toReturn.multiply(BigInteger.valueOf(N-k))
	                 .divide(BigInteger.valueOf(k+1));
	    
	    return toReturn.longValue();
	}
	
	
	
	
	

}
