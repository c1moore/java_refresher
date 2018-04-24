package codes.c1moore.refresher.bst;

/**
 * BinaryTreeNode is a node within a binary tree. Since this tree node will be
 * used in Binary Search Trees (hence the package) it will maintain a reference
 * to its parent node.
 */
class BinaryTreeNode<T> {
	protected T item;	// The item being stored.

	protected BinaryTreeNode<T> parent;	// This BinaryTreeNode's parent node.

	protected BinaryTreeNode<T> leftChild;	// The left child.
	protected BinaryTreeNode<T> rightChild;	// The right child.
}