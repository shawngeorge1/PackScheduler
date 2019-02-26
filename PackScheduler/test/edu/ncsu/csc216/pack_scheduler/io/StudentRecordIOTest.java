/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test student record IO
 * @author msleonar
 *
 */
public class StudentRecordIOTest {
    
    private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
    private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
    private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
    private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
    private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
    private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
    private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
    private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
    private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
    private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

    private String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
            validStudent6, validStudent7, validStudent8, validStudent9};

    private String hashPW;
    private static final String HASH_ALGORITHM = "SHA-256";
    @SuppressWarnings("unused")
    private StudentRecordIO srIO;
    
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
     * Set up any student record io test
     */
    @Before
    public void setUp() {
        srIO = new StudentRecordIO();
        try {
            String password = "pw";
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            hashPW = new String(digest.digest());
            
            for (int i = 0; i < validStudents.length; i++) {
                validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
            }
        } catch (NoSuchAlgorithmException e) {
            fail("Unable to create hash during setup");
        }
    }

    /**
     * Test read method of read student records
     */
    @Test
    public void testReadStudentRecords() {
        try {
            SortedList<Student> students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
            for (int i = 0; i < students.size(); i++) {
                assertEquals(validStudents[i], students.get(i).toString());
            }
        } catch (FileNotFoundException e) {
            fail("bad, find a real file");
        }
        
        try {
            SortedList<Student> students = StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt");
            assertEquals(0, students.size());
        } catch (FileNotFoundException e) {
            fail("bad, find a real file");
        }
        
        SortedList<Student> fakeStudents = null;
        try {
            fakeStudents = StudentRecordIO.readStudentRecords("faek");
            fail("Read from fake file");
        } catch (FileNotFoundException e) {
            assertNull(fakeStudents);
        }
    }

    /**
     * Test method for writeStudentRecords method
     */
    @Test
    public void testWriteStudentRecords() {
        SortedList<Student> students = new SortedList<Student>();
        for (String student : validStudents) {
            students.add(StudentRecordIO.processStudent(student));
        }
        try {
            StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
            assertFileEquals("test-files/sorted_student_records.txt", "test-files/actual_student_records.txt");
        } catch (IOException e) {
            fail("Couldnt write students");
        }
    }
    
    /**
     * Test write student records with the wrong permissions
     */
    @Test
    public void testWriteStudentRecordsNoPermissions() {
        SortedList<Student> students = new SortedList<Student>();
        students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
        //Assumption that you are using a hash of "pw" stored in hashPW
        // Averts weird PMD error which we really shouldn't care about tests
        String slash = "/";
        try {
            StudentRecordIO.writeStudentRecords(slash + "home/sesmith5/actual_student_records.txt", students);
            fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
        } catch (IOException e) {
            assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
            //The actual error message on Jenkins!
        }
    }

}
