package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the course catalog class
 * @author msleonar
 */
public class CourseCatalogTest {
    
    /** Valid course records */
    private final String validTestFile = "test-files/course_records.txt";
    /** Invalid course records */
    private final String invalidTestFile = "test-files/really_invalid_i_promise_invalid_course_records.txt";
    /** Course records place we can save to */
    private final String validSaveDestination = "test-files/actual_course_catalog.txt";

    /** Course name */
    private static final String NAME = "CSC216";
    /** Course title */
    private static final String TITLE = "Programming Concepts - Java";
    /** Course section */
    private static final String SECTION = "001";
    /** Course credits */
    private static final int CREDITS = 4;
    /** Course instructor id */
    private static final String INSTRUCTOR_ID = "sesmith5";
    /** student enrollment capacity for the class */
    private static final int ENROLLMENT_CAP = 100;
    /** Course meeting days */
    private static final String MEETING_DAYS = "TH";
    /** Course start time */
    private static final int START_TIME = 1330;
    /** Course end time */
    private static final int END_TIME = 1445;

    /**
     * Test the basic ability to add and clear from the catalog
     */
    @Test
    public void testAddClearCatalog() {
        CourseCatalog courseCatalog = new CourseCatalog();
        assertEquals(0, courseCatalog.getCourseCatalog().length);
        
        courseCatalog.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        assertEquals(1, courseCatalog.getCourseCatalog().length);
        
        
        courseCatalog.newCourseCatalog();
        assertEquals(0, courseCatalog.getCourseCatalog().length);
    }
    
    /**
     * Test CourseCatalog.getCourseFromCatalog().
     */
    @Test
    public void testGetCourseFromCatalog() {
        CourseCatalog ws = new CourseCatalog();

        // Attempt to get a course that doesn't exist
        assertNull(ws.getCourseFromCatalog("CSC492", "001"));

        // Attempt to get a course that does exist
        Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        ws.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        assertEquals(c, ws.getCourseFromCatalog("CSC216", "001"));
    }
    
    /**
     * Test load courses from file
     */
    @Test
    public void testLoadCoursesFromFile() {
        CourseCatalog cC = new CourseCatalog();
        cC.loadCoursesFromFile(validTestFile);
        assertTrue(cC.getCourseCatalog().length > 0);
        
        cC.newCourseCatalog();
        try {
            cC.loadCoursesFromFile(invalidTestFile);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(0, cC.getCourseCatalog().length);
        }
    }
    
    /**
     * Test remove courses from catalog
     */
    @Test
    public void testRemoveCoursesFromCatalog() {
        CourseCatalog cC = new CourseCatalog();
        assertFalse(cC.removeCourseFromCatalog(NAME, SECTION));
        cC.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        assertTrue(cC.removeCourseFromCatalog(NAME, SECTION));
    }
    
    /**
     * Test save course catalog to file
     */
    @Test
    public void testSaveCourseCatalog() {
        CourseCatalog cC = new CourseCatalog();
        cC.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        cC.saveCourseCatalog(validSaveDestination);
        cC.newCourseCatalog();
        cC.loadCoursesFromFile(validSaveDestination);
        assertEquals(TITLE, cC.getCourseFromCatalog(NAME, SECTION).getTitle());
        
    }
}
