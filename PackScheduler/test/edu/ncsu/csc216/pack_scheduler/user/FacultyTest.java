package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for testing the Faculty class
 * @author Abby Bowyer
 */
public class FacultyTest {
	
	/**
	 * Test method for constructing a faculty
	 */
	@Test
	public void testFacultyConstructor() {
        String first = "first";
        String last = "last";
        String id = "id";
        String email = "email@ncsu.edu";
        String passwd = "hashedpassword";
        int maxCourses = 2;
        User f = null; 
        try {
            f = new Faculty(null, last, id, email, passwd, maxCourses);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
        try {
            f = new Faculty(first, null, id, email, passwd, maxCourses);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
        try {
            f = new Faculty(first, last, null, email, passwd, maxCourses);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
        try {
            f = new Faculty(first, last, id, null, passwd, maxCourses);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
        try {
            f = new Faculty(first, last, id, email, null, maxCourses);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
        try {
            f = new Faculty(first, last, id, email, passwd, 0);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
        try {
            f = new Faculty(first, last, id, email, passwd, 4);
            fail();
        } catch (IllegalArgumentException e) {
        	assertNull(f);
        }
	}
	
	/**
	 * Tests the setter and getter for max Courses
	 */
	@Test
	public void testMaxCourses() {
		String first = "first";
        String last = "last";
        String id = "id";
        String email = "email@ncsu.edu";
        String passwd = "hashedpassword";
        int maxCourses = 2;
		Faculty f = new Faculty(first, last, id, email, passwd, maxCourses);
		assertEquals(2, f.getMaxCourses());
	}
	
	/**
	 * Test method for the hashcode of the faculty
	 */
	@Test
    public void testHashCode() {
        User f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
        User f2 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
        User f3 = new Faculty("last", "first", "id", "email@ncsu.edu", "hashedpassword", 2);
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f2.hashCode(), f3.hashCode());
    }
	
	/**
     * Test method for the equals method
     */
    @Test
    public void testEqualsObject() {
        User f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
        User f2 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
        User f3 = new Faculty("last", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
        User f4 = new Faculty("first", "first", "id", "email@ncsu.edu", "hashedpassword", 2);
        User f5 = new Faculty("first", "last", "di", "email@ncsu.edu", "hashedpassword", 2);
        User f6 = new Faculty("first", "last", "id", "email@ncsu.eduu", "hashedpassword", 2);
        User f7 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedp assword", 2);

        Object nullObj = null;

        assertTrue(f1.equals(f1));
        assertFalse(f1.equals(nullObj));
        assertFalse(f1.equals(new Object()));
        assertTrue(f1.equals(f2));
        assertFalse(f1.equals(f3));
        assertFalse(f1.equals(f4));
        assertFalse(f1.equals(f5));
        assertFalse(f1.equals(f6));
        assertFalse(f1.equals(f7));
    }
    
    /**
     * Test method for the to string method
     */
    @Test
    public void testToString() {
        User f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
        assertEquals("first,last,id,email@ncsu.edu,hashedpassword,2", f.toString());

    }
}
