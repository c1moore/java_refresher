package codes.c1moore.refresher.bst;

/**
 * BiDriectionalBinaryTreeNode is a BinaryTreeNode that has a reference to its parent.
 */
class BiDirectionalBinaryTreeNode<T> extends BinaryTreeNode<T> {
	protected BinaryTreeNode<T> parent;	// This BinaryTreeNode's parent node.
}