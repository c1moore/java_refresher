package codes.c1moore.refresher.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import codes.c1moore.refresher.heap.MaxHeap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("MaxHeap")
class MaxHeapTest extends HeapTest {
	private boolean comparatorCalled;
	private Comparator<Integer> comparator;

	protected MaxHeap<Integer> createHeap() {
		return new MaxHeap<Integer>();
	}

	@BeforeEach
	void beforeEach() {
		comparatorCalled = false;

		comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer lhs, Integer rhs) {
				comparatorCalled = true;

				return (lhs - rhs);
			}
		};
	}

	@Test
	@DisplayName("should be able to create an empty MaxHeap")
	void testDefaultConstructor() {
		new MaxHeap<Integer>();
	}

	@Test
	@DisplayName("should be able to create an empty MaxHeap with a Compartor")
	void testCreateWithComparator() {
		MaxHeap<Integer> heap = new MaxHeap<>(comparator);

		heap.insert(5);

		assertTrue(comparatorCalled);
	}

	@Test
	@DisplayName("should be able to create a new MaxHeap from an existing one")
	void testCreateFromExistingHeap() {
		MaxHeap<Integer> original = new MaxHeap<>(new Integer[] { 5, 7, 11 });
		MaxHeap<Integer> heap = new MaxHeap<>(original);

		while(!original.isEmpty()) {
			assertEquals(original.pop(), heap.pop());
		}
	}

	@Test
	@DisplayName("should use the existing MaxHeap's Comparator")
	void testCreateFromExistingWithComparator() {
		MaxHeap<Integer> original = new MaxHeap<>(new Integer[] { 5, 7, 11 }, comparator);

		comparatorCalled = false;

		MaxHeap<Integer> heap = new MaxHeap<>(original);
		heap.insert(89);

		assertTrue(comparatorCalled);
	}

	@Test
	@DisplayName("should initialize the new MaxHeap with the array of data")
	void testCreateWithArray() {
		final Integer[] values = new Integer[] { 7, 5, 3, 1 };
		MaxHeap<Integer> heap = new MaxHeap<>(values);

		for(Integer value: values) {
			assertEquals(value, heap.pop());
		}
	}

	@Test
	@DisplayName("should initialize the new MaxHeap with the array and use the provided Comparator")
	void testCreateWithArrayAndComparator() {
		final Integer[] values = new Integer[] { 7, 5, 3, 1 };

		assertFalse(comparatorCalled);

		MaxHeap<Integer> heap = new MaxHeap<>(values, comparator);

		assertTrue(comparatorCalled);

		for(Integer value: values) {
			assertEquals(value, heap.pop());
		}
	}

	@Test
	@DisplayName("should initialize the new MaxHeap with the provided List")
	void testCreateWithList() {
		final List<Integer> values = new ArrayList<>();

		values.add(9);
		values.add(7);
		values.add(6);
		values.add(2);

		MaxHeap<Integer> heap = new MaxHeap<>(values);

		for(Integer value: values) {
			assertEquals(value, heap.pop());
		}
	}

	@Test
	@DisplayName("should initialize the new MaxHeap with the provided List and Comparator")
	void testCreateWithListAndComparator() {
		final List<Integer> values = new ArrayList<>();

		values.add(9);
		values.add(3);
		values.add(2);
		values.add(1);

		assertFalse(comparatorCalled);

		MaxHeap<Integer> heap = new MaxHeap<>(values, comparator);

		assertTrue(comparatorCalled);

		for(Integer value: values) {
			assertEquals(value, heap.pop());
		}
	}

	@Nested
	@DisplayName("Instance Methods")
	class MaxHeapInstanceMethodSuite {
		MaxHeap<Integer> heap;

		@BeforeEach
		void beforeEach() {
			heap = createHeap();
		}

		@Nested
		@DisplayName("insert(T)")
		class MaxHeapInsertSuite {
			@Test
			@DisplayName("should be able to insert multiple values")
			void testInsertMultipleValues() {
				final Integer[] values = new Integer[] { 7, 5, 3, 1 };

				for(Integer value: values) {
					heap.insert(value);
				}

				for(Integer value: values) {
					assertEquals(value, heap.pop());
				}
			}

			@Test
			@DisplayName("should be able to insert duplicate items")
			void testInsertDuplicates() {
				final Integer insertedValue = 7;

				heap.insert(insertedValue);
				heap.insert(insertedValue);

				assertEquals(insertedValue, heap.pop());
				assertEquals(insertedValue, heap.pop());
			}
		}

		@Nested
		@DisplayName("pop()")
		class MaxHeapPopSuite {
			@Test
			@DisplayName("should pop items in decreasing order when inserted in decreasing order")
			void testDecreasingOrderInsertion() {
				final Integer[] values = new Integer[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 };

				for(Integer value: values) {
					heap.insert(value);
				}

				for(Integer value: values) {
					assertEquals(value, heap.pop());
				}
			}

			@Test
			@DisplayName("should pop items in decreasing order when inserted in increasing order")
			void testIncreasingOrderInsertion() {
				final Integer[] values = new Integer[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 };

				for(int index = values.length - 1; index >= 0; index--) {
					heap.insert(values[index]);
				}

				for(Integer value: values) {
					assertEquals(value, heap.pop());
				}
			}

			@Test
			@DisplayName("should pop items in decreasing order when inserted in random order")
			void testRandomOrderInsertion() {
				final int max = 100;

				int counter = 20;
				while(counter-- > 0) {
					heap.insert((int) (Math.random() * max));
				}

				int previousNumber = max + 1;
				while(!heap.isEmpty()) {
					assertTrue(previousNumber > heap.pop());
				}
			}
		}
	}
}
