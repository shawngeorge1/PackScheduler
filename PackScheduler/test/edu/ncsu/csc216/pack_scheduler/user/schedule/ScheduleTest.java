/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test class for the Schedule class and it's methods
 * 
 * @author Abby Bowyer
 */
public class ScheduleTest {

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
    /** Course enrollment capacity of students */
    private static final int ENROLLMENT_CAP = 100;
    /** Course meeting days */
    private static final String MEETING_DAYS = "TH";
    /** Course start time */
    private static final int START_TIME = 1330;
    /** Course end time */
    private static final int END_TIME = 1445;

    /**
     * Tests Schedule constructor
     */
    @Test
    public void testSchedule() {
        // Test with invalid file. Should have an empty catalog and schedule.
        Schedule s = new Schedule();
        assertEquals(0, s.getScheduledCourses().length);
    }

    /**
     * Test Schedule.addCourseToSchedule().
     */
    @Test
    public void testaddCourseToSchedule() {
        Schedule s = new Schedule();

        // Attempt to add a course that doesn't exist
        assertEquals(0, s.getScheduledCourses().length);

        Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);

        // Attempt to add a course that does exist
        assertTrue(s.addCourseToSchedule(c1));
        assertEquals(1, s.getScheduledCourses().length);

        // Attempt to add a course that already exists, even if different section
        try {
            s.addCourseToSchedule(c1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("You are already enrolled in CSC216", e.getMessage());
        }

        // attempt to add course that is conflicting
        Course c2 = new Course("CSC116", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        try {
            s.addCourseToSchedule(c2);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The course cannot be added due to a conflict.", e.getMessage());
        }
        
        // attempt to add course that we are already enrolled in
        Course c3 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 200, END_TIME + 200);
        try {
            s.addCourseToSchedule(c3);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("You are already enrolled in CSC216", e.getMessage());
        }
    }

    /**
     * Test Schedule.removeCourseFromSchedule().
     */
    @Test
    public void testremoveCourseFromSchedule() {
        Schedule s = new Schedule();
        Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Course c2 = new Course("CSC226", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 200,
                END_TIME + 200);
        Course c3 = new Course("CSC316", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 400,
                END_TIME + 400);

        // Attempt to remove from empty schedule
        assertFalse(s.removeCourseFromSchedule(c1));

        // Add some courses and remove them
        assertTrue(s.addCourseToSchedule(c1));
        assertTrue(s.addCourseToSchedule(c2));
        assertTrue(s.addCourseToSchedule(c3));
        assertEquals(3, s.getScheduledCourses().length);

        // Check that removing a course that doesn't exist when there are
        // scheduled courses doesn't break anything
//		assertFalse(s.removeCourseFromSchedule(c));
        assertEquals(3, s.getScheduledCourses().length);

        // Remove CSC216
        assertTrue(s.removeCourseFromSchedule(c1));
        assertEquals(2, s.getScheduledCourses().length);

        // Remove CSC226
        assertTrue(s.removeCourseFromSchedule(c2));
        assertEquals(1, s.getScheduledCourses().length);

        // Remove CSC316
        assertTrue(s.removeCourseFromSchedule(c3));
        assertEquals(0, s.getScheduledCourses().length);

        // Check that removing all doesn't break future adds
		assertTrue(s.addCourseToSchedule(c1));
        assertEquals(1, s.getScheduledCourses().length);
    }

    /**
     * Test Schedule.resetSchedule()
     */
    @Test
    public void testResetSchedule() {
        Schedule s = new Schedule();
        Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Course c2 = new Course("CSC226", TITLE, "004", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 200,
                END_TIME + 200);
        Course c3 = new Course("CSC316", TITLE, "005", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 400,
                END_TIME + 400);

        // Add some courses and reset schedule
        assertTrue(s.addCourseToSchedule(c1));
        assertTrue(s.addCourseToSchedule(c2));
        assertTrue(s.addCourseToSchedule(c3));
        assertEquals(3, s.getScheduledCourses().length);

        s.resetSchedule();
        assertEquals(0, s.getScheduledCourses().length);

        // Check that resetting doesn't break future adds
        assertTrue(s.addCourseToSchedule(c1));
        assertEquals(1, s.getScheduledCourses().length);
    }

    /**
     * Test Schedule.getCourseCatalog().
     */
    @Test
    public void testGetScheduledCourses() {
        Schedule s = new Schedule();

        Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Course c2 = new Course("CSC226", TITLE, "004", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 200,
                END_TIME + 200);
        Course c3 = new Course("CSC316", TITLE, "005", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME + 400,
                END_TIME + 400);
        
        assertTrue(s.addCourseToSchedule(c1));
        assertTrue(s.addCourseToSchedule(c2));
        assertTrue(s.addCourseToSchedule(c3));

        // Get the catalog and make sure contents are correct
        // Name, section, title
        String[][] catalog = s.getScheduledCourses();
        // Row 0
        assertEquals("CSC216", catalog[0][0]);
        assertEquals("001", catalog[0][1]);
        assertEquals("Programming Concepts - Java", catalog[0][2]);
        assertEquals("TH 1:30PM-2:45PM", catalog[0][3]);
        // Row 1
        assertEquals("CSC226", catalog[1][0]);
        assertEquals("004", catalog[1][1]);
        assertEquals("Programming Concepts - Java", catalog[1][2]);
        assertEquals("TH 3:30PM-4:45PM", catalog[1][3]);
        // Row 2
        assertEquals("CSC316", catalog[2][0]);
        assertEquals("005", catalog[2][1]);
        assertEquals("Programming Concepts - Java", catalog[2][2]);
        assertEquals("TH 5:30PM-6:45PM", catalog[2][3]);
    }

    /**
     * Test Schedule.setTitle().
     */
    @Test
    public void testSetTitle() {
        Schedule s = new Schedule();

        // Set Title and check that changed
        s.setTitle("New Title");

        // Check that exception is thrown if null title and no
        // change to title already there.
        try {
            s.setTitle(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("New Title", s.getTitle());
        }
    }

}
