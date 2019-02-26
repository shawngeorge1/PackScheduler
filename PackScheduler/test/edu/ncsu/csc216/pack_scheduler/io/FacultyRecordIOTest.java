/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Test cases for FacultyRecordIO ensuring each method works properly.
 * @author Neil Towne
 *
 */
public class FacultyRecordIOTest {

    private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
    private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
    private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
    private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
    private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
    private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
    private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
    private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

    private String [] validFaculty = {validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5,
            validFaculty6, validFaculty7};

    private String hashPW;
    private static final String HASH_ALGORITHM = "SHA-256";
    @SuppressWarnings("unused")
    private FacultyRecordIO frIO;
    
    private void assertFileEquals(String expFile, String actFile) {
        try {
            Scanner expScanner = new Scanner(new FileInputStream(expFile));
            Scanner actScanner = new Scanner(new FileInputStream(actFile));
            
            while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
                String exp = expScanner.nextLine();
                String act = actScanner.nextLine();
                assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
            }
            if (expScanner.hasNextLine()) {
                fail("The expected results expect another line " + expScanner.nextLine());
            }
            if (actScanner.hasNextLine()) {
                fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
            }
            
            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }

    /**
     * Set up any faculty record io test
     */
    @Before
    public void setUp() {
        frIO = new FacultyRecordIO();
        try {
            String password = "pw";
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            hashPW = new String(digest.digest());
            
            for (int i = 0; i < validFaculty.length; i++) {
                validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
            }
        } catch (NoSuchAlgorithmException e) {
            fail("Unable to create hash during setup");
        }
    }

    /**
     * Test read method of read faculty records
     */
    @Test
    public void testReadFacultyRecords() {
        try {
            LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
            for (int i = 0; i < faculty.size(); i++) {
                assertEquals(validFaculty[i], faculty.get(i).toString());
            }
        } catch (FileNotFoundException e) {
            fail("bad, find a real file");
        }
        
        try {
            LinkedList<Faculty> facultys = FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt");
            assertEquals(0, facultys.size());
        } catch (FileNotFoundException e) {
            fail("bad, find a real file");
        }
        
        LinkedList<Faculty> fakeFacultys = null;
        try {
            fakeFacultys = FacultyRecordIO.readFacultyRecords("faek");
            fail("Read from fake file");
        } catch (FileNotFoundException e) {
            assertNull(fakeFacultys);
        }
    }

    /**
     * Test method for writeFacultyRecords method
     */
    @Test
    public void testWriteFacultyRecords() {
        LinkedList<Faculty> facultys = new LinkedList<Faculty>();
        for(int i = 0; i < validFaculty.length; i++) {
            facultys.add(processFaculty(validFaculty[i]));
        }
        try {
            FacultyRecordIO.writeFacultyRecords("test-files/faculty_records.txt", facultys);
            assertFileEquals("test-files/expected_full_faculty_records.txt", "test-files/faculty_records.txt");
        } catch (IOException e) {
            fail("Couldnt write facultys");
        }
    }
    
    /**
     * Test write faculty records with the wrong permissions
     */
    @Test
    public void testWriteFacultyRecordsNoPermissions() {
        LinkedList<Faculty> facultys = new LinkedList<Faculty>();
        facultys.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 2));
        //Assumption that you are using a hash of "pw" stored in hashPW
        // Averts weird PMD error which we really shouldn't care about tests
        String slash = "/";
        try {
            FacultyRecordIO.writeFacultyRecords(slash + "home/sesmith5/faculty_records.txt", facultys);
            fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
        } catch (IOException e) {
            assertEquals("/home/sesmith5/faculty_records.txt (Permission denied)", e.getMessage());
            //The actual error message on Jenkins!
        }
    }

    /**
     * Processes a faculty member from a string using a scanner delimited by commas
     * helper method to test writing to file.
     * 
     * @param line to process
     * @return a Faculty object
     */
    private static Faculty processFaculty(String line) {
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter(",");
        try {
            Faculty faculty = new Faculty(lineScanner.next(), lineScanner.next(), lineScanner.next(),
                    lineScanner.next(), lineScanner.next(), lineScanner.nextInt());
            lineScanner.close();
            return faculty;
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException();
        }
    }
}

