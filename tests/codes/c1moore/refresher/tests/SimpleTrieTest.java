package codes.c1moore.refresher.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;

import codes.c1moore.refresher.trie.SimpleTrie;

/**
 * @author c1moore
 *
 */
@DisplayName("SimpleTrie")
class SimpleTrieSuite {
	@Test
	@DisplayName("should be able to create an instance")
	void canCreateInstance() {
		new SimpleTrie(15, (Character c) -> {
			return 1;
		});
	}
	
	@Nested
	@DisplayName("Instance Methods")
	class SimpleTrieInstanceSuite {
		private static final int ALPHABET_SIZE = 26;
		
		private final String validStrings[] = {"a", "ab", "defgh", "dcba"};
		private final String invalidStrings[] = {"/", "-_*", "!nv@1!d"};
		
		SimpleTrie trie;
		
		@BeforeEach
		void beforeEach() {
			trie = new SimpleTrie(SimpleTrieInstanceSuite.ALPHABET_SIZE, (Character c) -> {
				return (c - 'a');
			});
		}
		
		@Nested
		@DisplayName("add(String)")
		class SimpleTrieAddSuite {
			@Test
			@DisplayName("should add a String to an empty Trie with only valid characters")
			void testValidString() {
				trie.add(validStrings[0]);
			}
			
			@Test
			@DisplayName("should add a duplicate String")
			void testDuplicateString() {
				trie.add(validStrings[0]);
				trie.add(validStrings[0]);
			}
			
			@Test
			@DisplayName("should add multiple valid Strings")
			void testMultipleStrings() {
				trie.add(validStrings[0]);
				trie.add(validStrings[1]);
			}
			
			@Test
			@DisplayName("should throw an exception if str is null")
			void testNullString() {
				assertThrows(NullPointerException.class, () -> {
					trie.add(null);
				});
			}
			
			@Test
			@DisplayName("should throw an exception if str contains illegal characters")
			void testIllegalCharacters() {
				assertThrows(IndexOutOfBoundsException.class, () -> {
					trie.add(invalidStrings[0]);
				});
			}
		}
		
		@Nested
		@DisplayName("has(String)")
		class SimpleTrieHasSuite {
			private final String str = "acdhba";
			private final String prefix = "acd";
			private final String alternative = "edcba";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str);
				trie.add(prefix);
				trie.add(alternative);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return false when empty")
			void testEmptyTrie() {
				assertFalse(trie.has(str));
			}
			
			@Test
			@DisplayName("should return true when it contains str")
			void testHas() {
				assertTrue(trie.has(str));
				assertTrue(trie.has(alternative));
			}
			
			@Test
			@DisplayName("should return true when the prefix of another String has been inserted")
			void testHasPrefix() {
				assertTrue(trie.has(prefix));
			}
			
			@Test
			@DisplayName("should return false when searching for a prefix that exists but is not terminal")
			void testDoesNotHavePrefix() {
				assertFalse(trie.has(prefix.substring(0, prefix.length() - 1)));
			}
			
			@Test
			@DisplayName("should throw an exception for null pointers")
			void testNullPtr() {
				assertThrows(NullPointerException.class, () -> {
					trie.has(null);
				});
			}
		}
		
		@Nested
		@DisplayName("contains(String)")
		class SimpleTrieContainsSuite {
			private final String str = "abcdefg";
			private final String unknownStr = "defg";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return false when empty")
			void testEmptyTrie() {
				assertFalse(trie.has(str));
			}
			
			@Test
			@DisplayName("should return true when str has been added")
			void testAddedString() {
				assertTrue(trie.contains(str));
			}
			
			@Test
			@DisplayName("should return true when str is a prefix of a String that has been added")
			void testPrefix() {
				assertTrue(trie.contains(str.substring(0, str.length() - 2)));
			}
			
			@Test
			@DisplayName("should return false when str has not been inserted and is not a prefix of another String")
			void testDoesNotContain() {
				assertFalse(trie.contains(unknownStr));
			}
			
			@Test
			@DisplayName("should throw an exception for null pointers")
			void testNullPtr() {
				assertThrows(NullPointerException.class, () -> {
					trie.contains(null);
				});
			}
		}
		
		@Nested
		@DisplayName("remove(String)")
		class SimpleTrieRemoveSuite {
			private final String str1 = "abcdefg";
			private final String prefix = "abcd";
			private final String str2 = "defg";
			
			private final String unknownPrefix = "abc";
			private final String unknownStr = "bcda";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str1);
				trie.add(prefix);
				trie.add(str2);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return successfully when the Trie is empty")
			void testEmpty() {
				trie.remove(str1);
			}
			
			@Test
			@DisplayName("should return successfully when str has not been added")
			void testUnknownString() {
				trie.remove(unknownStr);
			}
			
			@Test
			@DisplayName("should remove str")
			void testRemove() {
				trie.remove(str1);
				
				assertFalse(trie.has(str1));
			}
			
			@Test
			@DisplayName("should not remove prefixes that have been added")
			void testPrefixRemains() {
				trie.remove(str1);
				
				assertTrue(trie.has(prefix));
			}
			
			@Test
			@DisplayName("should not remove Strings that have the removed String as its prefix")
			void testLongerStringRemains() {
				trie.remove(prefix);
				
				assertTrue(trie.has(str1));
			}
			
			@Test
			@DisplayName("should not remove unknown prefixes")
			void testUnknownPrefix() {
				trie.remove(unknownPrefix);
				
				assertTrue(trie.has(str1));
			}
		}
	}
}
