package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages a user registering into the system. Also contains a singleton
 * instance of itself. Also contains a course catalog and student directory.
 *
 * @author Matt
 * @author Abby Bowyer
 */
public class RegistrationManager {

	private static RegistrationManager instance;
	private CourseCatalog courseCatalog;
	private StudentDirectory studentDirectory;
	private FacultyDirectory facultyDirectory;
	private User registrar;
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Create the registrar
	 */
	private RegistrationManager() {
		createRegistrar();
		facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Instantiate the singleton with the properties from registrar.properties. Also
	 * instantiate the course catalog and student directory.
	 */
	private void createRegistrar() {

		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}

		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
	}

	/**
	 * Hash a registrars password using the static hash algorithm (SHA-256)
	 * 
	 * @param pw
	 *            the password to hash
	 * @return the hash of the password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Get the static instance of the registration manager
	 * 
	 * @return the instance of the registration manager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Get the current registration managers course catalog
	 * 
	 * @return the course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Get the current registration managers student directory
	 * 
	 * @return the student directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Get the current registration managers faculty directory
	 * 
	 * @return the faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Login to the registration manager using a students id, and either the
	 * students password or the registrars local password. If the id and password
	 * are valid, set the current user to the students id.
	 * 
	 * @param id
	 *            the users id
	 * @param password
	 *            the unhashed password
	 * @return whether the user was able to log in
	 */
	public boolean login(String id, String password) {
		if (currentUser != null) {
			return false;
		}

		if (registrar.getId().equals(id)) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}

		if (getStudentDirectory().getStudentById(id) != null) {
			Student s = getStudentDirectory().getStudentById(id);
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (s.getPassword().equals(localHashPW)) {
					currentUser = s;
					return true;
				}
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}

		} else if (getFacultyDirectory().getFacultyById(id) != null) {
			Faculty f = facultyDirectory.getFacultyById(id);

			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (f.getPassword().equals(localHashPW)) {
					currentUser = f;
					return true;
				}
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}

		} else {
			throw new IllegalArgumentException("User doesn't exist.");
		}

		return false;
	}

	/**
	 * Log the current user out;
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Get the current user logged in
	 * 
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clear all of the data in the course catalog and student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c
	 *            Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c
	 *            Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Adds the Course to the Faculty's schedule to add the faculty to the course
	 * 
	 * @param course
	 *            the course to add the faculty to
	 * @param faculty
	 *            the faculty to add to the course
	 * @return true or false if the faculty is added
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser == registrar) {
			faculty.facultySchedule.addCourseToSchedule(course);
			return true;
		}
		return false;
	}

	/**
	 * Removes the Course from the Faculty's schedule to remove the faculty from the
	 * course
	 * 
	 * @param course
	 *            the course to remove the faculty from
	 * @param faculty
	 *            the faculty to remove from the course
	 * @return true or false if the faculty is removed
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser == registrar) {
			faculty.facultySchedule.removeCourseFromSchedule(course);
			return true;
		}
		return false;
	}

	/**
	 * Resets the faculty's schedule
	 * 
	 * @param faculty
	 *            the faculty to reset the schedule for
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser != null && currentUser == registrar) {
			faculty.facultySchedule.resetSchedule();
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * A registrar which has all of the authentication methods of a user
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password in the
		 * registrar.properties file.
		 * 
		 * @param firstName
		 *            first name of registrar
		 * @param lastName
		 *            last name of registrar
		 * @param id
		 *            id of registrar
		 * @param email
		 *            email of registrar
		 * @param hashPW
		 *            hashpw of registrar
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}