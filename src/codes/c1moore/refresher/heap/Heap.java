package codes.c1moore.refresher.heap;

/**
 * Heap defines an interface for all Heaps; min, max, or otherwise; as well as
 * some useful methods common to all Heaps.
 *
 * How a Heap compares two elements is up to the Heap implementation. It may
 * require T to implement the Comparable interface, or it could perform
 * comparison, for example, with a lambda passed to the constructor that
 * compares two elements.
 *
 * However, in C++ or another language that supports operator overloading, it
 * would be preferable to simply overload the comparison operators for all
 * elements inserted into the Heap. This would add another layer of abstraction
 * to the Heap, as it would no longer have to worry about how comparisons were
 * made, it would leave that up to the elements.
 */
public abstract class Heap<T> {
	/**
	 * Inserts element into its appropriate position within the Heap.
	 *
	 * @param element the element to insert in the Heap
	 */
	public abstract void insert(T element);

	/**
	 * Returns the first (root) element in the Heap without removing it.
	 *
	 * @return the root element in the Heap
	 */
	public abstract T peek();

	/**
	 * Removes the first (root) element in the Heap and returns it.
	 *
	 * @return the root element in the Heap
	 */
	public abstract T pop();

	/**
	 * Returns if the Heap is empty.
	 *
	 * @return true iff the Heap is empty
	 */
	public abstract boolean isEmpty();

	/**
	 * Returns the index of the specified node's parent. If the specified node is
	 * the root of the Heap (i.e. index is 0), 0 is returned.
	 *
	 * @param index the index of the child whose parent index should be returned
	 *
	 * @return the index of the parent of the index-th node
	 */
	protected int getParentIndex(int index) {
		return ((index - 1) / 2);
	}

	/**
	 * Returns the index of the specified node's left child. As this method does not
	 * know the number of nodes in the Heap, it does not check to determine if the
	 * left child exists.
	 *
	 * @param index the index of the parent node
	 *
	 * @return the index of the left child of the index-th node
	 */
	protected int getLeftChildIndex(int index) {
		return ((index * 2) + 1);
	}

	/**
	 * Returns the index of the specified node's right child. As this method does
	 * not know the number of nodes in the Heap, it does not check to determine if
	 * the right child exists.
	 *
	 * @param index the index of the parent node
	 *
	 * @return the index of the right child of the index-th node
	 */
	protected int getRightChildIndex(int index) {
		return ((index * 2) + 2);
	}
}