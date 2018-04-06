package codes.c1moore.refresher.trie;
import java.util.HashMap;
import java.util.Map;

/**
 * Trie builds off of and improves SimpleTrie.  Trie allows duplicate
 * Strings and a dynamic alphabet.  Instead of defining a static
 * alphabet size and Function that can convert a char to an index
 * in the range of [0, ALPHABET_SIZE), Trie supports any legal char
 * supported by Java.
 */
public class Trie {
	private final TrieNode HEAD;

	/**
	 * Creates a new Trie.
	 */
	public Trie() {
		HEAD = new TrieNode(null);
	}

	/**
	 * Adds str to this Trie.  If str has already been added to this Trie, a duplicate is stored.
	 * 
	 * @param str the String to add to the Trie
	 */
	public void add(String str) {
		if(str == null) {
			throw new NullPointerException();
		}

		TrieNode parentNode = HEAD;
		TrieNode childNode = HEAD;

		// Navigate the Trie, creating nodes as necessary.
		for(char c: str.toCharArray()) {
			childNode = parentNode.getChild(c);

			if(childNode == null) {
				childNode = new TrieNode(parentNode);

				parentNode.addChild(childNode, c);
			}

			childNode.incrementOccurrenceCount();

			parentNode = childNode;
		}

		// Since the child node now represents the last node for str, mark it as terminal.
		childNode.incrementTerminalCount();
	}

	/**
	 * Returns true iff str has been inserted into this Trie.
	 * 
	 * @param str the String to search for
	 * 
	 * @return true iff str is in this Trie
	 */
	public boolean has(String str) {
		TrieNode currentNode = HEAD;

		for(char c: str.toCharArray()) {
			currentNode = currentNode.getChild(c);

			if(currentNode == null) {
				return false;
			}
		}

		return currentNode.isTerminal();
	}

	/**
	 * Returns true if str has been inserted into this Trie or a String with a prefix of str has
	 * been inserted into this Trie.
	 * 
	 * @param str the String to search for
	 * 
	 * @return true iff str is a prefix of or represents a complete String inserted into this Trie
	 */
	public boolean contains(String str) {
		TrieNode currentNode = HEAD;

		for(char c: str.toCharArray()) {
			currentNode = currentNode.getChild(c);

			if(currentNode == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Counts the number of times str has been inserted into this Trie.  This method does not count the number
	 * of times str appears as a prefix to other Strings.
	 * 
	 * @param str the String of interest
	 * 
	 * @return the total number of times str has been inserted into this Trie
	 */
	public int countOccurrences(String str) {
		TrieNode currentNode = HEAD;

		for(char c: str.toCharArray()) {
			currentNode = currentNode.getChild(c);

			if(currentNode == null) {
				return 0;
			}
		}

		return currentNode.getTerminalCount();
	}

	/**
	 * Removes a single occurrence of str.  Strings that contain str as a substring will not
	 * be affected.
	 * 
	 * @param str the String to remove
	 */
	public void remove(String str) {
		TrieNode currentNode = HEAD;

		// First, make sure the word exists in this Trie
		for(char c: str.toCharArray()) {
			currentNode = currentNode.getChild(c);

			if(currentNode == null) {
				// String does not exist in Trie.
				return;
			}
		}
		
		if(currentNode.getTerminalCount() <= 0) {
			return;
		}

		currentNode.decrementTerminalCount();
		
		for(int index = str.length() -1; currentNode != HEAD; index--) {
			currentNode.decrementOccurrenceCount();
			
			int charCount = currentNode.getOccurrenceCount();
			
			currentNode = currentNode.getParent();
			
			if(charCount == 0) {
				currentNode.removeChild(str.charAt(index));
			}
		}
	}

	/**
	 * Removes all insertions of str from this Trie.  Strings that contain str as a substring will not
	 * be affected.
	 * 
	 * @param str the String that should be removed from the Trie
	 */
	public void removeAll(String str) {
		TrieNode currentNode = HEAD;

		// First, make sure the word exists in this Trie
		for(char c: str.toCharArray()) {
			currentNode = currentNode.getChild(c);

			if(currentNode == null) {
				// String does not exist in Trie.
				return;
			}
		}
		
		int totalOccurrences = currentNode.getTerminalCount();
		
		if(totalOccurrences <= 0) {
			return;
		}

		currentNode.decrementTerminalCount(totalOccurrences);
		
		for(int index = str.length() -1; currentNode != HEAD; index--) {
			currentNode.decrementOccurrenceCount(totalOccurrences);
			
			int charCount = currentNode.getOccurrenceCount();
			
			currentNode = currentNode.getParent();
			
			if(charCount == 0) {
				currentNode.removeChild(str.charAt(index));
			}
		}
	}

	/**
	 * TrieNode represents a node with the Trie tree.
	 */
	private class TrieNode {
		private TrieNode parent;
		private Map<Character, TrieNode> children = new HashMap<>();

		private int occurrenceCount;	// How many times is this node used?
		private int terminalCount;		// How many words end at this node?

		/**
		 * Creates a new TrieNode that points to parent as its parent.
		 * 
		 * @param parent the parent node
		 */
		public TrieNode(TrieNode parent) {
			this.parent = parent;
			
			occurrenceCount = 0;
			terminalCount = 0;
		}
		
		/**
		 * Returns this TrieNode's parent node.
		 * 
		 * @return this TrieNode's parent
		 */
		public TrieNode getParent() {
			return this.parent;
		}

		/**
		 * Returns the child node for c.
		 * 
		 * @param c the character for which the child node should be returned
		 * 
		 * @return the child node for c
		 */
		public TrieNode getChild(char c) {
			return children.get(c);
		}

		/**
		 * Adds child as a child node for character c.
		 * 
		 * @throws (IllegalStateException) If there is already a child node for c
		 * 
		 * @param child the new child node
		 * @param c the character represented by child
		 */
		public void addChild(TrieNode child, char c) {
			if(getChild(c) != null) {
				throw new IllegalStateException("Node already exists for character " + c + ".");
			}

			children.put(c, child);
		}

		/**
		 * Removes the child node positioned at c.
		 * 
		 * @param c the child node to remove
		 */
		public void removeChild(char c) {
			TrieNode child = getChild(c);

			if(child == null) {
				return;
			}
			
			child.parent = null;
			children.remove(c);
		}

		/**
		 * Returns true iff this TrieNode represents the end of a String.
		 * 
		 * @return true iff this TrieNode represents the end of a String
		 */
		public boolean isTerminal() {
			return (terminalCount > 0);
		}

		/**
		 * Returns the total number of Strings that end at this TrieNode.
		 * 
		 * @return the total number of Strings that end at this TrieNode
		 */
		public int getTerminalCount() {
			return terminalCount;
		}

		/**
		 * Increments the count of the total number of Strings that end at this TrieNode
		 */
		public void incrementTerminalCount() {
			terminalCount++;
		}

		/**
		 * Decrements the count of the total number of Strings that end at this TrieNode.
		 * 
		 * @throws (IllegalStateException) If the terminal count is already 0
		 */
		public void decrementTerminalCount() {
			if(terminalCount <= 0) {
				throw new IllegalStateException("Terminal count cannot be negative.");
			}

			terminalCount--;
		}

		/**
		 * Decrements the count of the total number of Strings that end at this TrieNode.
		 * 
		 * @throws (IllegalStateException) If value is greater than the terminal count.
		 * 
		 * @param value the value by which the count should be decremented
		 */
		public void decrementTerminalCount(int value) {
			if(value > terminalCount) {
				throw new IllegalStateException("Terminal count cannot be negative.");
			}

			terminalCount -= value;
		}

		/**
		 * Returns the total number of Strings that contain this character after all
		 * of its ancestors.
		 * 
		 * @return the total number of Strings in this Trie that contain this character
		 *  after the prefix formed by its ancestors
		 */
		public int getOccurrenceCount() {
			return occurrenceCount;
		}

		/**
		 * Increments the occurrence count by 1.
		 */
		public void incrementOccurrenceCount() {
			occurrenceCount++;
		}

		/**
		 * Decrements the occurrence count by 1.
		 * 
		 * @throws (IllegalStateException) If the occurrence count is 0
		 */
		public void decrementOccurrenceCount() {
			if(occurrenceCount <= 0) {
				throw new IllegalStateException("Occurrence count cannot be negative.");
			}

			occurrenceCount--;
		}

		/**
		 * Decrements the occurrence count by value.
		 * 
		 * @throws (IllegalStateException) If value is greater than the occurrence count
		 * 
		 * @param value the value by which the occurrence count should be decremented
		 */
		public void decrementOccurrenceCount(int value) {
			if(value > occurrenceCount) {
				throw new IllegalStateException("Occurrence count cannot be negative.");
			}

			occurrenceCount -= value;
		}
	}
}