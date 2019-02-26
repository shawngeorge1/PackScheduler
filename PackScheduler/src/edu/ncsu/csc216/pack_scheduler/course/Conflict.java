package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Behaviors that are used when a class might conflict with another in a list
 * 
 * @author msleonar
 */
public interface Conflict {

    /**
     * Check if the two activities conflict
     * 
     * @param possibleConflictingActivity activity that might conflict with the
     *                                    current one
     * @throws ConflictException if the two activities conflict
     */
    void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
