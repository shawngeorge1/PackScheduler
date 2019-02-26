package edu.ncsu.csc216.pack_scheduler.course;

/**
 * An exception to be checked if two objects conflict
 * @author msleonar
 */
public class ConflictException extends Exception {

    /** ID used for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Create a conflict exception with a custom message
     * @param messsage Message for the exception
     */
    public ConflictException(String messsage) {
        super(messsage);
    }
    
    /**
     * Create a conflict exception with the default message
     * "Schedule conflict."
     */
    public ConflictException() {
        super("Schedule conflict.");
    }
}
