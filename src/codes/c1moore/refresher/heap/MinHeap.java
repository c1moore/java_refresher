package codes.c1moore.refresher.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * MinHeap provides a standard implementation of a min heap.
 * 
 * An approach different from that used by MaxHeap was purposely chosen. Instead
 * of allowing any generic type, parameterized types must implement the
 * Comparable interface. While his does limit the types that MinHeap can use, it
 * is more secure.
 */
public class MinHeap<T extends Comparable<T>> extends Heap<T> {
	private ArrayList<T> heap;
	private int heapSize;

	/**
	 * Creates a new, empty MinHeap.
	 */
	public MinHeap() {
		heap = new ArrayList<>();
		heapSize = 0;
	}

	/**
	 * Creates a new MinHeap with the same elements as original.
	 *
	 * @param original another MinHeap from which this MinHeap should be generated
	 */
	public MinHeap(MinHeap<T> original) {
		heap = new ArrayList<>(original.heapSize);

		heap.ensureCapacity(original.heapSize);

		for(T item: original.heap) {
			heap.add(item);
		}

		heapSize = original.heapSize;
	}

	/**
	 * Creates a new MinHeap that has all the items specified in elements.
	 *
	 * @param elements an array of the elements to insert in the new MinHeap
	 */
	public MinHeap(T[] elements) {
		this();

		heap.ensureCapacity(elements.length);

		for(T element: elements) {
			insert(element);
		}
	}

	/**
	 * Creates a new MinHeap that has all the items specified in elements.
	 *
	 * @param elements a List of the elements to insert in the new MinHeap
	 */
	public MinHeap(List<T> elements) {
		this();

		heap.ensureCapacity(elements.size());

		for(T element: elements) {
			insert(element);
		}
	}

	/**
	 * @inheritDoc
	 */
	public void insert(T element) {
		int elementIndex = heapSize;

		heap.add(element);
		heapSize++;

		for(int parentIndex = getParentIndex(heapSize); element.compareTo(heap.get(parentIndex)) < 0; parentIndex = getParentIndex(parentIndex)) {
			T parent = heap.get(parentIndex);

			heap.set(parentIndex, element);
			heap.set(elementIndex, parent);

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

		T minValue = heap.get(0);
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
				if(value.compareTo(leftChild) > 0) {
					heap.set(leftChildIndex, value);
					heap.set(index, leftChild);
				}

				break;
			}

			T rightChild = heap.get(rightChildIndex);

			if(value.compareTo(leftChild) < 0 && value.compareTo(rightChild) < 0) {
				break;
			}

			if(leftChild.compareTo(rightChild) < 0) {
				heap.set(leftChildIndex, value);
				heap.set(index, leftChild);

				index = leftChildIndex;
			} else {
				heap.set(rightChildIndex, value);
				heap.set(index, rightChild);

				index = rightChildIndex;
			}
		}

		return minValue;
	}

	/**
	 * @inheritDoc
	 */
	public boolean isEmpty() {
		return (heapSize == 0);
	}
}