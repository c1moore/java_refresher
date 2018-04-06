package codes.c1moore.refresher.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;

import codes.c1moore.refresher.trie.Trie;

@DisplayName("Trie")
class TrieSuite {
	@Test
	@DisplayName("should be able to create an instance")
	void canCreateInstance() {
		new Trie();
	}
	
	@Nested
	@DisplayName("Instance Methods")
	class TrieInstanceMethodSuite {
		private Trie trie;
		
		@BeforeEach
		void beforeEach() {
			trie = new Trie();
		}
		
		@Nested
		@DisplayName("add(String)")
		class TrieAddSuite {
			@Test
			@DisplayName("should add a String when empty")
			void testEmptyTrie() {
				trie.add("The process of preparing programs for a digital computer is especially attractive... because it can be an aesthetic experience much like composing poetry or music.");
			}
			
			@Test
			@DisplayName("should add duplicates")
			void testDuplicates() {
				final String str = "What is more beautiful and more elusive than a well written program?";
				
				trie.add(str);
				trie.add(str);
			}
			
			@Test
			@DisplayName("should add multiple Strings")
			void testMultipleStrings() {
				trie.add("To whom it may concern:");
				trie.add("It concerns no one.");
			}
			
			@Test
			@DisplayName("should throw an exception if str is null")
			void testNullPtr() {
				assertThrows(NullPointerException.class, () -> {
					trie.add(null);
				});
			}
		}
		
		@Nested
		@DisplayName("has(String)")
		class TrieHasSuite {
			private final String str = "This is a s!ng1e Str!ng!";
			private final String duplicate = "I am a duplicate!";
			private final String substr = "This is a";
			
			private final String unknownSubstr = "I am";
			private final String unknownStr = str + "5";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str);
				trie.add(duplicate);
				trie.add(duplicate);
				trie.add(substr);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return false when empty")
			void testEmpty() {
				assertFalse(trie.has(str));
			}
			
			@Test
			@DisplayName("should return false if str was not added")
			void testUnknownString() {
				assertFalse(trie.has(unknownStr));
			}
			
			@Test
			@DisplayName("should return true if str has been added")
			void testAddString() {
				assertTrue(trie.has(str));
			}
			
			@Test
			@DisplayName("should return true for duplicates")
			void testDuplicateString() {
				assertTrue(trie.has(duplicate));
			}
			
			@Test
			@DisplayName("should return true for prefix of another String that has been added")
			void testPrefix() {
				assertTrue(trie.has(substr));
			}
			
			@Test
			@DisplayName("should return false for a prefix of another String that has not been added")
			void testUnknownPrefix() {
				assertFalse(trie.has(unknownSubstr));
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
		class TrieContainsSuite {
			private final String str = "This is a s!ng1e Str!ng!";
			
			private final String unknownSubstr = "This is";
			private final String unknownStr = str + "5";
			
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
				assertFalse(trie.contains(str));
			}
			
			@Test
			@DisplayName("should return false when the String does not exist")
			void testUnknownString() {
				assertFalse(trie.contains(unknownStr));
			}
			
			@Test
			@DisplayName("should return true for a String that has been added")
			void testString() {
				assertTrue(trie.contains(str));
			}
			
			@Test
			@DisplayName("should return true for a substring that has not been added")
			void testUnknownSubstr() {
				assertTrue(trie.contains(unknownSubstr));
			}
		}
		
		@Nested
		@DisplayName("countOccurrences(String)")
		class TrieCountOccurrencesSuite {
			private final String str = "Lorem ipsum";
			private final String prefix = "Lorem ";
			private final String duplicate = "Duplicate!";
			
			private final String unknownStr = "Ad space available here.";
			private final String unknownPrefix = "Lorem";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str);
				trie.add(prefix);
				trie.add(duplicate);
				trie.add(duplicate);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return 0 when empty")
			void testEmptyTrie() {
				assertEquals(trie.countOccurrences(str), 0);
			}
			
			@Test
			@DisplayName("should return 0 for an unknown String")
			void testUnknownString() {
				assertEquals(trie.countOccurrences(unknownStr), 0);
			}
			
			@Test
			@DisplayName("should return 0 for a prefix that has not been added")
			void testUnknownPrefix() {
				assertEquals(trie.countOccurrences(unknownPrefix), 0);
			}
			
			@Test
			@DisplayName("should return the appropriate count for added Strings")
			void testString() {
				assertEquals(trie.countOccurrences(str), 1);
				assertEquals(trie.countOccurrences(duplicate), 2);
			}
			
			@Test
			@DisplayName("should return the appropriate count for prefixes that have been added")
			void testPrefix() {
				assertEquals(trie.countOccurrences(prefix), 1);
			}
		}
		
		@Nested
		@DisplayName("remove(String)")
		class TrieRemoveSuite {
			private final String str = "Lorem ipsum";
			private final String prefix = "Lorem";
			private final String duplicate = "Duplicate!";
			
			private final String unknownStr = "I am a mysterious string!";
			private final String unknownPrefix = "Lor";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str);
				trie.add(prefix);
				trie.add(duplicate);
				trie.add(duplicate);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return successfully when empty")
			void testEmptyTrie() {
				assertEquals(trie.countOccurrences(str), 0);
				
				trie.remove(str);
			}
			
			@Test
			@DisplayName("should return successfully when str has not been added")
			void testUnknownString() {
				assertEquals(trie.countOccurrences(unknownStr), 0);
				
				trie.remove(unknownStr);
			}
			
			@Test
			@DisplayName("should return successfully without affecting the Trie when str is an unknown prefix")
			void testUnknownPrefix() {
				assertFalse(trie.has(unknownPrefix));
				
				trie.remove(unknownPrefix);
				
				assertTrue(trie.has(str));
				assertTrue(trie.has(prefix));
				assertTrue(trie.has(duplicate));
			}
			
			@Test
			@DisplayName("should remove str without removing its prefixes")
			void testRemove() {
				assertEquals(trie.countOccurrences(str), 1);
				assertEquals(trie.countOccurrences(prefix), 1);
				
				trie.remove(str);
				
				assertFalse(trie.has(str));
				assertEquals(trie.countOccurrences(prefix), 1);
			}
			
			@Test
			@DisplayName("should remove a prefix without remove Strings that use it")
			void testRemovePrefix() {
				assertTrue(trie.has(str));
				assertEquals(trie.countOccurrences(prefix), 1);
				
				trie.remove(prefix);
				
				assertTrue(trie.has(str));
				assertFalse(trie.has(prefix));
			}
			
			@Test
			@DisplayName("should remove only a single occurrence")
			void testRemoveDuplicates() {
				assertEquals(trie.countOccurrences(duplicate), 2);
				
				trie.remove(duplicate);
				
				assertEquals(trie.countOccurrences(duplicate), 1);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should not keep prefixes that have not been added")
			void testRemoveStringWithoutPrefixes() {
				assertFalse(trie.contains(prefix));
				
				trie.add(str);
				
				assertTrue(trie.contains(prefix));
				
				trie.remove(str);
				
				assertFalse(trie.contains(prefix));
			}
		}
		
		@Nested
		@DisplayName("removeAll(String)")
		class TrieRemoveAllSuite {
			private final String str = "Lorem ipsum";
			private final String prefix = "Lorem";
			private final String duplicate = "Duplicate!";
			
			private final String unknownStr = "I am a mysterious string!";
			private final String unknownPrefix = "Lor";
			
			@BeforeEach
			void beforeEach(TestInfo testInfo) {
				if(testInfo.getTags().contains("EmptyTrie")) {
					return;
				}
				
				trie.add(str);
				trie.add(prefix);
				trie.add(duplicate);
				trie.add(duplicate);
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should return successfully when empty")
			void testEmptyTrie() {
				assertEquals(trie.countOccurrences(str), 0);
				
				trie.removeAll(str);
			}
			
			@Test
			@DisplayName("should return successfully when str has not been added")
			void testUnknownString() {
				assertEquals(trie.countOccurrences(unknownStr), 0);
				
				trie.removeAll(unknownStr);
			}
			
			@Test
			@DisplayName("should return successfully without affecting the Trie when str is an unknown prefix")
			void testUnknownPrefix() {
				assertFalse(trie.has(unknownPrefix));
				
				trie.removeAll(unknownPrefix);
				
				assertTrue(trie.has(str));
				assertTrue(trie.has(prefix));
				assertTrue(trie.has(duplicate));
			}
			
			@Test
			@DisplayName("should remove str without removing its prefixes")
			void testRemove() {
				assertEquals(trie.countOccurrences(str), 1);
				assertEquals(trie.countOccurrences(prefix), 1);
				
				trie.removeAll(str);
				
				assertFalse(trie.has(str));
				assertEquals(trie.countOccurrences(prefix), 1);
			}
			
			@Test
			@DisplayName("should remove a prefix without remove Strings that use it")
			void testRemovePrefix() {
				assertTrue(trie.has(str));
				assertEquals(trie.countOccurrences(prefix), 1);
				
				trie.removeAll(prefix);
				
				assertTrue(trie.has(str));
				assertFalse(trie.has(prefix));
			}
			
			@Test
			@DisplayName("should remove all occurrences")
			void testRemoveDuplicates() {
				assertEquals(trie.countOccurrences(duplicate), 2);
				
				trie.removeAll(duplicate);
				
				assertFalse(trie.has(duplicate));
			}
			
			@Test
			@Tag("EmptyTrie")
			@DisplayName("should not keep prefixes that have not been added")
			void testRemoveStringWithoutPrefixes() {
				assertFalse(trie.contains(prefix));
				
				trie.add(str);
				
				assertTrue(trie.contains(prefix));
				
				trie.removeAll(str);
				
				assertFalse(trie.contains(prefix));
			}
		}
	}
}
