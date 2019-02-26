package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Custom exception class for our finite state machine for when there is an invalid transition 
 * between states
 * @author Abby Bowyer
 */
public class InvalidTransitionException extends Exception {
	
	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the exception when given a unique message
	 * @param message	unique message
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for the exception for the default message
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}
}
