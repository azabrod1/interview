package interview;


public class Food{
	double price;
	FoodType type; 
	
	double getPrice(int q){
		return q%2 == 0? (price)*(q/2) : (price)*(q/2) + price;
	}
	
	
}

