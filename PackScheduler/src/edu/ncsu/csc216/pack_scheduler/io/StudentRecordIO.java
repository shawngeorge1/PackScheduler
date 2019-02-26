package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * A class which provides helper methods to read and write student records from
 * a file
 * 
 * @author Matthew Leonard
 */
public class StudentRecordIO {

    /**
     * Reads the students records from the file
     * 
     * @param fileName the file to read from
     * @return and array list of students from the file
     * @throws FileNotFoundException if the file is not found
     */
    public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
        SortedList<Student> students = new SortedList<Student>();
        Scanner fileReader = new Scanner(new FileInputStream(fileName));
        while (fileReader.hasNextLine()) {
            try {
                students.add(processStudent(fileReader.nextLine()));
            } catch (NoSuchElementException e) {
                System.out.println("Skipped student.");
            }

        }
        fileReader.close();
        return students;
    }

    /**
     * Writes the student records to a file
     * 
     * @param fileName         the file to write to
     * @param studentDirectory the students to write to the file
     * @throws IOException if the file cannot be read
     */
    public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));
        for (int i = 0; i < studentDirectory.size(); i++) {
            fileWriter.println(studentDirectory.get(i).toString());
        }
        fileWriter.close();
    }

    /**
     * Processes a student from a string using a scanner delimited by commas
     * 
     * @param line to process
     * @return a student object
     */
    public static Student processStudent(String line) {
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter(",");
        try {
            Student student = new Student(lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.next(),
                lineScanner.next(), lineScanner.nextInt());
            lineScanner.close();
            return student;
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException();
        }
    }

}
