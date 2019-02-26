package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests the array stack class pushing and popping
 * @author Shawn George
 */
public class ArrayStackTest {
	
	/**
	 * Test method for adding to the queue
	 */
	@Test
	public void testPush() {
		ArrayStack<String> stack = new ArrayStack<String>(10);

		stack.push("banana");
        assertEquals(1, stack.size());

        stack.push("apple");
        assertEquals(2, stack.size());

        stack.push("bad banana");
        assertEquals(3, stack.size());

        stack.push("really bad banana");
        assertEquals(4, stack.size());

        try {
        	stack.push(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(4, stack.size());
        }

        try {
        	stack.push("really bad banana");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(4, stack.size());
        }
	}

	/**
	 * Test method for removing from the queue and removing from an empty list
	 */
	@Test
	public void testPop() {
		ArrayStack<String> stack = new ArrayStack<String>(10);

		stack.push("banana");
		stack.push("apple");
		stack.push("bad banana");
		stack.push("really bad banana");
        
        assertEquals("really bad banana", stack.pop());
        assertEquals(3, stack.size());
        stack.pop();
        stack.pop();
        stack.push("pear");
        assertEquals(2, stack.size());
        stack.pop();
        stack.pop();
        assertEquals(0, stack.size());
        
        try {
            stack.pop();
            fail();
        } catch (EmptyStackException e) {
        	assertEquals(0, stack.size());
        }
	}

	/**
	 * Test method for setting the capacity
	 */
	@Test
	public void testSetCapacity() {
		ArrayStack<String> stack = new ArrayStack<String>(10);

		stack.push("banana");
		stack.push("apple");
		stack.push("bad banana");
		stack.push("really bad banana");
        
		stack.setCapacity(stack.size());
        
        try {
        	stack.setCapacity(stack.size() - 1);
            fail();
        } catch (IllegalArgumentException e) {
        	assertEquals(4, stack.size());
        }
        
        try {
        	stack.setCapacity(-1);
            fail();
        } catch (IllegalArgumentException e) {
        	assertEquals(4, stack.size());
        }
	}

}
