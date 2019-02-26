package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads and writes course records from file system
 * 
 * @author Matthew Leonard
 */
public class CourseRecordIO {

	/**
	 * Read the course records from the file name and return them without duplicates
	 * as an array list of Course's
	 * 
	 * @param fileName
	 *            file to read from
	 * @return List of courses from file
	 * @throws FileNotFoundException
	 *             if the file is not found by FileInputStream
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Course> courses = new SortedList<Course>();
		while (fileReader.hasNextLine()) {
			try {
				Course course = readCourse(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < courses.size(); i++) {
					Course c = courses.get(i);
					if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					courses.add(course);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return courses;
	}

	/**
	 * Read the course from a string
	 * 
	 * @param line
	 *            nextLine string representing the course in csv
	 * @return Course object
	 * @throws IllegalArgumentException
	 *             if line does not contain all necessary elements, or too many
	 */
	private static Course readCourse(String line) {
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(",");

		try {
			String name = scanner.next();
			String title = scanner.next();
			String section = scanner.next();
			int credits = scanner.nextInt();
			String newId = null;
			if (!scanner.hasNextInt()) {
				newId = scanner.next();
			}
			int enrollmentCap = scanner.nextInt();
			String meetingDays = scanner.next();

			if (meetingDays.equals("A")) {
				// Throw an error if there is a endTime listed
				if (scanner.hasNext()) {
					scanner.close();
					throw new IllegalArgumentException();
				}

				scanner.close();
				Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
				Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(newId);
				if (f != null) {
					f.getSchedule().addCourseToSchedule(course);
				}
				return course;
			}

			int startTime = scanner.nextInt();
			int endTime = scanner.nextInt();

			scanner.close();
			Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime,
					endTime);
			Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(newId);
			if (f != null) {
				f.getSchedule().addCourseToSchedule(course);
			}
			return course;
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Write the list of courses to a file.
	 * 
	 * @param fileName
	 *            file to write courses to
	 * @param courses
	 *            courses to write to the file
	 * @throws IOException
	 *             if there is an error writing to the file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}