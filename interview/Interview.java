package interview;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;
import java.util.Stack;
import java.util.List;

import javax.naming.ldap.SortControl;

import heap.BinaryHeap;
import interview.Solutions.Interval;

public class Interview {
	


	public static void main(String[] args) {
		Integer[] ar = new Integer[1000];
		for(int i = 0; i < ar.length; ++i)
			ar[i] = i*4;
		
		
		
		Linkedlist<Integer> list = new Linkedlist<Integer>(ar);
		
		BinarySearchTree<Integer> tree = Linkedlist.sortedToTree(list);
		
		System.out.println(tree.size());
		
		
		
		
		
		
		
		
		

	}
	
	
	public static void clonable() throws CloneNotSupportedException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		class Sample implements Cloneable{
			public int x;
			public Sample(int _x){this.x = _x;}
			
			public Sample clone() throws CloneNotSupportedException {
				Sample copy = (Sample) super.clone(); 
				copy.x++;
                return copy;
			}
			
			public void printX(String y, long t){
				System.out.println("X: " + (x+t));
			}
			
			
		}
		Sample sample = new Sample(5);
		Sample s2 = sample;
		System.out.println(sample == s2);
		Sample s3 = (Sample) s2.clone();
		System.out.println(s3.x);
		System.out.println(Arrays.toString(s2.getClass().getFields()));
		Method method = s2.getClass().getMethod("printX", String.class, long.class);
		method.invoke(s2, "s", 3);
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void heapStuff(){
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>();
		
		heap.insert(5, 5);
		heap.insert(3, 3);
		heap.insert(7, 7);
		heap.insert(10, 10);
		heap.insert(8,8);

		//heap.printHeap();
		System.out.println(heap.removeMin()); 		System.out.println(heap.removeMin());

		
		heap.printHeap();
		
		Integer[] array = {4,2,4,1,42,421,21};
		
		BinaryHeap.heapSort(array);
		System.out.println(array[1]);
		

	}
	
	public static void heapStuff2(){
		
		
		
		int N = 5003000;
		
		Integer[] array = new Integer[N];
		
		for(int i = 0; i < N; ++i){
			array[i] = i;
		}
		shuffleArray(array);
		
		
		Integer[] values = new Integer[N];
		
		for(int i = 0; i < N; ++i)
			values[i] = array[i];
		
		long currTime = System.currentTimeMillis();
		
		//BinaryHeap.heapSort(values);
		
		Arrays.parallelSort(values);
		
		System.out.println(System.currentTimeMillis() - currTime);
		
		
		

	}
	
	
	
	public static void bstFromSorted(){
		
		int N = 1000000;
		
		int[] array = new int[N];
		
		for(int i = 0; i < N; ++i){
			array[i] = i;
		}
		for(int i = 0; i < N; i+=2){
			array[i] = -i;
		}
		
		shuffle(array);
		
		BinarySearchTree<Integer> unBalanced = new BinarySearchTree<Integer>();
		
		long currTime = System.currentTimeMillis();
		
		for(int i : array){
			unBalanced.insert(i);
		}
		
			
		
		System.out.println("First Tree Height  " + unBalanced.getHeight() + "   time: " + (System.currentTimeMillis() - currTime));
		currTime = System.currentTimeMillis();
		
		Arrays.parallelSort(array);
		
		System.out.println("Sort time: " + (System.currentTimeMillis() - currTime));

		currTime = System.currentTimeMillis();
		
		Integer[] values = new Integer[N];
		
		for(int i = 0; i < N; ++i)
			values[i] = array[i];
		
		currTime = System.currentTimeMillis();

		
		BinarySearchTree<Integer> balanced = unBalanced.sortedToTree(array, values);
		
		System.out.println("Balanced Tree Height  " + balanced.getHeight() + "   time: " + (System.currentTimeMillis() - currTime));
		System.out.println(Arrays.toString(array));
		balanced.allSums(101109);
		
		
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void sampleBST(BinarySearchTree<Integer> b){
		
		b.insert(50, 50);
		b.insert(33, 33);
		b.insert(70, 70);
		b.insert(24, 24);
		b.insert(21, 21);
		b.insert(100, 100);
		b.insert(65,65);
		b.insert(63,63);

		b.insert(45,45);
		b.insert(46,46);

		b.insert(41,41);
		b.insert(25,25);

		b.insert(22);
		b.insert(19);
		b.insert(28);
		b.insert(42);
		b.insert(38);
		b.insert(43);
		b.insert(35);
		b.insert(48);
		b.insert(39);
		b.insert(81);
		b.insert(91);
		b.insert(115);
		
	/*	System.out.println(b.leastCommonAncestor(39,41));
		System.out.println(b.leastCommonBinaryAncestor(39,41));
		System.out.println(b.kSmallest(4));
		System.out.println(b.kSmallestIt(4));
		System.out.println(b.isBalanced());
		*/
		
	}
	
	public static void sampleBST2(BinarySearchTree<Integer> b){
		
	

	}
public static void bstFromSorted2(){
		
		int N = 15;
		
		int[] array = new int[N];
		
		for(int i = 0; i < N; ++i){
			array[i] = i;
		}
		
		shuffle(array);
		
		BinarySearchTree<Integer> unBalanced = new BinarySearchTree<Integer>();
		
		long currTime = System.currentTimeMillis();
		
		for(int i : array){
			unBalanced.insert(i);
		}
		
		BinarySearchTree<Integer> copy = unBalanced.treeCopy(unBalanced);
		
		for(int i = 0; i < N; ++i){
			if(copy.find(i) == null)
				System.out.println("ERR" + i);
			
		}
		
		copy.toDoublyLinked();
		
	}
	
	
	
	public static void shuffle(int[] array)
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
	
	
	private static void shuffleArray(Integer[] array)
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
	
	
	
	
	public static <E> boolean isRotation(E[] first, E[] second){
		
		int size = first.length;
		
		if(size != second.length)
			return false;
		
		int it;
		
		for(int offset = 0; offset < size; ++offset){
			it = 0;
			while(first[(offset + it) % size] == second[it]){
				++it;
				if(it == size)
					return true; 
			}
	
		}
		return false;
	}
	private static int adjBinarySearch(int[] list, int key, int l, int h){
		
		int middle = (h+l)/2;
		
		if(list[middle] == key)
			return middle;
		
		if(l == h)
			return -1;

		//First half is normal
		if(list[l] < list[middle]){
			if(key >= list[l] && key < list[middle])
				return adjBinarySearch(list, key, l, middle - 1);
			else
				return adjBinarySearch(list, key, middle + 1, h);
		}
		//Second half normal
		if(key > list[middle] && key <= list[h])
			return adjBinarySearch(list, key, middle + 1, h);
		else
			return adjBinarySearch(list, key, l, middle - 1);

	}
	
	public static int adjBinarySearch(int[] list, int key){
		return adjBinarySearch(list, key, 0, list.length - 1);
	}
	
}
