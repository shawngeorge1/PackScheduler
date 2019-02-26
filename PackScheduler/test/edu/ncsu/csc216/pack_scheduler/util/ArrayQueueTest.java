package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests the array queue class enqueueing and dequeueing
 * @author Abby Bowyer
 */
public class ArrayQueueTest {

	/**
	 * Test method for adding to the queue
	 */
	@Test
	public void testEnqueue() {
		ArrayQueue<String> queue = new ArrayQueue<String>(10);

        queue.enqueue("banana");
        assertEquals(1, queue.size());

        queue.enqueue("apple");
        assertEquals(2, queue.size());

        queue.enqueue("bad banana");
        assertEquals(3, queue.size());

        queue.enqueue("really bad banana");
        assertEquals(4, queue.size());

        try {
            queue.enqueue(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals(4, queue.size());
        }

        try {
            queue.enqueue("really bad banana");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(4, queue.size());
        }
	}

	/**
	 * Test method for removing from the queue and removing from an empty list
	 */
	@Test
	public void testDequeue() {
		ArrayQueue<String> queue = new ArrayQueue<String>(10);

        queue.enqueue("banana");
        queue.enqueue("apple");
        queue.enqueue("bad banana");
        queue.enqueue("really bad banana");
        
        assertEquals("banana", queue.dequeue());
        assertEquals(3, queue.size());
        queue.dequeue();
        queue.dequeue();
        queue.enqueue("pear");
        assertEquals(2, queue.size());
        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());
        
        try {
            queue.dequeue();
            fail();
        } catch (NoSuchElementException e) {
        	assertEquals(0, queue.size());
        }
	}

	/**
	 * Test method for setting the capacity
	 */
	@Test
	public void testSetCapacity() {
		ArrayQueue<String> queue = new ArrayQueue<String>(10);

        queue.enqueue("banana");
        queue.enqueue("apple");
        queue.enqueue("bad banana");
        queue.enqueue("really bad banana");
        
        queue.setCapacity(queue.size());
        
        try {
            queue.setCapacity(queue.size() - 1);
            fail();
        } catch (IllegalArgumentException e) {
        	assertEquals(4, queue.size());
        }
        
        try {
            queue.setCapacity(-1);
            fail();
        } catch (IllegalArgumentException e) {
        	assertEquals(4, queue.size());
        }
	}
}
