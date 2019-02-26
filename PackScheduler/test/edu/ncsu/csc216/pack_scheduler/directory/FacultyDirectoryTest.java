package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for FacultyDirectory testing that each method works as intended.
 * @author Neil Towne
 * @author aabowyer
 *
 */
public class FacultyDirectoryTest {
    /** Valid course records */
    private final String validTestFile = "test-files/faculty_records.txt";
    /** Invalid course records */
    private final String invalidTestFile = "test-files/faek";
    /** Test first name */
    private static final String FIRST_NAME = "Fac";
    /** Test last name */
    private static final String LAST_NAME = "Ulty";
    /** Test id */
    private static final String ID = "fculty";
    /** Test email */
    private static final String EMAIL = "fculty@ncsu.edu";
    /** Test password */
    private static final String PASSWORD = "pw";
    /** Test max credits */
    private static final int MAX_COURSES = 3;
    
    /**
     * Resets course_records.txt for use in other tests.
     * @throws Exception if something fails during setup.
     */
    @Before
    public void setUp() throws Exception {        
        //Reset faculty_records.txt so that it's fine for other needed tests
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
        try {
            Files.deleteIfExists(destinationPath);
            Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
            fail("Unable to reset files");
        }
    }

    /**
     * Tests FacultyDirectory().
     */
    @Test
    public void testFacultyDirectory() {
        //Test that the FacultyDirectory is initialized to an empty list
        FacultyDirectory sd = new FacultyDirectory();
        assertFalse(sd.removeFaculty("sesmith5"));
        assertEquals(0, sd.getFacultyDirectory().length);
    }

    /**
     * Tests FacultyDirectory.testNewFacultyDirectory().
     */
    @Test
    public void testNewFacultyDirectory() {
        //Test that if there are facultys in the directory, they 
        //are removed after calling newFacultyDirectory().
        FacultyDirectory sd = new FacultyDirectory();
        
        sd.loadFacultyFromFile(validTestFile);
        assertEquals(8, sd.getFacultyDirectory().length);
        
        sd.newFacultyDirectory();
        assertEquals(0, sd.getFacultyDirectory().length);
    }

    /**
     * Tests FacultyDirectory.loadFacultyFromFile().
     */
    @Test
    public void testLoadFacultysFromFile() {
        FacultyDirectory sd = new FacultyDirectory();
                
        //Test valid file
        sd.loadFacultyFromFile(validTestFile);
        assertEquals(8, sd.getFacultyDirectory().length);
        
        //Test invalid file
        sd.newFacultyDirectory();
        try {
            sd.loadFacultyFromFile(invalidTestFile);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(0, sd.getFacultyDirectory().length);
        }
    }

    /**
     * Tests FacultyDirectory.addFaculty().
     */
    @Test
    public void testAddFaculty() {
        FacultyDirectory sd = new FacultyDirectory();
        sd.addFaculty(FIRST_NAME, LAST_NAME, ID, "sj@ncsu.edu", "pw", "pw", 3);
        
        //Test valid Faculty
        String [][] facultyDirectory = sd.getFacultyDirectory();
        assertEquals(1, facultyDirectory.length);
        assertEquals(FIRST_NAME, facultyDirectory[0][0]);
        assertEquals(LAST_NAME, facultyDirectory[0][1]);
        assertEquals(ID, facultyDirectory[0][2]);
        
        //Test invalid Faculty
        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(1, sd.getFacultyDirectory().length);
        }
        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_COURSES);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(1, sd.getFacultyDirectory().length);
        }
        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_COURSES);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(1, sd.getFacultyDirectory().length);
        }
        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_COURSES);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(1, sd.getFacultyDirectory().length);
        }
        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "1", MAX_COURSES);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(1, sd.getFacultyDirectory().length);
        }

        assertFalse(sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
        
        assertTrue(sd.addFaculty(FIRST_NAME, LAST_NAME, ID + "2", EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
    }

    /**
     * Tests FacultyDirectory.removeFaculty().
     */
    @Test
    public void testRemoveFaculty() {
        FacultyDirectory sd = new FacultyDirectory();
                
        //Add faculty and remove
        sd.loadFacultyFromFile(validTestFile);
        assertEquals(8, sd.getFacultyDirectory().length);
        assertFalse(sd.removeFaculty("efrost"));
        String [][] facultyDirectory = sd.getFacultyDirectory();
        assertEquals(8, facultyDirectory.length);
        assertEquals("Fiona", facultyDirectory[1][0]);
        assertEquals("Meadows", facultyDirectory[1][1]);
        assertEquals("fmeadow", facultyDirectory[1][2]);
    }

    /**
     * Tests FacultyDirectory.saveFacultyDirectory().
     */
    @Test
    public void testSaveFacultyDirectory() {
        FacultyDirectory sd = new FacultyDirectory();
        
        //Add a faculty
        sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
        assertEquals(1, sd.getFacultyDirectory().length);
        sd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
        checkFiles("test-files/faculty_records.txt", "test-files/expected_full_faculty_records.txt");
        
        String slash = "/";
        // Avoid spotbugs/pmd error
        try {
            sd.saveFacultyDirectory(slash + "home/sesmith5/actual_faculty_records.txt");
            fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
        } catch (IllegalArgumentException e) {
            assertEquals("Unable to write to file /home/sesmith5/actual_faculty_records.txt", e.getMessage());
            //The actual error message on Jenkins!
        }
    }
    
    /**
     * Helper method to compare two files for the same contents
     * @param expFile expected output
     * @param actFile actual output
     */
    private void checkFiles(String expFile, String actFile) {
        try {
            Scanner expScanner = new Scanner(new FileInputStream(expFile));
            Scanner actScanner = new Scanner(new FileInputStream(actFile));
            
            while (expScanner.hasNextLine()) {
                assertEquals(expScanner.nextLine(), actScanner.nextLine());
            }
            
            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }
    
    /**
     * Tests FacultyDirectory.removeFaculty().
     */
    @Test
    public void testGetFacultyById() {
        FacultyDirectory sd = new FacultyDirectory();
        
        sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
        assertEquals(FIRST_NAME, sd.getFacultyById(ID).getFirstName());
        assertNull(sd.getFacultyById("notexistsan"));
        
    }

}

