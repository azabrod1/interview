package trie;

import java.util.HashMap;

public class Trie {

	private Cell root = new Cell();
	
	/*Insert a new word into the Trie. If the word is already in the trie, return false, else true*/
	public boolean insert(String word){
		Cell curr  = root;
		int len    = word.length();
		int cIndex = 0;
		
		//First go through the characters that exist
		while(cIndex < len && curr.contains(word.charAt(cIndex))) curr = curr.next(word.charAt(cIndex++));
		
		if(cIndex == len){ //Reached where the end of word should be
			if(curr.endWord) return false;
			curr.endWord = true;
		}
		
		while(cIndex < len){ curr = curr.addLink(word.charAt(cIndex++)); }
		curr.endWord = true;
		
		return true;
	}
	/*Return true if delete successful, otherwise return false if the word was not in the tree already */
	public boolean delete(String word){ return killWord(word, 0, root) != D_Status.NOT_FOUND;}
	
	private enum D_Status {NOT_FOUND, SUCCESS, CHILD_DESTROYED;}
	
	private D_Status killWord(String word, int idx, Cell curr){
		///We are at the cell that should contain the EOW pointer
		if(idx == word.length()){
			if(!curr.endWord) return D_Status.NOT_FOUND; //This node is not the end of a word, hence the word does not exist
			curr.endWord = false;
			return curr.cellEmpty()? D_Status.CHILD_DESTROYED : D_Status.SUCCESS;
			
		}
		
		//If we are here, we are not yet at the end of the word, so must keep searching
		Cell next = curr.next(word.charAt(idx));
		if(next == null) return D_Status.NOT_FOUND;
		
		D_Status status = killWord(word, idx + 1, next);
		
		/*If the recursive call returns CHILD_DESTROYED, it means the cell we visited no longer has any useful 
		 * information, hence we can delete our pointer to it. If now this cell contains no useful information, we return CHILD_DESTORYED
		 * again so that this cell is deleted
		 */
		if(status == D_Status.CHILD_DESTROYED){
			return curr.removeLink(word.charAt(idx))? D_Status.CHILD_DESTROYED : D_Status.SUCCESS;
		}
		
		return status;

	}
	
	public boolean containsPrefix(String prefix){return checkPrefix(prefix, root, 0);}
	
	private boolean checkPrefix(String prefix, Cell curr, int idx){
		if(idx == prefix.length())
			return true;
		Character nextLetter = prefix.charAt(idx);
		return curr.contains(nextLetter)? checkPrefix(prefix, curr.next(nextLetter), 1+idx) : false;
	}
	
	
	public boolean containsWord(String word){
		Cell curr  = root;
		int len    = word.length();
		int cIndex = 0;
		
		//First go through the characters that exist
		while(cIndex < len && curr.contains(word.charAt(cIndex))) curr = curr.next(word.charAt(cIndex++));
		
		return (cIndex == len) && curr.endWord;
	}
	

	private class Cell{
		private final HashMap<Character, Cell> next;
		public boolean endWord = false;
		
		public Cell(){
			next = new HashMap<Character, Cell>();
		}
		
		//Does the cell contain carry meaningful information?
		public boolean cellEmpty(){
			return !endWord && next.isEmpty();
		}
		
		public Cell next(Character key){
			return next.get(key);
		}
		
		public boolean contains(Character key){
			return next.containsKey(key);
		}
		
		//Returns whether the map is now empty
		public boolean removeLink(Character toRemove){
			next.remove(toRemove);
			return !endWord && next.isEmpty();
			
		}
	
		public Cell addLink(Character toAdd){
			Cell newLink = new Cell();
			next.put(toAdd, newLink);
			return newLink;
			
		}	
	}
}

