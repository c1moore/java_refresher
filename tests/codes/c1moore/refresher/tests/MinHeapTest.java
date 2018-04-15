package codes.c1moore.refresher.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import codes.c1moore.refresher.heap.MinHeap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("MinHeap")
class MinHeapTest extends HeapTest {
	protected MinHeap<Integer> createHeap() {
		return new MinHeap<Integer>();
	}

	@Test
	@DisplayName("should be able to create an empty MinHeap")
	void testDefaultConstructor() {
		new MinHeap<Integer>();
	}

	@Test
	@DisplayName("should be able to create a new MinHeap from an existing one")
	void testCreateFromExistingHeap() {
		MinHeap<Integer> original = new MinHeap<>(new Integer[] { 5, 7, 11 });
		MinHeap<Integer> heap = new MinHeap<>(original);

		while(!original.isEmpty()) {
			assertEquals(original.pop(), heap.pop());
		}
	}

	@Test
	@DisplayName("should initialize the new MaxHeap with the array of data")
	void testCreateWithArray() {
		final Integer[] values = new Integer[] { 1, 2, 3, 9 };

		MinHeap<Integer> heap = new MinHeap<>(values);

		for(Integer value: values) {
			assertEquals(value, heap.pop());
		}
	}

	@Test
	@DisplayName("should initialize the new MaxHeap with the provided List")
	void testCreateWithList() {
		final List<Integer> values = new ArrayList<>();

		values.add(2);
		values.add(6);
		values.add(7);
		values.add(9);

		MinHeap<Integer> heap = new MinHeap<>(values);

		for(Integer value: values) {
			assertEquals(value, heap.pop());
		}
	}

	@Nested
	@DisplayName("Instance Methods")
	class MinHeapInstanceMethodSuite {
		MinHeap<Integer> heap;

		@BeforeEach
		void beforeEach() {
			heap = createHeap();
		}

		@Nested
		@DisplayName("insert(T)")
		class MinHeapInsertSuite {
			@Test
			@DisplayName("should be able to insert multiple values")
			void testInsertMultipleValues() {
				final Integer[] values = new Integer[] { 1, 3, 5, 7 };

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
		class MinHeapPopSuite {
			@Test
			@DisplayName("should pop items in increasing order when inserted in increasing order")
			void testDecreasingOrderInsertion() {
				final Integer[] values = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

				for(Integer value: values) {
					heap.insert(value);
				}

				for(Integer value: values) {
					assertEquals(value, heap.pop());
				}
			}

			@Test
			@DisplayName("should pop items in increasing order when inserted in decreasing order")
			void testIncreasingOrderInsertion() {
				final Integer[] values = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

				for(int index = values.length - 1; index >= 0; index--) {
					heap.insert(values[index]);
				}

				for(Integer value: values) {
					assertEquals(value, heap.pop());
				}
			}

			@Test
			@DisplayName("should pop items in increasing order when inserted in random order")
			void testRandomOrderInsertion() {
				final int max = 100;

				int counter = 20;
				while(counter-- > 0) {
					heap.insert((int) (Math.random() * max));
				}

				int previousNumber = -1;
				while(!heap.isEmpty()) {
					assertTrue(previousNumber < heap.pop());
				}
			}
		}
	}
}
