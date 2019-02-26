package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * This class represents a Course for the scheduler
 * 
 * @author msleonar
 */
public class Course extends Activity implements Comparable<Course> {
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course name validator object */
	private CourseNameValidator validator;
	/** Course roll object of students in the course */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name
	 *            name of Course
	 * @param title
	 *            title of Course
	 * @param section
	 *            section of Course
	 * @param credits
	 *            credit hours for Course
	 * @param instructorId
	 *            instructor's unity id
	 * @param enrollmentCap
	 *            the capacity of students for the course
	 * @param meetingDays
	 *            meeting days for Course as series of chars
	 * @param startTime
	 *            start time of the course in military time
	 * @param endTime
	 *            end time of the course in military time
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		validator = new CourseNameValidator();
		roll = new CourseRoll(enrollmentCap, this);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name
	 *            name of Course
	 * @param title
	 *            title of Course
	 * @param section
	 *            section of Course
	 * @param credits
	 *            credit hours for Course
	 * @param instructorId
	 *            instructor's unity id
	 * @param enrollmentCap
	 *            the capacity of students for the course
	 * @param meetingDays
	 *            meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 4 or
	 * greater than 6, an IllegalArgumentException is thrown.
	 * 
	 * @param name
	 *            the name to set
	 * @throws IllegalArgumentException
	 *             if name is null or length is less than 4 or greater than 6 or is
	 *             not valid according to validator
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() < 4 || name.length() > 6) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		// not sure if this if is right
		// also not sure if the throws is right but eclipse didn't like it
		try {
			if (!validator.isValid(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section. If the section is null or not exactly three
	 * digits, an IllegalArgumentException is thrown.
	 * 
	 * @param section
	 *            the section to set
	 * @throws IllegalArgumentException
	 *             if section is null or not exactly three digits
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section number.");
		}
		if (section.length() != 3) {
			throw new IllegalArgumentException("Invalid section number.");
		}
		if (!Character.isDigit(section.charAt(0)) || !Character.isDigit(section.charAt(1))
				|| !Character.isDigit(section.charAt(2))) {
			throw new IllegalArgumentException("Invalid section number.");
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credits.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits. If the credits are not between 1 and 5
	 * (inclusive), an IllegalArgumentException is thrown.
	 * 
	 * @param credits
	 *            the credits to set
	 * @throws IllegalArgumentException
	 *             if credits is not between 1 and 5 (inclusive)
	 */
	public void setCredits(int credits) {
		if (credits < 1 || credits > 5) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructors id.
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor id. If the instructor id is null or an empty
	 * string digits, an IllegalArgumentException is thrown.
	 * 
	 * @param instructorId
	 *            the instructor id to set
	 * @throws IllegalArgumentException
	 *             if instructor id is null or an empty string
	 */
	public void setInstructorId(String instructorId) {
		// if (instructorId == null) {
		// throw new IllegalArgumentException("Invalid instructor unity id.");
		// }
		// if (instructorId.equals("")) {
		// throw new IllegalArgumentException("Invalid instructor unity id.");
		// }
		this.instructorId = instructorId;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (meetingDays.equals("A")) {
			return name + "," + title + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + meetingDays;
		}
		return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap()
				+ "," + meetingDays + "," + startTime + "," + endTime;
	}

	/**
	 * Get the short display string
	 * 
	 * @return String[] short display array of course
	 */
	public String[] getShortDisplayArray() {
		String[] shortDisplayArray = { getName(), getSection(), getTitle(), getMeetingString(),
				"" + roll.getOpenSeats() };
		return shortDisplayArray;
	}

	/**
	 * Get the long display string
	 * 
	 * @return String[] short display array of course
	 */
	public String[] getLongDisplayArray() {
		String[] longDisplayArray = { getName(), getSection(), getTitle(), getCredits() + "", getInstructorId(),
				getMeetingString(), "" };
		return longDisplayArray;
	}

	/**
	 * Sets the Course's meeting days. If the meeting days consist of any characters
	 * other then M, T, W, H, F, or A, or contains an A and any other character, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param meetingDays
	 *            the meeting days to set
	 * @throws IllegalArgumentException
	 *             if the meeting days consist of any characters other then M, T, W,
	 *             H, F, or A, or contains an A and any other character
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null) {
			throw new IllegalArgumentException("Invalid course times.");
		}
		for (char ch : meetingDays.toCharArray()) {
			if ("MTWHFA".indexOf(ch) == -1) {
				throw new IllegalArgumentException("Invalid course times.");
			}
		}
		if (meetingDays.indexOf("A") != -1 && !meetingDays.equals("A")) {
			throw new IllegalArgumentException("Invalid course times.");
		}
		super.setMeetingDays(meetingDays);
	}

	/*
	 * Get the courses has code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/*
	 * Check if the object is equal on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;

		if (!getName().equals(other.getName()))
			return false;
		if (!getSection().equals(other.getSection()))
			return false;
		return true;
	}

	/**
	 * Check if the two courses are the same
	 * 
	 * @param activity
	 *            the activity to check
	 * @return true if the courses have the same name
	 */
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course other = (Course) activity;
			return this.getName().equals(other.getName());
		}
		return false;
	}

	@Override
	public int compareTo(Course course) {
		String thisString = this.getName() + this.getSection();
		String courseString = course.getName() + course.getSection();
		return thisString.compareTo(courseString);
	}

	/**
	 * Gets the roll list of the course
	 * 
	 * @return roll the list of roll for the course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}