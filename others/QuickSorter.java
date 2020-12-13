package others;

public class QuickSorter extends Sorter {

	@Override
	public void sort(int[] array) {
		
		quicksort(array, 0, array.length-1);
		
	}
	
	private void quicksort(int[] array, int lo, int hi){
		if(lo >= hi) return;
		
		int p = partition(array,  lo, hi);
		quicksort(array, lo, p-1); quicksort(array, p+1, hi);
		
	}
	
	private int partition(int[] array, int lo, int hi){
		
		int pValue = array[hi];
		int l = lo, r = hi-1;
		
		while(l < r){

			if(array[l] <= pValue)  ++l;
			
			
			else if(array[r] >= pValue) --r;
			
			else{
				final int leftTemp = array[l]; 
				array[l++] = array[r];
				array[r--] = leftTemp;
			}
			
			
		}
		
		if(pValue <= array[l]){
			array[hi] = array[l];
			array[l] = pValue;
		}
		
		else{
			array[hi] = array[l+1];
			array[l+1] = pValue;
		}
		
		return l;
		
	}

}
