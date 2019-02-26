package edu.ncsu.csc216.pack_scheduler.course.roll;


import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Course roll represents the roll of students as a linked abstract list for the
 * course Students can enroll and drop in and out of the course roll.
 * 
 * @author Abby Bowyer
 * @author Neil Towne
 */
public class CourseRoll {

	/** the linked abstract list of students in the roll for the course */
	private LinkedAbstractList<Student> roll;

	/** the enrollment capacity for the course */
	private int enrollmentCap;

	/** the minimum number of students allowed to enroll in a course */
	private static final int MIN_ENROLLMENT = 10;

	/** the maximum number of students allowed to enroll in a course */
	private static final int MAX_ENROLLMENT = 250;

	/** the waitlist size */
	private static final int WAITLIST_SIZE = 10;
	
	/** the course object to use */
	private Course course;

	/**
	 * wait list for a course containing students who attempt to enroll after max
	 * enrollment point
	 */
	private ArrayQueue<Student> waitlist;

	/**
	 * Constructs a course roll of students
	 * 
	 * @param enrollmentCap the capacity of students for the course
	 * @param course		the course for to look at
	 */
	public CourseRoll(int enrollmentCap, Course course) {
		if (course == null || enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		setEnrollmentCap(enrollmentCap);
		waitlist = new ArrayQueue<Student>(WAITLIST_SIZE);
		this.course = course;
	}

	/**
	 * Gets the enrollment capacity for the course
	 * 
	 * @return enrollmentCap the capacity of students for the course
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollment capacity to the provided capacity
	 * 
	 * @param enrollmentCap
	 *            the capacity of students for the course
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT || enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment capacity.");
		}
		this.enrollmentCap = enrollmentCap;
		roll.setCapacity(enrollmentCap);
	}

	/**
	 * Adds the student to the course roll by enrolling them in the course
	 * 
	 * @param student
	 *            the student to enroll
	 * @throws IllegalArgumentException
	 *             if course and waitlist are full.
	 */
	public void enroll(Student student) {
		if (student == null || roll == null) {
			throw new IllegalArgumentException("Student cannot be enrolled in this course.");
		}
		
		if (roll.contains(student)) {
			throw new IllegalArgumentException();
		}
		if (roll.size() == enrollmentCap) {
			if (waitlist.size() < WAITLIST_SIZE) {
				waitlist.enqueue(student);
			} else {
				throw new IllegalArgumentException();
			}
		} else {

			roll.add(roll.size(), student);

		}
	}

	/**
	 * Removes the student from the course by dropping the course from their
	 * schedule and removing them from the course roll
	 * 
	 * @param student
	 *            the student to drop
	 */
	public void drop(Student student) {
		if (student == null) {
			throw new IllegalArgumentException();
		}
		if (roll == null || roll.size() == 0) {
			throw new IllegalArgumentException();
		}
		if (roll.contains(student)) {
			try {
				roll.remove(student);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
			
			if (!waitlist.isEmpty()) {
				Student waitlistStudent = waitlist.dequeue();
				roll.add(roll.size(), waitlistStudent);
				waitlistStudent.getSchedule().addCourseToSchedule(course);
			} 
		} else {
			for (int i = 0; i < waitlist.size(); i++) {
				Student studentInLine = waitlist.dequeue();
				if (!studentInLine.equals(student)) {
					waitlist.enqueue(studentInLine);
				}
			}
		}
	}

	/**
	 * Gets the amount of open seats left
	 * 
	 * @return the amount of open seats left
	 */
	public int getOpenSeats() {
		int openSeats = enrollmentCap - roll.size();
		return openSeats;
	}

	/**
	 * Returns true if there is room for a student to enroll in the course
	 * 
	 * @param student
	 *            the student to enroll
	 * @return true or false for if the student can enroll
	 */
	public boolean canEnroll(Student student) {
		if (getOpenSeats() == 0 && getNumberOnWaitlist() == 10) {
			return false;
		}
		
		// if student is enrolled already return false
		for (int i = 0; i < roll.size(); i++) {
			if (roll.get(i).getId().equals(student.getId())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * gets the current number of the waitlist based on the size of the list
	 * @return the size of the list
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
