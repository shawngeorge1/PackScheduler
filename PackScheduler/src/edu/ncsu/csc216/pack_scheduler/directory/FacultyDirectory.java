package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Represents the directory of faculty members and can add remove and load and save directories of
 * faculty members
 * @author Neil Towne
 */
public class FacultyDirectory {

    /** List of faculty in the directory */
    private LinkedList<Faculty> facultyDirectory;
    /** Hashing algorithm */
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Creates an empty faculty directory.
     */
    public FacultyDirectory() {
        newFacultyDirectory();  
    }

    /**
     * Creates an empty faculty directory. All faculty in the previous list are
     * list unless saved by the user.
     */
    public void newFacultyDirectory() {
        facultyDirectory = new LinkedList<Faculty>();
        
    }

    /**
     * Constructs the faculty directory by reading in faculty information from the
     * given file. Throws an IllegalArgumentException if the file cannot be found.
     * 
     * @param fileName file containing list of faculty
     */
    public void loadFacultyFromFile(String fileName) {

        try {
            facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file " + fileName);
        }
    }

    /**
     * Adds a Faculty to the directory. Returns true if the faculty is added and
     * false if the faculty is unable to be added because their id matches another
     * faculty's id.
     * 
     * This method also hashes the faculty's password for internal storage.
     * 
     * @param firstName      faculty's first name
     * @param lastName       faculty's last name
     * @param id             faculty's id
     * @param email          faculty's email
     * @param password       faculty's password
     * @param repeatPassword faculty's repeated password
     * @param maxCourses     faculty's max courses.
     * @return true if added
     */
    public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
            String repeatPassword, int maxCourses) {
        String hashPW = "";
        String repeatHashPW = "";
        if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
            throw new IllegalArgumentException("Invalid password");
        }
        try {
            MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
            digest1.update(password.getBytes());
            hashPW = new String(digest1.digest());

            MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
            digest2.update(repeatPassword.getBytes());
            repeatHashPW = new String(digest2.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot hash password");
        }

        if (!hashPW.equals(repeatHashPW)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // If an IllegalArgumentException is thrown, it's passed up from Faculty
        // to the GUI
        Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

        for(Faculty fac: facultyDirectory)
        {
        	if(fac.getId().equals(id))
        	{
        		return false;
        	}
        }
        facultyDirectory.add(faculty);   
        return  true;
        
    }

   

    /**
     * Removes the faculty with the given id from the list of faculty with the
     * given id. Returns true if the faculty is removed and false if the faculty is
     * not in the list.
     * 
     * @param facultyId faculty's id
     * @return true if removed
     */
    public boolean removeFaculty(String facultyId) {
        for (int i = 0; i < facultyDirectory.size(); i++) {
            User s = facultyDirectory.get(i);
            if (s.getId().equals(facultyId)) {
                facultyDirectory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all faculty in the directory with a column for first name, last
     * name, and id.
     * 
     * @return String array containing faculty first name, last name, and id.
     */
    public String[][] getFacultyDirectory() {
        String[][] directory = new String[facultyDirectory.size()][3];
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty s = facultyDirectory.get(i);
            directory[i][0] = s.getFirstName();
            directory[i][1] = s.getLastName();
            directory[i][2] = s.getId();
        }
        return directory;
    }

    /**
     * Saves all faculty in the directory to a file.
     * 
     * @param fileName name of file to save faculty to.
     */
    public void saveFacultyDirectory(String fileName) {
        try {
            FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to write to file " + fileName);
        }
    }

    /**
     * Get a faculty from the directory by their id
     * 
     * @param id id to search for faculty by
     * @return the faculty
     */
    public Faculty getFacultyById(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("Invalid id");
        }
        Faculty faculty = null;
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (id.equals(f.getId())) {
                faculty = facultyDirectory.get(i);
                return faculty;
            }
        }
        return null;
    }
}

