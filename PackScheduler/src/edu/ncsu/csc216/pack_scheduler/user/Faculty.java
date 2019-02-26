package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Represents a faculty member who teaches a certain amount of courses
 * 
 * @author Abby Bowyer
 */
public class Faculty extends User {

	/** the max number of courses for the faculty member */
	private int maxCourses;

	/** the minimum number of courses a faculty member can teach */
	public static final int MIN_COURSES = 1;

	/** the maximum number of courses a faculty member can teach */
	public static final int MAX_COURSES = 2;

	/** Faculty schedule representing courses assigned to faculty memeber */
	public FacultySchedule facultySchedule;

	/**
	 * Constructs a Faculty member using the given parameters
	 * 
	 * @param firstName
	 *            the first name of the faculty member
	 * @param lastName
	 *            the lase name of the faculty member
	 * @param id
	 *            the id of the faculty member
	 * @param email
	 *            the email of the faculty member
	 * @param password
	 *            the password of the faculty member
	 * @param maxCourses
	 *            the max courses for the faculty member
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		facultySchedule = new FacultySchedule(id);
	}

	/**
	 * Sets the max courses parameter to the given number of max courses parameter
	 * 
	 * @param maxCourses
	 *            the max number of courses for a faculty member
	 * @throws IllegalArgumentException
	 *             when the max courses is invalid (less than 1 or more than 3)
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < 1 || maxCourses > 3) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Gets the max courses for the faculty
	 * 
	 * @return maxCourses the maximum courses for the faculty member
	 */
	public int getMaxCourses() {
		return maxCourses;
	}




    /**
     * Returns the FacultySchedule
     * @return facultySchedule	the current schedule for the faculty member
     */
    public FacultySchedule getSchedule() {
        return facultySchedule;
    }


	/**
	 * Determines if faculty is overloaded if their scheduled courses is greater
	 * than their max courses
	 * 
	 * @return true if number of scheduled courses is greater than max courses false
	 *         if number of scheduled courses is less than max courses
	 */
	public boolean isOverloaded() {
		return (facultySchedule.getScheduledCourses().length > MAX_COURSES);
	}

	/**
	 * Get the hash code of the faculty
	 * 
	 * @return hash code of faculty based on its max credits
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Get whether a faculty is equal to the given object based on its max credits
	 * 
	 * @param obj
	 *            object to check
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	/**
	 * Returns Faculty member's fields as a comma separated string
	 * 
	 * @return Faculty member's fields as a comma separated string
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ getMaxCourses();
	}
}
