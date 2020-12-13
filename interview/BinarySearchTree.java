package interview;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;


public class BinarySearchTree<V> {

	private Node root;
	
	public BinarySearchTree (Node _root){
		this.root =  _root;
	}
	
	Node getNewNode(V value){
		return new Node((int) value, value);
	}
	
	public BinarySearchTree() {this(null);}
	
	public void insert(int key, V value){
	
		if(root == null){
			root = new Node(key, value);
			return;
		}
		
		Node toBranch = root;
		
		do{
			if(toBranch.key == key){
				toBranch.value = value;
				return;
			}
			
			if(key < toBranch.key){
				if(toBranch.left == null){
					toBranch.left = new Node(key, value);
					return;
				}
					toBranch = toBranch.left;
				
			}
			else if(key > toBranch.key){
				if(toBranch.right != null)
					toBranch = toBranch.right;
				else{
					toBranch.right = new Node(key,value);
					return;
				}
			}
		
			
		}while(true);
	
		
	}
	
	public V find(int key){
		return subtreeSearch(root, key);
	
	}
	
	protected V subtreeSearch(Node root, int key){
		
		if(root == null)
			return null;
		
		if(root.key == key )
			return root.value;
		
		if(key < root.key)
			return subtreeSearch(root.left, key);
		
		return subtreeSearch(root.right, key);
		
	}
	
	public V delete(int key){
		
		if(root == null)
			return null;
		
		if(root.key == key){
			Node dummyRoot = new Node(key+1, null);
			dummyRoot.left = root;
			root = dummyRoot;
			V toReturn = delete(key);
			root = root.left;
			return toReturn;
			
		}
		
		Node parent = root;
		Node child;
		
		while(true){
						
			
			if(key < parent.key){
				child = parent.left;
				if(child == null)
					return null;
				if(child.key == key){
					if(child.left == null){
						parent.left = child.right;
					}
					else if(child.right == null){
						parent.left = child.left;
					}
					
					else{
						if(child.right.left == null){
							parent.left = child.right;
							child.right.left = child.left;
							
						}
						parent.left = findAndKillSmallest(child.right);
						parent.left.left = child.left;
						parent.left.right = child.right;
					}
					
					return child.value;
				}
				
			}
			
			else{
				child = parent.right;
				if(child == null)
					return null;
				if(child.key == key){
					if(child.left == null){
						parent.right = child.right;
					}
					else if(child.right == null){
						parent.right = child.left;
					}
					
					else{
						if(child.right.left == null){
							parent.right = child.right;
							child.right.left = child.left;
							
						}
						parent.right = findAndKillSmallest(child.right);
						parent.right.left  = child.left;
						parent.right.right = child.right; 
					}
					return child.value;
				}
				
			}
			
			parent = child;

		}
	}


	private Node findAndKillSmallest(Node root){

		if(root.left.left == null){ //root's child (root.left) is the smallest
			Node toReturn = root.left; 
			root.left = root.left.right;
			return toReturn;

		}
		return findAndKillSmallest(root.left);

	}
	//Unsafe
	public void insert(Integer keyVal){
		this.insert(keyVal,(V) keyVal);
		
	}
	
	public int leastCommonAncestor(int k1, int k2){
		
		if(k1 == k2)
			return k1;
		if(root.key == k1 || root.key == k2)
			return root.key;
		
		return findAncestor(root, k1, k2).key; 
		
		
	}
	
	private Node findAncestor(Node curr, int k1, int k2){
		
		if(curr == null)
			return null;
		
		if(curr.key == k1 || curr.key == k2){
			return curr;

		}
		
		Node leftResult  = findAncestor(curr.left, k1, k2);
		Node rightResult = findAncestor(curr.right, k1, k2);

		if(leftResult != null){
			if(rightResult != null)
				return curr; 
			return leftResult;
		}
		return rightResult;
		
		
	}
	
	
	
	public int size(){ return size(root); }
	
	private int size(Node node){
				
		return node == null? 0 :  1 + size(node.right) + size(node.left);
		
	}
	

	
	public int leastCommonBinaryAncestor(int k1, int k2){
		
		if(root == null || root.key == k1 || root.key == k2)
			return root.key;
		
		if(k1 == k2)
			return k1;
		
		
		int max = Math.max(k1, k2);
		int min = Math.min(k1, k2);
		
		Node curr = root;
		
		while(curr != null){
			if(curr.key == k1 || curr.key == k2)
				return curr.key;
			
			if(max < curr.key)
				curr = curr.left;
			else if(min > curr.key)
				curr = curr.right;
			else
				return curr.key;
			
		}
		
		return -1;
		
	}
	
	
	public ArrayList<V> kSmallestIt(int k){
		
		ArrayList<V> toReturn  = new ArrayList<V>(k);
		ArrayDeque<Node> stack = new ArrayDeque<Node>();
		//stack.push(root); 

		Node curr = root;
		
		while(toReturn.size() < k){
			
			if(curr.left == null){
				do{
					toReturn.add(curr.value);
					if(curr.right != null){
						curr = curr.right;
						break;
					}
					if(!stack.isEmpty())
						curr = stack.pop();
					else
						return toReturn;
					
					
				}while(true);
				
				continue;
				
			}
				stack.push(curr);
				curr = curr.left;
			
			}
			
		return toReturn;	
			
		}
	
	
	public  BinarySearchTree<V> sortedToTree(int[] keys, V[] values){
		
		if(keys.length == 0)
			return null;
		
			
		BinarySearchTree<V> tree = new BinarySearchTree<V>();
		
	
		tree.root = makeSubtree(keys, values, 0, keys.length - 1);
		
		return tree;
		
		
	}
	
	public Node makeSubtree(int[] keys, V[] values, int l, int h){
		
		if(l > h)
			return null;
		
		int middle = (h+l)/2;
		
		Node newSubroot = new Node(keys[middle], values[middle]);
		
		newSubroot.left = makeSubtree(keys, values, l, middle - 1);
		
		newSubroot.right = makeSubtree(keys,values, middle + 1, h);
		
		return newSubroot;
		
		
	}
	
	
	
	
	
	
	public boolean isBalanced(){ return (isBalanced(root) > -2);}

	
	private int isBalanced(Node node){
		
		if(node == null)
			return -1;
		
		int leftDepth  = isBalanced(node.left);
		if(leftDepth == -10)
			return -10;
		int rightDepth = isBalanced(node.right);
		
		if(rightDepth == -10)
			return -10;
				
		if(leftDepth - rightDepth > 1 || leftDepth - rightDepth < -1)
			return -10;
		
		return Math.max(leftDepth, rightDepth) + 1; //"Good case"
		
	}
	
	public int getHeight(){ 	return calcHeight(root);	}
	
	private int calcHeight(Node node){
		if(node == null)
			return -1;
		
		return Math.max(calcHeight(node.right), calcHeight(node.left)) + 1;
		
	}
	
	

	public ArrayList<V> kSmallest(int k){
		
		if(k < 1)
			return null;

		ArrayList<V> toReturn = new ArrayList<V>(k);
		
		kInOrder(toReturn, k, root);
		
		return toReturn;

	}
	
	private int kInOrder(ArrayList<V> ordering, int k, Node curr){
		
		if(curr == null || k == 0)
			return 0;
		
		int count = kInOrder(ordering, k, curr.left);
		
		if(count < k){
			ordering.add(curr.value);
		
			if(++count < k ){
				return kInOrder(ordering, k - count, curr.right) + count;
			}
			
		}
		
		return count;
		
	}
	
	
	public Node toDoublyLinked(){
		
		Node rightMost = linker(root, null);
		rightMost.right = null; //End of list
		
		Node leftMost = printList(rightMost);
		
		return rightMost;
	
	}


	//Returns previously added node
	private Node linker(Node curr, Node prevAdded){

		if(curr.left == null){
			curr.left = prevAdded; // predecessor <-- curr
			if(prevAdded != null)
				prevAdded.right = curr;  //predecessor --> curr
			if(curr.right != null)
				curr.right = linker(curr.right, curr); 

			return curr; //I Am previously added

		} 

		prevAdded = linker(curr.left, prevAdded); 
		
		prevAdded.right = curr;  //predecessor --> curr
		
		curr.left = prevAdded; // predecessor <-- curr
		
		if(curr.right != null)
			return linker(curr.right, curr);  
		
		return curr;

	}


	private Node printList(Node curr){
		
		//Now print to test
		while(curr.left!= null){
			System.out.println(curr.value);
			curr = curr.left;
			
		} System.out.println(curr.value);

		Node toReturn = curr;
		//Now other way
		
		while(curr!= null){
			System.out.println(curr.value);
			curr = curr.right;
		}
		
		return toReturn;
		
		
	}
	
	public void sumPaths(int sum){
		computeSum(root, new ArrayList<Node>(20), sum);
	}
	
	public void allSums(int sum){
		
		ArrayDeque<Node> nodes = new ArrayDeque<Node>(1000);
		nodes.add(root);
		while(!nodes.isEmpty()){
			Node curr = nodes.pop();
			if(curr.left != null)
				nodes.add(curr.left);
			if(curr.right != null)
				nodes.add(curr.right);
			computeSum(curr, new ArrayList<Node>(50), sum);

		}
		
	}
	
	private void computeSum(Node node, ArrayList<Node> path , int deficit){
		
		if(node == null)
			return;
		
		path.add(node);
		deficit -= node.key;
		if(deficit == 0)
			printPath(path);
		computeSum(node.left, path, deficit);
		computeSum(node.right, path, deficit);
		deficit += node.key;
		path.remove(path.size() - 1);
	}
	
	static final int LEFT = 0; static final int BOTH = 1; static final int NONE = 2;

	public  BinarySearchTree<V> treeCopy(BinarySearchTree<V> original){
		Node curr, oldCurr;
		BinarySearchTree copy = new BinarySearchTree();
		copy.root = new Node(original.root.key, original.root.value);
		curr = copy.root;
		oldCurr = original.root;
		int childEXPLORED = NONE;
		Node parent, oldParent;
		
		while(!(curr == copy.root && childEXPLORED == BOTH)){
			
			if(childEXPLORED == NONE){ //have not explored left yet
				if(oldCurr.left == null)
					childEXPLORED = LEFT;
				else{                  //Go explore left subtree
					curr.left = new Node(oldCurr.left.key, oldCurr.left.value);
					curr.left.prev = curr;  //This way we can climb back up the tree
					curr.left.next = oldCurr; //So we can climb back up old tree
					curr    = curr.left;
					oldCurr = oldCurr.left;
					childEXPLORED = NONE;
				}
				
			}
			
			else if(childEXPLORED == LEFT){
				if(oldCurr.right == null)
					childEXPLORED = BOTH; 
				else{				   //Go explore right subtree
					curr.right = new Node(oldCurr.right.key, oldCurr.right.value);
					curr.right.prev = curr;  //This way we can climb back up the tree
					curr.right.next = oldCurr; //So we can climb back up old tree
					curr       = curr.right;
					oldCurr    = oldCurr.right;
					childEXPLORED = NONE;
				}
			}
			
			else{ //left and right have already been explored so need go backwards
				parent    = curr.prev;
				oldParent = curr.next;
				
				childEXPLORED = (curr == parent.left)? LEFT : BOTH; //Before going back up tree, let parent know if I am left or right child
				curr = parent; oldCurr = oldParent;	
				
			}
		
		}
		
		return copy;
		
				
	}
	
/*	//Removes nodes with key in range
	public void removeRange(int low, int high){
		
		
		
	}
	
	private Node rangeKill(Node n, int low, int high, int l, int h){
		
		if(n == null)
			return null;
		
		
		
		
		
	}
		*/
		
	private void printPath(ArrayList<Node> path){
		if(path.isEmpty())
			return;
		for(Node n : path)
			System.out.print(n.key + ", ");
		
	}

	class Node{
		
		public int key;
		public V value;
		public Node left;
		public Node right;
		public Node prev, next;
		
		public Node(){
			
		}
		
		public Node(int key, V value){
			this.key   = key;
			this.value = value;
			
		}

	}
	
}
