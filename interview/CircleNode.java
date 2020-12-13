package interview;

public class CircleNode {

	final int value;
	CircleNode next;
	
	
	public CircleNode(int _value, CircleNode _next){
		this.value = _value; this.next = _next;
		
	}
	
	public CircleNode(int _value){
		this(_value, null);
	}
	
	public void insert(int value){insert(value, this);}
	
	public void printSortedList(){printSortedList(this);}

	
	public static void insert(int value, CircleNode start){
		
		if(start == null) start = new CircleNode(value, start);
		
		CircleNode curr = start; 
		
		while(true){
			
			if(curr.value > curr.next.value && (value >= curr.value || value <= curr.next.value)){
				curr.next = new  CircleNode(value, curr.next);
				return;
			}
			
			if(value >= curr.value && value <= curr.next.value){
				curr.next = new  CircleNode(value, curr.next);
				return;
			}
			
			curr = curr.next;
			
			if(curr == start){ //Went around in a circle, that means all the nodes are identical
				curr.next = new  CircleNode(value, curr.next);
				return;

			}
		}
		
	}
	
	public static  void printSortedList(CircleNode start){
		if(start == null){
			System.out.println("The list is empty");
			return;
		}
		
		if(start == start.next){
			System.out.println(start.value);
			return;
		}
		
		CircleNode curr = start;
		
		do{
			
			if(curr.value > curr.next.value){
				start = curr.next;
				break;  
			}
		
			curr = curr.next;
		
		}while(curr != start);
		
		curr = start;
		System.out.print("{ ");
		do{
			System.out.print(curr.value + " ");
			curr = curr.next;
		}while(curr != start);
		
		System.out.print("}");

	}
	

	
}
