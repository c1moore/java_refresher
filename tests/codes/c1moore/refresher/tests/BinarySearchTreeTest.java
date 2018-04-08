package codes.c1moore.refresher.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;

import codes.c1moore.refresher.bst.BinarySearchTree;

@DisplayName("BinarySearchTree")
class BSTTestSuite {
	boolean comparatorCalled;
	Comparator<Integer> comparator;
	
	private List<Integer> createDataList(int size) {
		Random rand = new Random();
		
		List<Integer> data = new ArrayList<>();
		
		rand.ints(size).forEach((int number) -> {
			data.add(number);
		});
		
		return data;
	}
	
	@BeforeEach
	void beforeEach() {
		comparatorCalled = false;
		
		comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer lhs, Integer rhs) {
				comparatorCalled = true;
				
				return rhs - lhs;
			}
		};
	}
	
	@Test
	@DisplayName("should be able to create a BinarySearchTree")
	void create() {
		new BinarySearchTree<Integer>();
	}
	
	@Test
	@DisplayName("should be able to create a BinarySearchTree with a Comparator")
	void createWithComparator() {
		assertFalse(comparatorCalled);
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(comparator);
		
		bst.insert(1);
		bst.insert(2);
		
		assertTrue(comparatorCalled);
	}
	
	@Test
	@DisplayName("should be able to create a BinarySearchTree with an array of existing values")
	void createWithArray() {
		Integer numbers[] = {1, 3, 9, 7, 5, 2};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>(numbers);
		
		for(Integer number: numbers) {
			assertTrue(bst.has(number));
		}
	}
	
	@Test
	@DisplayName("should be able to create a BinarySearchTree with a Comparator and an array")
	void createWithComparatorAndArray() {
		assertFalse(comparatorCalled);
		
		Integer numbers[] = {7, 3, 9, 2, 1, 6, 8, 10};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>(numbers, comparator);
		
		assertTrue(comparatorCalled);
		
		for(Integer number:numbers) {
			assertTrue(bst.has(number));
		}
	}
	
	@Test
	@DisplayName("should be able to create a BinarySearchTree with a list of existing values")
	void createWithList() {
		List<Integer> numbers = createDataList(5);
		BinarySearchTree<Integer> bst = new BinarySearchTree<>(numbers);
		
		for(Integer number: numbers) {
			assertTrue(bst.has(number));
		}
	}
	
	@Test
	@DisplayName("should be able to create a BinarySearchTree with a list of existing values")
	void createWithCompartorAndList() {
		assertFalse(comparatorCalled);
		
		List<Integer> numbers = createDataList(5);
		BinarySearchTree<Integer> bst = new BinarySearchTree<>(numbers, comparator);
		
		assertTrue(comparatorCalled);
		
		for(Integer number: numbers) {
			assertTrue(bst.has(number));
		}
	}
	
	@Nested
	@DisplayName("Instance Methods")
	class BSTInstanceTestSuite {
		BinarySearchTree<Integer> bst;
		
		@BeforeEach
		void beforeEach() {
			bst = new BinarySearchTree<>();
		}
		
		@Nested
		@DisplayName("insert(T)")
		class BSTInsertTestSuite {
			@Test
			@DisplayName("should be able to insert a node when empty")
			void testEmptyTree() {
				final int number = 5;
				
				bst.insert(number);
				
				assertTrue(bst.has(number));
			}
			
			@Test
			@DisplayName("should be able to insert a multiple nodes")
			void testInsertMultipleNodes() {
				final int[] numbers = {7, 5, 9, 6, 8};
				
				for(int number: numbers) {
					bst.insert(number);
				}
				
				for(int number: numbers) {
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should work with a custom Comparator")
			void testInsertWithComparator() {
				final int[] numbers = {7, 5, 9, 6, 8};
				
				bst = new BinarySearchTree<>(comparator);
				
				for(int number: numbers) {
					bst.insert(number);
				}
				
				for(int number: numbers) {
					assertTrue(bst.has(number));
				}
			}
		}
		
		@Nested
		@DisplayName("has(T)")
		class BSTInstanceHasTestSuite {
			List<Integer> data;
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyBST")) {
					return;
				}
				
				data = createDataList(50);
				
				for(int number: data) {
					bst.insert(number);
				}
			}
			
			@Test
			@Tag("EmptyBST")
			@DisplayName("should return false for an empty BST")
			void testEmpty() {
				assertFalse(bst.has(7));
			}
			
			@Test
			@Tag("EmptyBST")
			@DisplayName("should return false for an item not in the BST")
			void testMissingItem() {
				bst.insert(7);
				bst.insert(2);
				bst.insert(15);
				
				assertFalse(bst.has(18));
				assertFalse(bst.has(1));
			}
			
			@Test
			@DisplayName("should return true for the root node")
			void testRootNode() {
				assertTrue(bst.has(data.get(0)));
			}
			
			@Test
			@DisplayName("should return true for an item in the BST")
			void testInsertedItems() {
				for(int number: data) {
					assertTrue(bst.has(number));
				}
			}
		}
		
		@Nested
		@DisplayName("getMinimum()")
		class BSTInstanceMinimumTestSuite {
			@Test
			@DisplayName("should return null when the tree is empty")
			void testEmptyTree() {
				assertEquals(null, bst.getMinimum());
			}
			
			@Test
			@DisplayName("should return the root when it is the minimum")
			void testRoot() {
				final int root = 0;
				
				bst.insert(root);
				bst.insert(root + 5);
				bst.insert(root + 10);
				
				assertTrue(root == bst.getMinimum());
			}
			
			@Test
			@DisplayName("should return the minimum")
			void testMinimum() {
				bst.insert(5);
				bst.insert(10);
				
				int minimum = 0;
				bst.insert(minimum);
				assertTrue(minimum == bst.getMinimum());
				
				bst.insert(1);
				
				minimum = -5;
				bst.insert(minimum);
				assertTrue(minimum == bst.getMinimum());
			}
		}
		
		@Nested
		@DisplayName("getMaximum()")
		class BSTInstanceMaximumTestSuite {
			@Test
			@DisplayName("should return null when the tree is empty")
			void testEmptyTree() {
				assertEquals(null, bst.getMaximum());
			}
			
			@Test
			@DisplayName("should return the root when it is the maximum")
			void testRoot() {
				final int root = 427;
				
				bst.insert(root);
				bst.insert(root - 5);
				bst.insert(root - 10);
				
				assertTrue(root == bst.getMaximum());
			}
			
			@Test
			@DisplayName("should return the maximum")
			void testMinimum() {
				bst.insert(5);
				bst.insert(10);
				
				int maximum = 427;
				bst.insert(maximum);
				assertTrue(maximum == bst.getMaximum());
				
				bst.insert(55);
				
				maximum = 515;
				bst.insert(maximum);
				assertTrue(maximum == bst.getMaximum());
			}
		}
		
		@Nested
		@DisplayName("remove(T)")
		class BSTInstanceRemoveTestSuite {
			@Test
			@DisplayName("should return successfully when the tree is empty")
			void testEmptyTree() {
				bst.remove(10);
			}
			
			@Test
			@DisplayName("should return successfully when the item does not exist")
			void testUnknownItem() {
				Integer[] numbers = {10, 5, 15};
				
				bst = new BinarySearchTree<>(numbers);
				
				bst.remove(150);
				
				for(Integer number: numbers) {
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove the root node")
			void testRemoveRoot() {
				Integer[] numbers = {5, 15, 20, 14, 13, 12, 11};
				final int root = 10;
				
				bst.insert(root);
				for(Integer number: numbers) {
					bst.insert(number);
				}
				
				bst.remove(root);
				
				assertFalse(bst.has(root));
				
				for(Integer number: numbers) {
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove the only node")
			void testRemoveLastNode() {
				final int number = 10;
				
				bst.insert(number);
				bst.remove(number);
				
				assertFalse(bst.has(number));
			}
			
			@Test
			@DisplayName("should remove a leaf node")
			void testLeafNode() {
				Integer[] numbers = {10, 5, 15, 59};
				final int leaf = 7;
				
				bst = new BinarySearchTree<>(numbers);
				bst.insert(leaf);
				
				bst.remove(leaf);
				
				assertFalse(bst.has(leaf));
				
				for(Integer number: numbers) {
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove a node with a left child")
			void testNodeWithLeftChild() {
				Integer[] numbers = {10, 5, 15, 8, 6};
				final int removedItem = numbers[3];
				
				bst = new BinarySearchTree<>(numbers);
				
				bst.remove(removedItem);
				
				assertFalse(bst.has(removedItem));
				
				for(Integer number: numbers) {
					if(number == removedItem) {
						continue;
					}
					
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove a node with a right child")
			void testNodeWithRightChild() {
				Integer[] numbers = {10, 5, 15, 8, 9};
				final int removedItem = numbers[3];
				
				bst = new BinarySearchTree<>(numbers);
				
				bst.remove(removedItem);
				
				assertFalse(bst.has(removedItem));
				
				for(Integer number: numbers) {
					if(number == removedItem) {
						continue;
					}
					
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove a node with 2 children")
			void testNodeWith2Children() {
				Integer[] numbers = {10, 5, 15, 8, 6, 9};
				final int removedItem = numbers[3];
				
				bst = new BinarySearchTree<>(numbers);
				
				bst.remove(removedItem);
				
				assertFalse(bst.has(removedItem));
				
				for(Integer number: numbers) {
					if(number == removedItem) {
						continue;
					}
					
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove a node with a deep successor")
			void testNodeWithDeepSuccessor() {
				Integer[] numbers = {10, 5, 15, 7, 6, 9, 8};
				final int removedItem = numbers[3];
				
				bst = new BinarySearchTree<>(numbers);
				
				bst.remove(removedItem);
				
				assertFalse(bst.has(removedItem));
				
				for(Integer number: numbers) {
					if(number == removedItem) {
						continue;
					}
					
					assertTrue(bst.has(number));
				}
			}
			
			@Test
			@DisplayName("should remove a node with a successor that has a child")
			void testSuccessorWithChild() {
				Integer[] numbers = {50, 0, 100, 25, 12, 35, 30, 40, 26, 27};
				final int removedItem = numbers[3];
				
				bst = new BinarySearchTree<>(numbers);
				
				bst.remove(removedItem);
				
				assertFalse(bst.has(removedItem));
				
				for(Integer number: numbers) {
					if(number == removedItem) {
						continue;
					}
					
					assertTrue(bst.has(number));
				}
			}
		}
	}
}
