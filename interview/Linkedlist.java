package interview;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Linkedlist<T> {
	private Node head;
	private int size;
	
	public Linkedlist(){}
	
	public Linkedlist(T[] init){
		if(init.length == 0)  return;
		head = new Node(init[0]); Node curr = head;
		for(int i = 1; i <  init.length; ++i){
			curr.next = new Node(init[i]);
			curr = curr.next;
		}
		
		this.size = init.length;
			
	}
	
	private static class ListCounter<T>{
		public ListCounter(Linkedlist<T>.Node  _node){this.node = _node;}
		Linkedlist<T>.Node node;
		public Linkedlist<T>.Node increment(){
			Linkedlist<T>.Node toReturn = this.node;
			this.node = node.next; 
			return toReturn;
		}
	}
	
	public static <T> BinarySearchTree<T> sortedToTree(Linkedlist<T> list){

		BinarySearchTree<T> tree = new BinarySearchTree<T>();
		
		ListCounter<T> listIt = new ListCounter<T>(list.head);
	
		return new BinarySearchTree<T>(makeTree(listIt, tree, list.size));
		
	}
	
	
	private static <T> BinarySearchTree<T>.Node makeTree(ListCounter<T> list, BinarySearchTree<T> tree , int n){

		if(n <= 0) return null;
		
		
		BinarySearchTree<T>.Node left = makeTree(list, tree, n/2);
		
		Linkedlist<T>.Node listNode	  = list.increment();
		BinarySearchTree<T>.Node root = tree.getNewNode(listNode.item);
		
		root.left = left;
		
		root.right = makeTree(list, tree, n - n / 2 - 1);
		

		return root;
	}
	
	public void insert(T item){

		Node toInsert = new Node(item, this.head);
		this.head = toInsert;
		++size;
		
	}
	
	public T deleteTop(){
		if(head == null) return null;
		
		T toReturn = head.item;
		this.head = head.next;
		return toReturn;

	}

	public void printList(){
		Node curr = head;
		while(curr != null){
			System.out.print(curr.item + " "); curr = curr.next;
		}
		System.out.println();
	}


	public boolean isPal(){
		Stack<T> stack = new Stack<T>();

		Node curr = medianNode();
		
		while(curr != null){
			stack.push(curr.item);
			curr = curr.next; 
		}
		Node frontNode = head; 
		
		while(!stack.isEmpty()){
			if(stack.pop() != frontNode.item)
				return false;
			frontNode = frontNode.next;
			
		}
		
		return true;
	}

	public T middle(){
		return medianNode().item;
	}
	
//If odd, returns the second of the two
	private Node medianNode(){
		if(head == null || head.next == null)
			return head;
		
		Node oneSkip = head;
		Node twoSkip = head;
		int i = 1;
		
		while(twoSkip.next != null){
			twoSkip = twoSkip.next;
			if((++i & 1) == 0) oneSkip = oneSkip.next;
			
		}
		return oneSkip;
		
	}
	
	private Node midNode(boolean[] odd){
		
		if(head == null || head.next == null)
			return head;
		
		Node oneSkip = head;
		Node twoSkip = head;
		int i = 0;
		
		while(twoSkip.next != null){
			twoSkip = twoSkip.next;
			if((++i & 1) == 0) oneSkip = oneSkip.next;
			
		}
		odd[0] = i%2 == 0;
		
		return oneSkip;
		
	}
	
	public void reverse(){
		
		if(head == null) return;
		
		 Node prev, curr, next;
		 
		 prev = head; curr = head.next;
		 prev.next = null;

		 while(curr != null){
			 next = curr.next;
			 curr.next = prev;
			 prev = curr; 
			 curr = next;
		 }
		 
		 head = prev;

	}
	
	public static int add(Linkedlist<Integer> l1, Linkedlist<Integer> l2){
		int sum = 0; int place  = 1;
		
		Deque<Linkedlist<Integer>.Node> stack1 = new ArrayDeque<Linkedlist<Integer>.Node>();
		Deque<Linkedlist<Integer>.Node> stack2 = new ArrayDeque<Linkedlist<Integer>.Node>();

		Linkedlist<Integer>.Node curr = l1.head;

		while(curr != null){
			stack1.push(curr); 
			curr = curr.next;
		}
		
		curr = l2.head;
		while(curr != null){
			stack2.push(curr);
			curr = curr.next;
		}
		
		int d1,d2, increment; boolean carry = false;
		
		while(!(stack1.isEmpty() || stack2.isEmpty())){
			d1 = stack1.pop().item; d2 = stack2.pop().item;
			increment = d1 + d2 + (carry? 1: 0);
			if(increment > 9){
				sum += (increment-10)*place; carry = true;
			}
			else{
				sum += increment * place; carry = false;
			}
			place *= 10;
			
		}
		
		while(!stack1.isEmpty()){
			d1 = stack1.pop().item; 
			increment = d1 + (carry? 1: 0);
			if(increment > 9){
				sum += (increment-10)*place; carry = true;
			}
			else{
				sum += increment * place; carry = false;
			}
			place *= 10;
			
			
		}
		
		while(!stack2.isEmpty()){
			d2 = stack2.pop().item; 
			increment = d2 + (carry? 1: 0);
			if(increment > 9){
				sum += (increment-10)*place; carry = true;
			}
			else{
				sum += increment * place; carry = false;
			}
			place *= 10;
		}
		
		if(carry)
			sum += place;
		
	
		return sum;
	}

	
	private class Node{
		final T item;
		Node next;
		
		public Node(T item, Node next){
			this.item = item;
			this.next = next;
		}
		
		public Node(T item){this.item = item;}
		
		
		
	}
}
