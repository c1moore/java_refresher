package codes.c1moore.refresher.trie;

import java.util.function.Function;

/**
 * Implements a simple Trie tree. This implementation uses a static sized array
 * of length ALPHABET_SIZE as specified when the SimpleTrie is created. This is
 * the implementation commonly found in introductory books on the subject and
 * simple examples around the web.
 * 
 * To improve the flexibility of the Trie, the Trie will accept a Function that
 * can convert chars to indices in an array. Instead of supporting all
 * characters all the time, this allows SimpleTrie to support simplified
 * alphabets with less space requirements.
 */
public class SimpleTrie {
	private final int ALPHABET_SIZE;
	private final TrieNode HEAD;

	private Function<Character, Integer> charToIndex;

	/**
	 * Creates a new SimpleTrie tree.
	 *
	 * @param alphabetSize the total number of legal characters that appear in the
	 *            alphabet
	 * @param charToIndex a Function that converts a legal char to an int index. The
	 *            index must be in the range [0,alphabetSize)
	 */
	public SimpleTrie(int alphabetSize, Function<Character, Integer> charToIndex) {
		ALPHABET_SIZE = alphabetSize;
		HEAD = new TrieNode(null);

		this.charToIndex = charToIndex;
	}

	/**
	 * Adds str to the SimpleTrie, if it does not already exist. str must not be
	 * null.
	 *
	 * @throws NullPointerException thrown if str is null
	 *
	 * @param str the String to add to the SimpleTrie
	 */
	public void add(String str) {
		if(str == null) {
			throw new NullPointerException();
		}

		TrieNode parentNode = HEAD;
		TrieNode childNode = HEAD;

		// Follow the path down the Trie, creating nodes as necessary.
		for(char c: str.toCharArray()) {
			childNode = parentNode.getChild(c);

			if(childNode == null) {
				childNode = new TrieNode(parentNode);

				parentNode.addChild(childNode, c);
			}

			parentNode = childNode;
		}

		// The last node for this String should be marked as terminal (since the String ends here).
		childNode.setTerminal(true);
	}

	/**
	 * Determines if str has been stored in this SimpleTrie.
	 *
	 * @param str the String to find in this SimpleTrie
	 *
	 * @return true iff str has been added to this SimpleTrie; false otherwise
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
	 * Determines if str is a prefix of a String stored in this Trie.
	 * 
	 * @param str the prefix to find
	 * 
	 * @return true iff str is the prefix of at least one String in this Trie
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
	 * Removes str from this SimpleTrie. str will not be removed if it is a prefix
	 * of another word.
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

		if(!currentNode.isTerminal()) {
			return;
		}

		currentNode.setTerminal(false);

		for(int index = str.length() - 1; currentNode != HEAD; index--) {
			if(currentNode.hasChildren() || currentNode.isTerminal()) {
				break;
			}

			currentNode = currentNode.getParent();

			currentNode.removeChild(str.charAt(index));
		}
	}

	/**
	 * TrieNode represents a node within the SimpleTrie tree. Instead of having
	 * different node types for branch and leaf nodes or a special character to
	 * indicate the end of String, the TrieNode can be marked as terminal if a
	 * String ends at this node.
	 */
	private class TrieNode {
		TrieNode parent;
		TrieNode children[] = new TrieNode[ALPHABET_SIZE];

		boolean terminal;   // Is this the last node for a String stored in the SimpleTrie?
		int totalChildren;	// How many children does this node have?

		public TrieNode(TrieNode parent) {
			this.parent = parent;

			for(int index = 0; index < ALPHABET_SIZE; index++) {
				children[index] = null;
			}

			terminal = false;
		}

		/**
		 * Returns the parent of this TrieNode.
		 * 
		 * @return the parent TrieNode
		 */
		public TrieNode getParent() {
			return parent;
		}

		/**
		 * Returns this node's child for c.
		 *
		 * @param c the next character
		 */
		public TrieNode getChild(char c) {
			return children[charToIndex.apply(c)];
		}

		/**
		 * Adds a child node for this TrieNode for character c. If a child node already
		 * exists for c, an error is thrown.
		 *
		 * @throws IllegalStateError thrown if a child node already exists for c
		 *
		 * @param child the new child
		 * @param c the character from which child should branch
		 */
		public void addChild(TrieNode child, char c) {
			if(getChild(c) != null) {
				throw new IllegalStateException("Node already exists for character " + c + ".");
			}

			children[charToIndex.apply(c)] = child;
			totalChildren++;
		}

		/**
		 * Removes this TrieNode's child for c.
		 * 
		 * @param c the child to remove
		 */
		public void removeChild(char c) {
			children[charToIndex.apply(c)] = null;
			totalChildren--;
		}

		/**
		 * Returns true iff this TrieNode has at least one child.
		 * 
		 * @return true iff this TrieNode has at least one child
		 */
		public boolean hasChildren() {
			return (totalChildren > 0);
		}

		/**
		 * Returns true iff this TrieNode represents the end of a String.
		 *
		 * @return true iff this TrieNode represents the end of a String
		 */
		public boolean isTerminal() {
			return terminal;
		}

		/**
		 * Marks this TrieNode as a terminal node. That is, this node will be recognized
		 * as the last node for at least one string.
		 * 
		 * @param terminal true iff this TrieNode is a terminal node
		 */
		public void setTerminal(boolean terminal) {
			this.terminal = terminal;
		}
	}
}
