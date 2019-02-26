package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Creates a student to hold in the student directory.
 * 
 * @author Matthew Leonard
 * @author Harper Atkins
 * @author S. Isaac Kellogg
 */
public class Student extends User implements Comparable<Student> {

    /** Students maximum credits */
    private int maxCredits;

    /** Any students maximum possible max credits */
    public static final int MAX_CREDITS = 18;

    /** Schedule of the student */
    private Schedule schedule;

    /**
     * Default constructor
     * 
     * @param firstName  Student's first name
     * @param lastName   Student's last name
     * @param id         Student's id
     * @param email      Student's email
     * @param password   Student's password
     * @param maxCredits Student's max allowed credits
     */
    public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
        super(firstName, lastName, id, email, password);
        this.setMaxCredits(maxCredits);
        this.schedule = new Schedule();
    }

    /**
     * Constructor which assumes max credits is the default value
     * 
     * @param firstName Student's first name
     * @param lastName  Student's last name
     * @param id        Student's id
     * @param email     Student's email
     * @param password  Student's password
     */
    public Student(String firstName, String lastName, String id, String email, String password) {
        this(firstName, lastName, id, email, password, MAX_CREDITS);
    }

    /**
     * Returns maximum allowed credits for the student
     * 
     * @return the maxCredits
     */
    public int getMaxCredits() {
        return maxCredits;
    }

    /**
     * Sets the maximum allowed credits for the student
     * 
     * @param maxCredits the maxCredits to set
     */
    public void setMaxCredits(int maxCredits) {
        if (maxCredits < 3 || maxCredits > 18) {
            throw new IllegalArgumentException("Invalid max credits");
        }
        this.maxCredits = maxCredits;
    }

    /**
     * Returns student's fields as a comma separated string
     * 
     * @return Student's fields as a comma separated string
     */
    @Override
    public String toString() {
        return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
                + getMaxCredits();
    }

    /**
     * Compares a student to another Student, returning
     * 
     * @param student student to compare to
     * @return int which student is higher
     */
    public int compareTo(Student student) {
        String thisStudent = this.getLastName() + this.getFirstName() + this.getId();
        String paramStudent = student.getLastName() + student.getFirstName() + student.getId();
        return thisStudent.compareTo(paramStudent);
    }

    /**
     * Get the hash code of the student
     * 
     * @return hash code of student based on its max credits
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + maxCredits;
        return result;
    }

    /**
     * Get whether a student is equal to the given object based on its max credits
     * 
     * @param obj object to check
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (maxCredits != other.maxCredits)
            return false;
        return true;
    }

    /**
     * Returns the student's schedule
     * 
     * @return schedule of the student.
     */
    public Schedule getSchedule() {
        return this.schedule;
    }
    
    /**
     * Decides if the student can be added to the course
     * @param course	the course for the student to be added to
     * @return true or false if the student can add the course
     */
    public boolean canAdd(Course course) {
		if(course.getCredits() > this.getMaxCredits())
		{
			return false;
		}
		if(schedule.getScheduleCredits() + course.getCredits() > this.getMaxCredits())
		{
			return false;
		}
		if (schedule.canAdd(course)) {
			return true;
		}
		return false;
    }
}
