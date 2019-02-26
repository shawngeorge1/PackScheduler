/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Manages the student's schedule, it handles addition and removal of courses,
 * also allows to reset the schedule.
 * 
 * @author Tanvi Thummar
 *
 */
public class Schedule {

    /** A custom ArrayList of Courses */
    private ArrayList<Course> schedule;

    /** Title of the schedule */
    private String title;

    /**
     * Creates a schedule and sets the default title.
     */
    public Schedule() {
        schedule = new ArrayList<Course>();
        this.title = "My Schedule";
    }

    /**
     * Adds a course to the schedule if it not already in the schedule, and there is
     * not conflict in the schedule.
     * 
     * @param course Course to be added
     * @return true If the course is added successfully.
     */
    public boolean addCourseToSchedule(Course course) {

        int flag = 0;
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getName().equals(course.getName())) {
                throw new IllegalArgumentException("You are already enrolled in " + course.getName());
            }
            try {
                schedule.get(i).checkConflict(course);
            } catch (ConflictException e) {
                throw new IllegalArgumentException("The course cannot be added due to a conflict.");
            }
        }

        if (flag == 0) {
            if (title == null) {
                return false;
            } else {
                schedule.add(schedule.size(), course);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a course from the schedule if it exists in the schedule.
     * 
     * @param course Course to be removed.
     * @return true If the course is removed successfully.
     */
    public boolean removeCourseFromSchedule(Course course) {

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).equals(course)) {
                schedule.remove(course);
                return true;
            }
        }
        return false;
    }

    /**
     * Resets the schedule to an empty schedule.
     */
    public void resetSchedule() {
        this.schedule = new ArrayList<Course>();
        this.title = "My Schedule";
    }

    /**
     * Returns basic information of the courses in the schedule.
     * 
     * @return s A 2D string array containing basic information about the courses
     */
    public String[][] getScheduledCourses() {
        String[][] s = new String[this.schedule.size()][5];

        for (int i = 0; i < schedule.size(); i++) {
            s[i] = schedule.get(i).getShortDisplayArray();
        }
        return s;
    }

    /**
     * Sets the title of the schedule to the given title.
     * 
     * @param title Title of the schedule
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }

    /**
     * Gets the title of the schedule to the given title.
     * 
     * @return Title of the schedule
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the scheduled amount of credits for the student
     * @return credits	the amount of credits the student is enrolled in
     */
    public int getScheduledCredits() {
    	int credits = 0;
    	for (int i = 0; i < schedule.size(); i++) {
    		credits += schedule.get(i).getCredits();
    	}
    	return credits;
    }
    
    /**
     * gets the number of credits so far in the schedule
     * @return credits	the number of credits in the schedule
     */
    public int getScheduleCredits() {
    	int credits = 0;
    	for (int i = 0; i < schedule.size(); i++) {
    		credits += schedule.get(i).getCredits();
    	}
    	return credits;
    }
    
    /**
     * Returns true if the student can add the course to their schedule
     * @param course	the course to attempt to add to
     * @return true or false if the student can add the course
     */
    public boolean canAdd(Course course) {
    	if (course == null) { 
    		return false;
    	}
    	for (int i = 0; i < schedule.size(); i++) {
    		if (schedule.get(i).getName().equals(course.getName())) {
    			return false;
    		}
    		try {
    			schedule.get(i).checkConflict(course);
    		} catch (ConflictException e) {
    			return false;
    		}
    	}
    	return true;
    }
}
