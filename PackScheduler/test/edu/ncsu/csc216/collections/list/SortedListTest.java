package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests our teachers sorted list class
 * @author msleonar
 * @author sikellog
 */
public class SortedListTest {

    /**
     * Tests initial state of a sorted list and its expandability
     */
    @Test
    public void testSortedList() {
        SortedList<String> list = new SortedList<String>();
        assertEquals(0, list.size());
        assertFalse(list.contains("apple"));

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
    }

    /**
     * Tests the ability of a sorted list to be added to
     */
    @Test
    public void testAdd() {
        SortedList<String> list = new SortedList<String>();

        list.add("banana");
        assertEquals(1, list.size());
        assertEquals("banana", list.get(0));

        list.add("apple");
        assertEquals(2, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));

        list.add("bad banana");
        assertEquals(3, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("bad banana", list.get(1));
        assertEquals("banana", list.get(2));

        list.add("really bad banana");
        assertEquals(4, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("bad banana", list.get(1));
        assertEquals("banana", list.get(2));
        assertEquals("really bad banana", list.get(3));

        try {
            list.add(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals(4, list.size());
        }

        try {
            list.add("really bad banana");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(4, list.size());
        }
    }

    /**
     * Tests getting from sortedlist at various indices
     */
    @Test
    public void testGet() {
        SortedList<String> list = new SortedList<String>();

        // Since get() is used throughout the tests to check the
        // contents of the list, we don't need to test main flow functionality
        // here. Instead this test method should focus on the error
        // and boundary cases.

        try {
            list.get(0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(0, list.size());
        }

        list.add("apple");
        list.add("banana");
        list.add("bad banana");
        list.add("really bad banana");

        try {
            list.get(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(4, list.size());
        }

        try {
            list.get(list.size());
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(4, list.size());
        }
    }

    /**
     * Tests removing from sorted list at various indices
     */
    @Test
    public void testRemove() {
        SortedList<String> list = new SortedList<String>();

        try {
            list.remove(0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(0, list.size());
        }

        list.add("apple");
        list.add("banana");
        list.add("bad banana");
        list.add("really bad banana");

        try {
            list.remove(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(4, list.size());
        }

        try {
            list.remove(list.size());
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(4, list.size());
        }

        list.remove(1);
        assertEquals(3, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("really bad banana", list.get(2));

        list.remove(list.size() - 1);
        assertEquals(2, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));

        list.remove(0);
        assertEquals(1, list.size());
        assertEquals("banana", list.get(0));

        list.remove(list.size() - 1);
        assertEquals(0, list.size());
    }

    /**
     * Tests getting the index at which various objects are in a sorted list
     */
    @Test
    public void testIndexOf() {
        SortedList<String> list = new SortedList<String>();

        assertEquals(-1, list.indexOf("notathing"));

        list.add("apple");
        list.add("banana");
        list.add("bad banana");
        list.add("really bad banana");

        assertEquals(0, list.indexOf("apple"));
        assertEquals(1, list.indexOf("bad banana"));
        assertEquals(2, list.indexOf("banana"));
        assertEquals(3, list.indexOf("really bad banana"));

        assertEquals(-1, list.indexOf("really really bad banana"));
        assertEquals(-1, list.indexOf("good banana"));

        try {
            list.indexOf(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals(4, list.size());
        }

    }

    /**
     * Tests the method to clear a list
     */
    @Test
    public void testClear() {
        SortedList<String> list = new SortedList<String>();

        list.add("apple");
        list.add("banana");
        list.add("bad banana");
        list.add("really bad banana");

        list.clear();

        assertEquals(0, list.size());
    }

    /**
     * Tests the method to get whether a list is empty
     */
    @Test
    public void testIsEmpty() {
        SortedList<String> list = new SortedList<String>();

        assertTrue(list.isEmpty());

        list.add("element");

        assertFalse(list.isEmpty());
    }

    /**
     * Tests the method which determines of a sorted list contains various objects
     */
    @Test
    public void testContains() {
        SortedList<String> list = new SortedList<String>();

        assertFalse(list.contains("other"));

        list.add("Some");
        list.add("other");
        list.add("null");

        assertTrue(list.contains("Some"));
        assertTrue(list.contains("other"));
        assertTrue(list.contains("null"));
        assertFalse(list.contains("string"));
        assertFalse(list.contains("object"));
    }

    /**
     * Tests the ability of sorted lists to compare themselves via the .equals method
     */
    @Test
    public void testEquals() {
        SortedList<String> list1 = new SortedList<String>();
        SortedList<String> list2 = new SortedList<String>();
        SortedList<String> list3 = new SortedList<String>();

        list1.add("empty");
        list2.add("empty");
        list3.clear();
        
        Object nullObj = null;

        assertTrue(list1.equals(list2));
        assertTrue(list2.equals(list1));
        assertFalse(list1.equals(list3));
        assertFalse(list3.equals(list1));
        assertFalse(list1.equals(nullObj));
        assertFalse(list3.equals(nullObj));
        assertFalse(list1.equals("empty"));
    }

    /**
     * Tests the hash code of various equal and not equal sorted lists and how they compare
     */
    @Test
    public void testHashCode() {
        SortedList<String> list1 = new SortedList<String>();
        SortedList<String> list2 = new SortedList<String>();
        SortedList<String> list3 = new SortedList<String>();

        list1.add("empty");
        list2.add("empty");
        list3.clear();

        assertTrue(list1.hashCode() == list2.hashCode());
        assertTrue(list1.hashCode() != list3.hashCode());
    }

}
