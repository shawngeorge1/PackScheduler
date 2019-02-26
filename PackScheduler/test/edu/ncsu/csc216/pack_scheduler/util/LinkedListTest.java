package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests our replacement for ArrayList class
 *
 * @author Matt
 */
public class LinkedListTest {

    /**
     * Tests initial state of a array list and its expandability
     */
    @Test
    public void testLinkedList() {
        LinkedList<String> list = new LinkedList<String>();
        assertEquals(0, list.size());
        list.listIterator(0);
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
        assertEquals("1", list.listIterator().next());
        try {
        	list.listIterator().previous();
        } catch (NoSuchElementException e)
        {
        	//do othing
        }
        assertEquals(-1, list.listIterator().previousIndex());
        assertEquals(0, list.listIterator().nextIndex());
        assertFalse(list.listIterator().hasPrevious());
        assertTrue(list.listIterator().hasNext());

    }

    /**
     * Tests the ability of a sorted list to be added to
     */
    @Test
    public void testAdd() {
        LinkedList<String> list = new LinkedList<String>();

        list.add("banana");
        assertEquals(1, list.size());
        assertEquals("banana", list.get(0));

        list.add("apple");
        assertEquals(2, list.size());
        assertEquals("apple", list.get(1));
        assertEquals("banana", list.get(0));

        list.add("bad banana");
        assertEquals(3, list.size());
        assertEquals("apple", list.get(1));
        assertEquals("bad banana", list.get(2));
        assertEquals("banana", list.get(0));

        list.add("really bad banana");
        assertEquals(4, list.size());
        assertEquals("apple", list.get(1));
        assertEquals("bad banana", list.get(2));
        assertEquals("really bad banana", list.get(3));
        assertEquals("banana", list.get(0));
    

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
     * Tests getting from ArrayList at various indices
     */
    @Test
    public void testGet() {
        LinkedList<String> list = new LinkedList<String>();

        // Since get() is used throughout the tests to check the
        // contents of the list, we don't need to test main flow functionality
        // here. Instead this test method should focus on the error
        // and boundary cases.

        try {
            list.get(-1);
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
            list.get(list.size() + 1);
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
        LinkedList<String> list = new LinkedList<String>();

        try {
            list.remove(0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(0, list.size());
        }

        try {
            list.remove(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(0, list.size());
        }
        
        try {
            list.remove(list.size());
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(0, list.size());
        }
        
        list.add("apple");
        list.add("banana");
        list.add("bad banana");
        list.add("really bad banana");





        list.remove(1);
        assertEquals(3, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("bad banana", list.get(1));
        assertEquals("really bad banana", list.get(2));

        list.remove(list.size() - 1);
        assertEquals(2, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("bad banana", list.get(1));

        list.remove(0);
        assertEquals(1, list.size());
        assertEquals("bad banana", list.get(0));

        list.remove(list.size() - 1);
        assertEquals(0, list.size());
    }



    /**
     * Tests the method to get whether a list is empty
     */
    @Test
    public void testIsEmpty() {
        LinkedList<String> list = new LinkedList<String>();

        assertTrue(list.isEmpty());

        list.add("element");

        assertFalse(list.isEmpty());
    }

    /**
     * Tests the sorted list set method
     */
    @Test
    public void testSet() {
        LinkedList<String> list = new LinkedList<String>();

        list.add("banana");
        assertEquals(1, list.size());
        assertEquals("banana", list.get(0));

        list.add("apple");
        assertEquals(2, list.size());
        assertEquals("banana", list.get(0));
        assertEquals("apple", list.get(1));

        list.add("carrot");
        assertEquals(3, list.size());
        assertEquals("banana", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("carrot", list.get(2));

        list.add("date");
        assertEquals(4, list.size());
        assertEquals("banana", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("carrot", list.get(2));
        assertEquals("date", list.get(3));

        list.set(1, "bagel");
        assertEquals(4, list.size());
        assertEquals("banana", list.get(0));
        assertEquals("bagel", list.get(1));
//        assertEquals("apple", list.get(2));
        assertEquals("carrot", list.get(2));
        assertEquals("date", list.get(3));
    }
}
