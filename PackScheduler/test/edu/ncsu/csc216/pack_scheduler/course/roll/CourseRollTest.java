package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll Class
 * 
 * @author Tanvi Thummar
 *
 */
public class CourseRollTest {


	/** Valid Student 1 */
	private Student student1 = new Student("shawn" , "george" , "flast" , "flast@ncsu.edu" , "flastNCSU");
	/** Valid Student 2 */
	private Student student2 = new Student("john" , "doe" , "jdoe" , "jdoe@ncsu.edu" , "jdoeNCSU");
	/** Valid Student 3 */
	private Student student3 = new Student("course" , "rolle" , "test" , "courseroll@ncsu.edu" , "coresNCUS");
	/** Valid Student 4 */
	private Student student4 = new Student("firstn" , "rllli" , "asdf" , "asdf@ncsu.edu" , "adsfe");
	/** Valid Student 5 */
	private Student student5 = new Student("first" , "aliw" , "abse" , "absd@ncsu.edu" , "dagbs");
	/** Valid Student 6 */
	private Student student6 = new Student("firstname" , "lastNam" , "slkai" , "alkdi@ncsu.edu" , "alkiNCSU");
	/** Valid Student 7 */
	private Student student7 = new Student("firstname" , "lastNa" , "sfgk" , "asdf@ncsu.edu" , "adfjNCSU");
	/** Valid Student 8 */
	private Student student8 = new Student("firstname" , "lastN" , "cngsd" , "qer@ncsu.edu" , "qerNCSU");
	/** Valid Student 9 */
	private Student student9 = new Student("firstname" , "last" , "cbsf" , "cvad@ncsu.edu" , "eqirNCSU");
	/** Valid Student 10 */
	private Student student10 = new Student("firstnam" , "lastNam" , "bvc" , "ghfg@ncsu.edu" , "eqNCSU");
	/** Valid Student 11 */
	private Student student11 = new Student("firstna" , "lastNa" , "sadfv" , "awer@ncsu.edu" , "eqNeNCSU");
	/** Valid Student 12 */
	private Student student12 = new Student("first" , "last" , "adfc" , "qera@ncsu.edu" , "oppoNCSU");

	/**
	 * Tests the constructor of CourseRoll.
	 */
	@Test
	public void testCourseRoll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// CourseRoll roll = new CourseRoll(10); //Update as below

		// Test valid capacity
		assertEquals(10, c.getCourseRoll().getOpenSeats());
		assertEquals(10, c.getCourseRoll().getEnrollmentCap());

		// //Test invalid capacity (less)
		// try {
		// courseRoll = new CourseRoll(5);
		// fail();
		// } catch(IllegalArgumentException e) {
		// assertNotNull(e);
		// }
		//
		// //Test invalid capacity (more)
		// try {
		// courseRoll = new CourseRoll(255);
		// fail();
		// } catch(IllegalArgumentException e) {
		// assertNotNull(e);
		// }
	}

	/**
	 * Tests the CoureRoll.setEnrollmentCap() method
	 */
	@Test
	public void testSetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// CourseRoll roll = new CourseRoll(10); //Update as below
		CourseRoll roll = c.getCourseRoll();

		// Test valid capacity
		roll.setEnrollmentCap(155);
		assertEquals(155, roll.getEnrollmentCap());

		// Test invalid capacity (less)
		try {
			roll.setEnrollmentCap(5);
			fail();
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

		// Test invalid capacity (more)
		try {
			roll.setEnrollmentCap(355);
			fail();
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

	}

	/**
	 * Tests the CourseRoll.enroll(Student s) method
	 */
	@Test
	public void testEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// CourseRoll roll = new CourseRoll(10); //Update as below
		CourseRoll roll = c.getCourseRoll();

		// Test valid student
		try {
			roll.enroll(student1);
		} catch (Exception e) {
			fail();
		}

		// Test add null student
		try {
			roll.enroll(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

		// Test add duplicate students
		try {
			roll.enroll(student1);
			roll.enroll(student1);
			fail();
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

		// Test add student in a full class
		try {
			roll.enroll(student1);
			roll.enroll(student2);
			roll.enroll(student3);
			roll.enroll(student4);
			roll.enroll(student5);
			roll.enroll(student6);
			roll.enroll(student7);
			roll.enroll(student8);
			roll.enroll(student9);
			roll.enroll(student10);
			roll.enroll(student11);
			roll.enroll(student12);
			fail();
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

	}

	/**
	 * Tests the CoureRoll.drop() method
	 */
	@Test
	public void testDrop() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// CourseRoll roll = new CourseRoll(10); //Update as below
		CourseRoll roll = c.getCourseRoll();

		// Test drop null student
		try {
			roll.drop(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

		// Test valid student
		try {
			roll.enroll(student1);
			roll.drop(student1);
		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	/**
	 * Tests the CoureRoll.canEnroll() method
	 */
	@Test
	public void testCanEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// CourseRoll roll = new CourseRoll(10); //Update as below
		CourseRoll roll = c.getCourseRoll();

		// Test enrolling valid student
		assertTrue(roll.canEnroll(student1));

		// Test enrolling duplicate student
		roll.enroll(student1);
		assertFalse(roll.canEnroll(student1));

		// Test enrolling student to full class
		roll.drop(student1);
		roll.enroll(student2);

		roll.enroll(student3);
		roll.enroll(student4);
		roll.enroll(student5);
		roll.enroll(student6);
		roll.enroll(student7);
		roll.enroll(student8);
		roll.enroll(student9);
		roll.enroll(student10);
		assertTrue(roll.canEnroll(student11));

		roll.enroll(student11);
		assertTrue(roll.canEnroll(student12));

	}

	/**
	 * Tests the Course Roll waitlist method
	 */
	@Test
	public void testWaitlist() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");

		CourseRoll roll = c.getCourseRoll();

		roll.enroll(student1);
		roll.enroll(student2);
		roll.enroll(student3);
		roll.enroll(student4);
		roll.enroll(student5);
		roll.enroll(student6);
		roll.enroll(student7);
		roll.enroll(student8);
		roll.enroll(student9);
		roll.enroll(student10);

		assertEquals(roll.getNumberOnWaitlist(), 0);

		roll.enroll(student11);
		roll.enroll(student12);

		assertEquals(roll.getNumberOnWaitlist(), 2);
		roll.drop(student11);
		assertEquals(roll.getNumberOnWaitlist(), 1);

	}

}
