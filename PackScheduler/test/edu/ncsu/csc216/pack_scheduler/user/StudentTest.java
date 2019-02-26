package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test student class
 * 
 * @author msleonar
 */
public class StudentTest {

    /**
     * Test method for the hashcode method
     */
    @Test
    public void testHashCode() {
        User s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        User s2 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        User s3 = new Student("last", "first", "id", "email@ncsu.edu", "hashedpassword");
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s2.hashCode(), s3.hashCode());
    }

    /**
     * Test method for the first student constructor
     */
    @Test
    public void testStudentStringStringStringStringStringInt() {
        String first = "first";
        String last = "last";
        String id = "id";
        String email = "email@ncsu.edu";
        String passwd = "hashedpassword";
        int maxCred = 18;
        User s = null; // Initialize a student reference to null
        try {
            s = new Student(null, last, id, email, passwd, maxCred);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, null, id, email, passwd, maxCred);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, last, null, email, passwd, maxCred);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, last, id, null, passwd, maxCred);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, last, id, email, null, maxCred);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
    }

    /**
     * Test method for the second student constructor
     */
    @Test
    public void testStudentStringStringStringStringString() {
        String first = "first";
        String last = "last";
        String id = "id";
        String email = "email@ncsu.edu";
        String passwd = "hashedpassword";
        User s = null; // Initialize a student reference to null
        try {
            s = new Student(null, last, id, email, passwd);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, null, id, email, passwd);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, last, null, email, passwd);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, last, id, null, passwd);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
        try {
            s = new Student(first, last, id, email, null);
            // Note that for testing purposes, the password doesn't need to be
            // hashedpassword
            fail(); // If we reach this point a Student was constructed when it shouldn't have been!
        } catch (IllegalArgumentException e) {
            // We should get here if the expected IllegalArgumentException is thrown, but
            // that's not enough
            // for the test. We also need to make sure that the reference s is still null!
            assertNull(s);
        }
    }

    /**
     * Test method for setting the email of the student
     */
    @Test
    public void testSetEmail() {
        // Construct a valid Student
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        try {
            s.setEmail(null);
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("email@ncsu.edu", s.getEmail());
        }
        try {
            s.setEmail("");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("email@ncsu.edu", s.getEmail());
        }
        try {
            s.setEmail("emailncsu.edu");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("email@ncsu.edu", s.getEmail());
        }
        try {
            s.setEmail("email@ncsuedu");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("email@ncsu.edu", s.getEmail());
        }
        try {
            s.setEmail("email.ncsu@edu");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("email@ncsu.edu", s.getEmail());
        }
    }

    /**
     * Test method for setting the password for the student
     */
    @Test
    public void testSetPassword() {
        // Construct a valid Student
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        try {
            s.setPassword(null);
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("hashedpassword", s.getPassword());
        }
        try {
            s.setPassword("");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("hashedpassword", s.getPassword());
        }
    }

    /**
     * Test method for setting the max credits for the student
     */
    @Test
    public void testSetMaxCredits() {
        // Construct a valid Student
        Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
        try {
            s.setMaxCredits(1);
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals(15, s.getMaxCredits());
        }
        try {
            s.setMaxCredits(19);
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals(15, s.getMaxCredits());
        }
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.pack_scheduler.user.Student#setFirstName(java.lang.String)}.
     */
    @Test
    public void testSetFirstName() {
        // Construct a valid Student
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        try {
            s.setFirstName(null);
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("first", s.getFirstName());
        }
        try {
            s.setFirstName("");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("first", s.getFirstName());
        }
    }

    /**
     * Test method for setting the last name of the student
     */
    @Test
    public void testSetLastName() {
        // Construct a valid Student
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        try {
            s.setLastName(null);
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("last", s.getLastName());
        }
        try {
            s.setLastName("");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("last", s.getLastName());
        }
    }

    /**
     * Test method for the to string method
     */
    @Test
    public void testToString() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
        assertEquals("first,last,id,email@ncsu.edu,hashedpassword,16", s.toString());

    }

    /**
     * Test method for the equals method
     */
    @Test
    public void testEqualsObject() {
        User s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        User s2 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        User s3 = new Student("last", "last", "id", "email@ncsu.edu", "hashedpassword");
        User s4 = new Student("first", "first", "id", "email@ncsu.edu", "hashedpassword");
        User s5 = new Student("first", "last", "di", "email@ncsu.edu", "hashedpassword");
        User s6 = new Student("first", "last", "id", "email@ncsu.eduu", "hashedpassword");
        User s7 = new Student("first", "last", "id", "email@ncsu.edu", "hashedp assword");
        User s8 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);

        Object nullObj = null;

        assertTrue(s1.equals(s1));
        assertFalse(s1.equals(nullObj));
        assertFalse(s1.equals(new Object()));
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(s3));
        assertFalse(s1.equals(s4));
        assertFalse(s1.equals(s5));
        assertFalse(s1.equals(s6));
        assertFalse(s1.equals(s7));
        assertFalse(s1.equals(s8));
    }

    /**
     * Test set id for student
     */
    @Test
    public void testSetId() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        try {
            s = new Student("first", "last", null, "email@ncsu.edu", "hashedpassword");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("id", s.getId());
        }
        try {
            s = new Student("first", "last", "", "email@ncsu.edu", "hashedpassword");
            fail(); // We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            // We've caught the exception, now we need to make sure that the field didn't
            // change
            assertEquals("id", s.getId());
        }
    }

    /**
     * Test compareTo function of student
     */
    @Test
    public void testCompareTo() {
        Student s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        Student s2 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
        Student s3 = new Student("last", "first", "id2", "email@ncsu.edu", "hashedpassword");
        Student s4 = new Student("really last", "first", "id2", "email@ncsu.edu", "hashedpassword");

        assertEquals(0, s1.compareTo(s2));
        assertEquals(0, s2.compareTo(s1));

        assertTrue(s1.compareTo(s3) > 0);
        assertTrue(s3.compareTo(s1) < 0);
        assertTrue(s4.compareTo(s3) > 0);
        assertTrue(s3.compareTo(s4) < 0);
        assertTrue(s4.compareTo(s3) > 0);

    }

}
