package interview;

import java.util.Random;

public class Nqueens {
	
	private final boolean[] board;
	private final int n;
	public final int[] finalRow;
	public final int[] finalCol;

	
	
	
	public Nqueens(int n){
		this.board = new boolean[n*n];
		this.n = n;
		this.finalCol = new int[n];
		this.finalRow = new int[n];

	}
	
	
	private boolean canUseRow(int r){
		for(int element = r*n ; element < (r+1)*n; ++element){
			if(board[element])
				return false;
		}
		return true;
		
	}
	
	private boolean canUseColumn(int c){
		for(int element = c; element < (n*n); element += n){
			if(board[element])
				return false;
		}
		return true;
		
	}
	
	private boolean canUseD(int row, int col){
		
		int r = row; int c = col;
		
		while(r >= 0 && c >=0){
			if(board[n*r +c])
				return false;
			--r; --c;
		}
		
		r = row; c = col;
		
		while(r < n && c < n){
			if(board[n*r +c])
				return false;
			++r; ++c;
		}
		
		r = row; c = col;

		while(r >= 0 && c < n ){
			if(board[n*r +c])
				return false;
			--r; ++c;
		}
		
		r = row; c = col;
		
		while(r < n && c >= 0){
			if(board[n*r +c])
				return false;
			++r; --c;
		}
	
		
		return true;
		
	}
	
	private static void shuffle(int[] array)
	{
	    int index, temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
	
	public boolean solve(int col){

		if(col == n)
			return true;
		
		int[] array = new int[n];
		for(int i = 0; i < n; ++i)
			array[i] = i;
		
		shuffle(array);
		
		for(int tried = 0; tried < n; ++tried){
			int row = array[tried];
			if(canUseRow(row) && canUseD(row, col)){
				board[row*n + col] = true;
				if(solve(col+1)){
					
					finalCol[col] = col; finalRow[col] = row;
					return true;
				}
				board[row*n + col] = false; //UNDO
			}
			
		}
		return false;
		
	}
	
	
	public void printASolution(){
		
		if(!solve(0)){
			System.out.println("No Solution exists, try another n"); 
			return;
		}
		
		for(int i = 0; i < n; ++i)
			System.out.println("(" + (finalRow[i]+1) + " , " + (finalCol[i]+1) + ")");
			
	}
	

	
	
	
	
	
	
	
	
	

}
