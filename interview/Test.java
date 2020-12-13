package interview;

public class Test {

	public static void main(String[] args) {
		Basket basket = new Basket();
		basket.addItem(new MacCheese());
		basket.addItem(new Burger()); 		basket.addItem(new Burger());
		basket.addItem(new Burger());


		System.out.println(basket.total());

		
		
	}

}
