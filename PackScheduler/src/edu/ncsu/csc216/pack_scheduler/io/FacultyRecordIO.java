/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Faculty class that uses a file to either read in faculty members and
 * information and convert them to Faculty objects in a LinkedList, or writes
 * the current LinkedList if Faculty to a file.
 * 
 * @author Neil Towne
 *
 */
public class FacultyRecordIO {

    /**
     * Reads the faculty records from the file
     * 
     * @param fileName the file to read from
     * @return and linkedList of faculty from the file
     * @throws FileNotFoundException if the file is not found
     */
    public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
        LinkedList<Faculty> faculty = new LinkedList<Faculty>();
        Scanner fileReader = new Scanner(new FileInputStream(fileName));
        while (fileReader.hasNextLine()) {
            try {
                faculty.add(processFaculty(fileReader.nextLine()));
            } catch (NoSuchElementException e) {
                System.out.println("Skipped faculty.");
            }

        }
        fileReader.close();
        return faculty;
    }

    /**
     * Writes the faculty records to a file
     * 
     * @param fileName          the file to write to
     * @param facultyDirectory the faculty to write to the file
     * @throws IOException if the file cannot be read
     */
    public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));
        for (int i = 0; i < facultyDirectory.size(); i++) {
            fileWriter.println(facultyDirectory.get(i).toString());
        }
        fileWriter.close();
    }

    /**
     * Processes a faculty member from a string using a scanner delimited by commas
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
