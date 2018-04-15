package codes.c1moore.refresher.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * MaxHeap implements a standard max heap.
 * 
 * Sorting in MaxHeap is performed one of two ways. These methods, in order of
 * of preference, are
 * <ol>
 * <li>A Comparator passed to the MaxHeap constructor</li>
 * <li>The `compareTo()` method on one of the elements being compared, if they
 * both implement the Comparable interface</li>
 * </ol>
 */
public class MaxHeap<T> extends Heap<T> {
	private ArrayList<T> heap;
	private int heapSize;
	private Comparator<? super T> comparator = null;

	/**
	 * Creates a new, empty MaxHeap.
	 */
	public MaxHeap() {
		heap = new ArrayList<>();
		heapSize = 0;
	}

	/**
	 * Creates a new MaxHeap with the same elements as original.
	 *
	 * @param original another MaxHeap from which this MaxHeap should be generated
	 */
	public MaxHeap(MaxHeap<T> original) {
		heap = new ArrayList<>(original.heapSize);

		heap.ensureCapacity(original.heapSize);

		for(T item: original.heap) {
			heap.add(item);
		}

		heapSize = original.heapSize;
		comparator = original.comparator;
	}

	/**
	 * Creates a new MaxHeap that uses comparator to compare elements. comparator
	 * takes priority over other comparison methods when comparing nodes.
	 *
	 * @param comparator the Comparator to use to determine the position of elements
	 *            within the new MaxHeap
	 */
	public MaxHeap(Comparator<? super T> comparator) {
		this();

		this.comparator = comparator;
	}

	/**
	 * Creates a new MaxHeap generated from elements.
	 *
	 * @param elements an array of the elements to insert in the new MaxHeap
	 */
	public MaxHeap(T[] elements) {
		this();

		heap.ensureCapacity(elements.length);

		for(T element: elements) {
			this.insert(element);
		}
	}

	/**
	 * Creates a new MaxHeap generated from elements.
	 *
	 * @param elements an array of the elements to insert in the new MaxHeap
	 * @param comparator the Comparator to use to determine the position of elements
	 *            within the new MaxHeap
	 */
	public MaxHeap(T[] elements, Comparator<T> comparator) {
		this(comparator);

		heap.ensureCapacity(elements.length);

		for(T element: elements) {
			this.insert(element);
		}
	}

	/**
	 * Creates a new MaxHeap generated from elements.
	 *
	 * @param elements a List of the elements to insert in MaxHeap
	 */
	public MaxHeap(List<T> elements) {
		this();

		heap.ensureCapacity(elements.size());

		for(T element: elements) {
			insert(element);
		}
	}

	/**
	 * Creates a new MaxHeap generated from elements.
	 *
	 * @param elements a List of the elements to insert in MaxHeap
	 * @param comparator the Comparator to use to determine the position of elements
	 *            within the new MaxHeap
	 */
	public MaxHeap(List<T> elements, Comparator<T> comparator) {
		this(comparator);

		heap.ensureCapacity(elements.size());

		for(T element: elements) {
			insert(element);
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void insert(T element) {
		int elementIndex = heapSize;

		heap.add(element);
		heapSize++;

		for(int parentIndex = getParentIndex(heapSize); isGreaterThan(element, heap.get(parentIndex)); parentIndex = getParentIndex(parentIndex)) {
			heap.set(elementIndex, heap.get(parentIndex));
			heap.set(parentIndex, element);

			elementIndex = parentIndex;
		}
	}

	/**
	 * @inheritDoc
	 */
	public T peek() {
		if(heapSize == 0) {
			return null;
		}

		return heap.get(0);
	}

	/**
	 * @inheritDoc
	 */
	public T pop() {
		if(heapSize == 0) {
			return null;
		}

		T maxValue = heap.get(0);
		heapSize--;

		T value = heap.get(heapSize);

		heap.set(0, value);
		heap.remove(heapSize);

		int index = 0;

		while(true) {
			int leftChildIndex = getLeftChildIndex(index);
			int rightChildIndex = getRightChildIndex(index);

			if(leftChildIndex >= heapSize) {
				break;
			}

			T leftChild = heap.get(leftChildIndex);

			if(rightChildIndex >= heapSize) {
				if(isGreaterThan(leftChild, value)) {
					heap.set(leftChildIndex, value);
					heap.set(index, leftChild);
				}

				break;
			}

			T rightChild = heap.get(rightChildIndex);

			if(isGreaterThan(value, leftChild) && isGreaterThan(value, rightChild)) {
				break;
			}

			if(isGreaterThan(leftChild, rightChild)) {
				heap.set(leftChildIndex, value);
				heap.set(index, leftChild);

				index = leftChildIndex;
			} else {
				heap.set(rightChildIndex, value);
				heap.set(index, rightChild);

				index = rightChildIndex;
			}
		}

		return maxValue;
	}

	/**
	 * @inheritDoc
	 */
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/**
	 * Returns whether lhs (left-hand side operand) is greater than rhs (right-hand
	 * side operand).
	 *
	 * @param lhs the operand on the left-hand side of the greater-than (>) operator
	 * @param rhs the operand on the right-hand side of the greater-than (>)
	 *            operator
	 *
	 * @return true iff lhs is greater than rhs
	 */
	@SuppressWarnings("unchecked")
	private boolean isGreaterThan(T lhs, T rhs) {
		if(comparator != null) {
			return (comparator.compare(lhs, rhs) > 0);
		}

		return (((Comparable<T>) lhs).compareTo(rhs) > 0);
	}
}