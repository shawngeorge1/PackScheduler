package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Holds a catalog of courses which can be saved and loaded from the file
 * system, and added and removed from.
 * 
 * @author msleonar
 */
public class CourseCatalog {
    /** Catalog of courses */
    private SortedList<Course> catalog;

    /**
     * Constructs an empty course catalog
     */
    public CourseCatalog() {
        newCourseCatalog();
    }

    /**
     * Constructs a new empty course catalog
     */
    public void newCourseCatalog() {
        catalog = new SortedList<Course>();
    }

    /**
     * Loads courses into the catalog from the file.
     * 
     * @param fileName the file to read courses from
     * @throws IllegalArgumentException if any errors are had reading the file
     */
    public void loadCoursesFromFile(String fileName) throws IllegalArgumentException {
        try {
            catalog = CourseRecordIO.readCourseRecords(fileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a Course object with values for all fields and adds it to the
     * catalog.
     * 
     * @param name         name of Course
     * @param title        title of Course
     * @param section      section of Course
     * @param credits      credit hours for Course
     * @param instructorId instructor's unity id
     * @param meetingDays  meeting days for Course as series of chars
     * @param enrollmentCap	the capacity for enrollment in the course
     * @param startTime    start time of the course in military time
     * @param endTime      end time of the course in military time
     * 
     * @return true if the course is not a duplicate and able to be added
     */
    public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
            int enrollmentCap, String meetingDays, int startTime, int endTime) {
        Course newCourse = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
        for (int i = 0; i < catalog.size(); i++) {
            if (newCourse.isDuplicate(catalog.get(i))) {
                return false;
            }
        }
        return catalog.add(newCourse);
    }

    /**
     * Removes a course by name and section
     * 
     * @param name    the name of the course to remove
     * @param section the section of the course to remove
     * 
     * @return true if the course is in the schedule and can be removed
     */
    public boolean removeCourseFromCatalog(String name, String section) {
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
                catalog.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Get a course from the catalog sharing the name and section
     * 
     * @param name    the name of the course
     * @param section the section of the course
     * @return the course with the same section and name as the parameters. If not
     *         found, return null
     */
    public Course getCourseFromCatalog(String name, String section) {
        for (int i = 0; i < catalog.size(); i++) {
            Course course = catalog.get(i);
            if (course.getName().equals(name) && course.getSection().equals(section)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Get the 2d string array display-able variant of the course catalog
     * 
     * @return 2d string array representation of course catalog, in the order of
     *         name, section, title, and meeting information
     */
    public String[][] getCourseCatalog() {
        String[][] courseCatalogArrays = new String[catalog.size()][];
        for (int i = 0; i < catalog.size(); i++) {
            courseCatalogArrays[i] = catalog.get(i).getShortDisplayArray();
        }
        return courseCatalogArrays;
    }

    /**
     * Export the catalog to the file
     * 
     * @param fileName the file to write to
     */
    public void saveCourseCatalog(String fileName) {
        try {
            CourseRecordIO.writeCourseRecords(fileName, catalog);
        } catch (IOException e) {
            throw new IllegalArgumentException("The file cannot be saved.");
        }
    }
}
