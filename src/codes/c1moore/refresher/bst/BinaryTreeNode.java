package codes.c1moore.refresher.bst;

/**
 * BinaryTreeNode is a node within a binary tree.
 */
class BinaryTreeNode<T> {
	protected T item;	// The item being stored.

	protected BinaryTreeNode<T> leftChild;	// The left child.
	protected BinaryTreeNode<T> rightChild;	// The right child.
}