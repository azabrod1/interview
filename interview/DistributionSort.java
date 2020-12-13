package interview;

public class DistributionSort {

	private static final int INTER_MULT = 1; 
	
	public static void sort(int[] array){
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		for(int element : array){
			if(element < min)
				min = element;
			else if(element > max)
				max = element;
		}
		
		int interSize = Math.max(array.length * INTER_MULT,1);
		
		double scale = (double)interSize/(double)(max - min +1) ;
		
		//System.out.println(max*scale);
		
		//System.out.println(max - min);
		
		SortedLinkedList[] data = new SortedLinkedList[interSize];
		
		
		for(int element : array){
			int pos = (int) ((element - min) * scale);
			if(data[pos] == null)
				data[pos] = new SortedLinkedList(element);
			else
				data[pos].sortedInsert(element);
		
		}
		int curr = 0;
		
		for(int list = 0; list < data.length; ++list){
			if(data[list] != null){
				while(!data[list].isEmpty()){
					array[curr++] = data[list].pop();
					
				}
			}

		}

	}

	private static class SortedLinkedList{

		private Node head;
		
		public SortedLinkedList(int firstElement){
			this.head = new Node(firstElement, null);
		}
		

		//Assumes the LL is not null
		public int pop(){
			int toReturn = this.head.data;
			this.head    = this.head.next;
			return toReturn;

		}
		
		public boolean isEmpty(){
			return head == null;
		}
		
		public void sortedInsert(int insertMe){
		
			Node curr = this.head;
			
			while(curr.next != null && curr.next.data < insertMe)
				curr = curr.next;
			
			curr.next = new Node(insertMe, curr.next);

		}
		
		

		private class Node{
			final int data;
			Node next;

			public Node(int data, Node next){
				this.data = data; this.next = next;
			}
		}
	}
	
		
		
		
		
		
		
}
