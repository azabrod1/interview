package interview;

import javax.crypto.Mac;

public class Basket {
	
	 Food[] foods = {new Pizza(), new Burger(), new MacCheese()}; 
	 int[] quantities = new int[FoodType.values().length];
	 
	 void addItem(Food item){
		quantities[item.type.ordinal()]++;
	 }
	 
	 double total(){
		 double total = 0;
		 for(int f = 0; f < quantities.length; ++f){
			 System.out.println(quantities[f] + "  " + foods[f].price+ "   " + foods[f].getPrice(quantities[f]) + "   " + (foods[f] instanceof MacCheese));
			 if(quantities[f] != 0)
				 total += foods[f].getPrice(quantities[f]);
			 
		 }
			 
			 
		 return total;
		 
		 
	 }
	 
	
	
}
