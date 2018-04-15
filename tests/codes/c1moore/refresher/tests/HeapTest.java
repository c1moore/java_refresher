package codes.c1moore.refresher.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import codes.c1moore.refresher.heap.Heap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

/**
 * HeapTest does not test the actual Heap abstract class, but rather provides
 * some generic tests that all Heaps should pass.
 * 
 * @author c1moore
 *
 */
@DisplayName("Heap")
abstract class HeapTest {
	/**
	 * Creates and returns a new Heap. Basically we're using the Factory Method DP
	 * here.
	 * 
	 * @return a new Heap that can be used for testing purposes
	 */
	abstract protected Heap<Integer> createHeap();

	@Nested
	@DisplayName("Instance Methods")
	class HeapInstanceMethodSuite {
		Heap<Integer> heap;

		@BeforeEach
		void beforeEach() {
			heap = createHeap();
		}

		@Nested
		@DisplayName("insert(T)")
		class HeapInsertSuite {
			@Test
			@DisplayName("should be able to insert an item")
			void testInsert() {
				final Integer insertedValue = 5;

				heap.insert(insertedValue);

				assertEquals(insertedValue, heap.pop());
			}
		}

		@Nested
		@DisplayName("peek()")
		class HeapPeekSuite {
			@Test
			@DisplayName("should not remove the item")
			void testMultiplePeeks() {
				final Integer value = 7;

				assertTrue(heap.isEmpty());

				heap.insert(value);

				assertEquals(value, heap.peek());
				assertEquals(value, heap.peek());
			}

			@Test
			@DisplayName("should return null if the list is empty")
			void testEmptyHeap() {
				assertTrue(heap.isEmpty());
				assertEquals(null, heap.peek());
			}
		}

		@Nested
		@DisplayName("pop()")
		class HeapPopSuite {
			@Test
			@DisplayName("should return null if the list is empty")
			void testEmptyHeap() {
				assertTrue(heap.isEmpty());
				assertEquals(null, heap.pop());
			}
		}

		@Nested
		@DisplayName("isEmpty()")
		class HeapEmptySuite {
			@Test
			@DisplayName("should return true if the Heap is empty")
			void testEmptyHeap() {
				assertTrue(heap.isEmpty());
			}

			@Test
			@DisplayName("should return false if the Heap is not empty")
			void testNonEmptyHeap() {
				heap.insert(7);

				assertFalse(heap.isEmpty());
			}

			@Test
			@DisplayName("should return true if the Heap is emptied")
			void testEmptiedHeap() {
				heap.insert(7);

				heap.pop();

				assertTrue(heap.isEmpty());
			}
		}
	}
}
