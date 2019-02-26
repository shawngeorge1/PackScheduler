package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test activity abstract class
 *
 * @author msleonar
 */
public class ActivityTest {

    /**
     * Test activity tests method, check conflict
     */
    @Test
    public void testCheckConflict() {
        Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "MW", 1330, 1445);
        Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "TH", 1330, 1445);
        try {
            a1.checkConflict(a2);
            assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
            assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM",
                    a2.getMeetingString());
        } catch (ConflictException e) {
            fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
        }

        // Update a1 with the same meeting days and a start time that overlaps the end
        // time of a2
        a1.setMeetingDays("TH");
        a1.setActivityTime(1445, 1530);
        try {
            a1.checkConflict(a2);
            fail(); // ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }

        // Test commutative
        try {
            a2.checkConflict(a1);
            fail(); // ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }

        // Test conflicting on only one day
        a1.setMeetingDays("TW");
        try {
            a1.checkConflict(a2);
            fail(); // ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            assertEquals("TW 2:45PM-3:30PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }

        // Test conflicts the other way around
        a1.setActivityTime(1230, 1330);
        try {
            a1.checkConflict(a2);
            fail(); // ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            assertEquals("TW 12:30PM-1:30PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }

        // Test conflicts a couple more ways for condition coverage
        a1.setActivityTime(1335, 1445);
        try {
            a1.checkConflict(a2);
            fail(); // ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            assertEquals("TW 1:35PM-2:45PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }

        a1.setActivityTime(1330, 1445);
        try {
            a1.checkConflict(a2);
            fail(); // ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            assertEquals("TW 1:30PM-2:45PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }
        a1.setActivityTime(1446, 1447);
        try {
            a1.checkConflict(a2);
            assertEquals("TW 2:46PM-2:47PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            fail();
        }
        
        a2.setMeetingDays("A");
        try {
            a1.checkConflict(a2);
            assertEquals("TW 2:46PM-2:47PM", a1.getMeetingString());
            assertEquals("Arranged", a2.getMeetingString());
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            fail();
        }
        
        a1.setMeetingDays("A");
        try {
            a1.checkConflict(a2);
            assertEquals("Arranged", a1.getMeetingString());
            assertEquals("Arranged", a2.getMeetingString());
        } catch (ConflictException e) {
            // Check that the internal state didn't change during method call.
            fail();
        }
    }

}
