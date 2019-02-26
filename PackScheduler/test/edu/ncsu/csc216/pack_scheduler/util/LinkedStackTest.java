package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the linked stack class pushing and popping
 * @author abbybowyer
 *
 */
public class LinkedStackTest {

	/**
	 * Tests the pushing onto the stack
	 */
	@Test
	public void push() {
		LinkedStack<Integer> stack = new LinkedStack<Integer>(5);
		stack.push(1);
		stack.push(3);
		assertEquals(2, stack.size());
		assertFalse(stack.isEmpty());
	}

	/**
	 * tests the popping off of a stack
	 */
	@Test public void pop() {
		LinkedStack<Integer> stack = new LinkedStack<Integer>(10);
		stack.push(1);
		stack.push(3);
		stack.pop();
		assertEquals(1, stack.size());
	}
	
	

}
