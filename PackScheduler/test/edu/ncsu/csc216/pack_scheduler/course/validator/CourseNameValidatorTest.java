/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the CourseNameValidator class
 * 
 * @author Tanvi Thummar
 *
 */
public class CourseNameValidatorTest {

	/**
	 * Test method for isValid(), it tests if the given course name is valid.
	 */
	@Test
	public void testIsValid() {
		CourseNameValidator validator = new CourseNameValidator();

		// Valid 3 letters
		try {
			assertTrue(validator.isValid("CSC116"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 4 letters
		try {
			assertTrue(validator.isValid("HESS211"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 2 letters
		try {
			assertTrue(validator.isValid("MA241"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 1 letter
		try {
			assertTrue(validator.isValid("E102"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 3 letters with suffix
		try {
			assertTrue(validator.isValid("CSC116A"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 4 letters with suffix
		try {
			assertTrue(validator.isValid("HESS211B"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 2 letters with suffix
		try {
			assertTrue(validator.isValid("MA241C"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Valid 1 letter with suffix
		try {
			assertTrue(validator.isValid("E102D"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// InValid 2 digits
		try {
			assertFalse(validator.isValid("E10"));
		} catch (InvalidTransitionException e) {
			assertEquals("Invalid validator Transition.", e.getMessage());
		}

		// InValid 1 digits
		try {
			assertFalse(validator.isValid("E1"));
		} catch (InvalidTransitionException e) {
			assertEquals("Invalid validator Transition.", e.getMessage());
		}

		// InValid no digits
		try {
			assertFalse(validator.isValid("E"));
		} catch (InvalidTransitionException e) {
			assertEquals("Invalid validator Transition.", e.getMessage());
		}

		// InValid no digits
		try {
			assertFalse(validator.isValid("226"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}

		// More than 4 letters
		try {
			assertFalse(validator.isValid("ABCDE123"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}

		// More than 3 digits
		try {
			assertFalse(validator.isValid("E1034"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}

		// Just 1 digit
		try {
			assertFalse(validator.isValid("ABC1A"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}

		// Just 2 digit
		try {
			assertFalse(validator.isValid("ABC12A"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}

		// More than 1 Suffix
		try {
			assertFalse(validator.isValid("A123AB"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}

		// Digits after suffix
		try {
			assertFalse(validator.isValid("A123A2"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}

		// Invalid Character
		try {
			assertFalse(validator.isValid("A!134A#"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

}
