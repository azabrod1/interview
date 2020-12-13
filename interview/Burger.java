package interview;


public class Burger extends Food{
	
	public Burger(){
		this.type = FoodType.burger;

		this.price = 5;
	}
	@Override
	double getPrice(int q) {
		
		if(q >= 3) return q*4.50;
		
		else return  super.getPrice(q);
	
	}
}