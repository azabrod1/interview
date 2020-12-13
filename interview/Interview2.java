package interview;
import trie.Trie;

public class Interview2 {

	public static void main(String[] args) {
		/*
		long start = System.currentTimeMillis();
		Population society = new Population(10000, 0.25, 0.25);
		society.live_show();
		/*
		society.para_run(50);
	
		society.getStats();
		society.printHistory();*/
		
		//System.out.println("Time elapsed " + (System.currentTimeMillis() - start));
		
		
		String[] strs = {"grze   neggs", "sam", "i", "am", "ham"};
		
		System.out.println(Solutions.MergeStrings(strs));
		
		System.out.println(Solutions.count_palindromes(new int[]{1,2,3}, 2));
		
	
	}
	
	
	
	public static void testTrie(){
		
		Trie t = new Trie();
		t.insert("ball");
		t.insert("cat");
		t.insert("cats");
		t.insert("flipper");
		t.insert("flippers");
		t.insert("owls");

		String[] words = "bats , sharks, hide, penquins, hawks, cats, weed, reefer, tv, baseball, fade, void, public ,static , void, main".split("\\s*(=>|,|\\s)\\s*");
		for(String w : words)
			t.insert(w);

		t.insert("balls");
		System.out.println(t.insert("hawk"));

		System.out.println(t.delete("hawks"));
		
		System.out.println(t.containsPrefix("hawks"));
		
		
		
		
		
	}

}
