package heap;

import java.util.ArrayList;

public class BinaryHeap<V> {
	
	private ArrayList<Cell> heap; 
	
	
	public BinaryHeap(int initialSize){
		heap = new ArrayList<Cell>(20);
	}
	
	public BinaryHeap(){ heap = new ArrayList<Cell>();}
	
	
	private int left(int index){
		return 2*index + 1;
	}
	
	private int right(int index){
		return 2*index + 2;
	}
	
	private int parent(int index){
		return (index-1)/2;
	}
	
	
	private class Cell{
		int key; V value;
		
		public Cell(int key, V value){
			this.key   = key;
			this.value = value;
		}
		
	}
	
	public int size(){
		return heap.size();
	}
	
	private void percolateUp(int index){
		
		if(index == 0)
			return;
	
		
		if(key(index) < pkey(index)){
			Cell oldParent = heap.get(parent(index));
			heap.set(parent(index), heap.get(index));
			heap.set(index, oldParent);
			percolateUp(parent(index));
		}
		
	}
	
	public void printHeap(){
		
		if(heap.isEmpty()){
			System.out.println("The heap is empty!");
			return;
		}
		
		
		for(Cell cell : heap)
			System.out.print("| " + cell.value + " ");
		System.out.println();
		
	}
	
	public void insert(int key, V value){
		int oldSize = heap.size();
		
		heap.add(new Cell(key,value));
		
		if(oldSize > 0) //It was not the first element
			percolateUp(oldSize);
		
	}
	
	public static void heapSort(Integer[] toSort){
		
		BinaryHeap<Integer> hp = new BinaryHeap<Integer>();

		for(Integer value : toSort)
				hp.insert(value, value);
		
		for(int v = 0; v < toSort.length; ++v){
			toSort[v] = hp.removeMin();
		}
	
		
	}
	
	public boolean isEmpty(){
		return heap.size() == 0;
	}
	
	public V getMin(){
		return heap.get(0).value;
	}
	
	public V removeMin(){
		V toReturn = heap.get(0).value;
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size() - 1);
		this.percolateDown(0);
		
		return toReturn;
	}
	
	public void percolateDown(int parent){
		int size = heap.size();
		Cell temp;

		while(2*parent + 2 < size){ //Both children must be there

			if(key(parent) > lkey(parent) || key(parent) > rkey(parent)){
				
				if(lkey(parent) <= rkey(parent)){

					temp = heap.get(parent);
					heap.set(parent, heap.get(left(parent)));
					heap.set(left(parent), temp);
					parent = left(parent);
					
				}
				else{
					temp = heap.get(parent);
					heap.set(parent, heap.get(right(parent)));
					heap.set(right(parent), temp);
					parent = right(parent);
					
				}
			}
			else
				return;
			
			
		}
		
		if(size == 2*parent + 2){
			if(key(parent) > lkey(parent) ){
				temp = heap.get(parent);
				heap.set(parent, heap.get(left(parent)));
				heap.set(left(parent), temp);
				
			}
		}
		
	}
	
	
	private int key(int index){
		return heap.get(index).key;
	}
	
	private int pkey(int index){
		return  heap.get((index -1) /2).key;

	}
	
	private int lkey(int index){
		return  heap.get(2*index + 1).key;

	}
	
	private int rkey(int index){
		return  heap.get(2*index + 2).key;

	}
	
	
	
	
	
	
	
	
	

}
