package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test Conflict Exceptions
 * 
 * @author msleonar
 */
public class ConflictExceptionTest {

    /**
     * Test method for Conflict Exception with message
     */
    @Test
    public void testConflictExceptionString() {
        ConflictException ce = new ConflictException("Custom exception message");
        assertEquals("Custom exception message", ce.getMessage());
    }

    /**
     * Test method for Conflict Exception
     */
    @Test
    public void testConflictException() {
        ConflictException ce = new ConflictException();
        assertEquals("Schedule conflict.", ce.getMessage());
    }

}
