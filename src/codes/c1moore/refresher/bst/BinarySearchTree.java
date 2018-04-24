package codes.c1moore.refresher.bst;

import java.util.Comparator;
import java.util.List;

import codes.c1moore.refresher.common.Comparison;

/**
 * BinarySearchTree represents a simple, unbalanced BST. All operations are
 * worse-case O(n) with expected-case of O(log(n)).
 * 
 * BinarySearchTree uses an approach for ordering similar to other standard Java
 * classes in that it accepts an optional Comparator that can be passed to the
 * constructor. If a Comparator is specified, it will be used for all
 * Comparisons. If a Comparator is not specified, all elements in the tree must
 * implement Comparable.
 */
public class BinarySearchTree<T> {
	private BinaryTreeNode<T> head;
	private Comparator<T> comparator;

	/**
	 * Creates an empty BinarySearchTree.
	 */
	public BinarySearchTree() {
		head = null;
		comparator = null;
	}

	/**
	 * Creates an empty BinarySearchTree that will use comparator to compare items.
	 *
	 * @param comparator a Comparator that should be used to compare items
	 */
	public BinarySearchTree(Comparator<T> comparator) {
		this();

		this.comparator = comparator;
	}

	/**
	 * Creates a BinarySearchTree initialized with data.
	 *
	 * @param data the items to insert in the BinarySearchTree
	 */
	public BinarySearchTree(T[] data) {
		this();

		for(T item: data) {
			this.insert(item);
		}
	}

	/**
	 * Creates a BinarySearchTree initialized with data. Comparator will be used for
	 * all comparison related to the new BinarySearchTree.
	 *
	 * @param data the items to insert in the BinarySearchTree
	 * @param comparator a Comparator that should be used to compare items
	 */
	public BinarySearchTree(T[] data, Comparator<T> comparator) {
		this(comparator);

		for(T item: data) {
			this.insert(item);
		}
	}

	/**
	 * Creates a BinarySearchTree initialized with data.
	 *
	 * @param data the items to insert in the BinarySearchTree
	 */
	public BinarySearchTree(List<T> data) {
		this();

		for(T item: data) {
			this.insert(item);
		}
	}

	/**
	 * Creates a BinarySearchTree initialized with data. Comparator will be used for
	 * all comparison related to the new BinarySearchTree.
	 *
	 * @param data the items to insert in the BinarySearchTree
	 * @param comparator a Comparator that should be used to compare items
	 */
	public BinarySearchTree(List<T> data, Comparator<T> comparator) {
		this(comparator);

		for(T item: data) {
			this.insert(item);
		}
	}

	/**
	 * Inserts item into this BinarySearchTree.
	 *
	 * @param item the item to insert
	 */
	public void insert(T item) {
		if(head == null) {
			head = new BinaryTreeNode<>();
			head.item = item;

			return;
		}

		BinaryTreeNode<T> parentNode = head;
		BinaryTreeNode<T> currentNode = head;

		Comparison comparison;

		do {
			parentNode = currentNode;

			comparison = compare(item, currentNode.item);

			if(comparison == Comparison.LESS || comparison == Comparison.EQUAL) {
				currentNode = currentNode.leftChild;
			} else {
				currentNode = currentNode.rightChild;
			}
		} while(currentNode != null);

		BinaryTreeNode<T> node = new BinaryTreeNode<>();
		node.parent = parentNode;
		node.item = item;

		if(comparison == Comparison.LESS || comparison == Comparison.EQUAL) {
			parentNode.leftChild = node;
		} else {
			parentNode.rightChild = node;
		}
	}

	/**
	 * Returns if item is stored in this BinarySearchTree.
	 *
	 * @param item the item to search fore
	 *
	 * @return true iff item is stored in this BinarySearchTree
	 */
	public boolean has(T item) {
		return (findNode(item) != null);
	}

	/**
	 * Returns the minimum value in this BinarySearchTree.
	 *
	 * @return the item with the minimum value
	 */
	public T getMinimum() {
		if(head == null) {
			return null;
		}

		BinaryTreeNode<T> currentNode = head;

		while(currentNode.leftChild != null) {
			currentNode = currentNode.leftChild;
		}

		return currentNode.item;
	}

	/**
	 * Returns the item with the maximum value in this BinarySearchTree.
	 *
	 * @return the item with the maximum value
	 */
	public T getMaximum() {
		if(head == null) {
			return null;
		}

		BinaryTreeNode<T> currentNode = head;

		while(currentNode.rightChild != null) {
			currentNode = currentNode.rightChild;
		}

		return currentNode.item;
	}

	/**
	 * Removes item from this BinarySearchTree.
	 *
	 * @param item the item to remove
	 */
	public void remove(T item) {
		BinaryTreeNode<T> node = findNode(item);

		if(node == null) {
			return;
		}

		BinaryTreeNode<T> replacement;

		if(node.rightChild == null) {
			replacement = (BinaryTreeNode<T>) node.leftChild;
		} else if(node.leftChild == null) {
			replacement = (BinaryTreeNode<T>) node.rightChild;
		} else {
			replacement = findSuccessor(node);

			if(replacement == node.rightChild) {
				replacement.leftChild = node.leftChild;
			} else {
				replacement.parent.leftChild = replacement.rightChild;

				replacement.leftChild = node.leftChild;
				replacement.rightChild = node.rightChild;
			}
		}

		if(replacement != null) {
			replacement.parent = node.parent;
		}

		if(node == head) {
			head = replacement;

			return;
		}

		if(node.parent.leftChild == node) {
			node.parent.leftChild = replacement;
		} else {
			node.parent.rightChild = replacement;
		}
	}

	/**
	 * Searches this BinarySearchTree for the node that is storing item.
	 *
	 * @param item the item to find
	 *
	 * @return the node that is storing item or null if no node is storing item
	 */
	private BinaryTreeNode<T> findNode(T item) {
		BinaryTreeNode<T> currentNode = head;
		Comparison comparison;

		while(currentNode != null && (comparison = compare(item, currentNode.item)) != Comparison.EQUAL) {
			if(comparison == Comparison.LESS) {
				currentNode = currentNode.leftChild;
			} else {
				currentNode = currentNode.rightChild;
			}
		}

		return currentNode;
	}

	/**
	 * Finds root's successor. If root has no successor, null is returned.
	 * 
	 * @param root the node for which a successor should be found
	 * 
	 * @return root's successor or null if root has no successor
	 */
	private BinaryTreeNode<T> findSuccessor(BinaryTreeNode<T> root) {
		if(root.rightChild == null) {
			return null;
		}

		BinaryTreeNode<T> successor = root.rightChild;
		while(successor.leftChild != null) {
			successor = successor.leftChild;
		}

		return successor;
	}

	/**
	 * Compares lfs (left-hand side operand) to rhs (right-hand side operand).
	 * 
	 * @throws (ClassCastException) If lhs and/or rhs cannot be compared.
	 *
	 * @param lhs the operand on the left-hand side of the comparison operator
	 * @param rhs the operand on the right-hand side of the comparison operator
	 *
	 * @return the result of comparing lhs and rhs
	 */
	@SuppressWarnings("unchecked")
	private Comparison compare(T lhs, T rhs) {
		int comparison = 0;
		boolean comparisonMade = false;

		if(comparator != null) {
			comparison = comparator.compare(lhs, rhs);
			comparisonMade = true;
		}

		if(!comparisonMade && lhs instanceof Comparable && rhs instanceof Comparable) {
			comparison = ((Comparable<T>) lhs).compareTo(rhs);
			comparisonMade = true;
		}

		if(comparisonMade) {
			return Comparison.create(comparison);
		}

		throw new ClassCastException("Element cannot be compared.");
	}
}