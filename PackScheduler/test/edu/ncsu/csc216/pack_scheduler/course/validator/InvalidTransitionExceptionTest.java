package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the new custom exception class for when there is an invalid transition between states
 * and the custom message is the same and the default messages are the same when called.
 * @author Abby Bowyer
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Tests the custom message and asserts equal the string and the exception.getMessage()
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ite = new InvalidTransitionException("custom message");
		assertEquals("custom message", ite.getMessage());
	}
	
	/**
	 * Tests the default message and asserts equal the string and the exception.getMessage()
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ite = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ite.getMessage());
	}

}
