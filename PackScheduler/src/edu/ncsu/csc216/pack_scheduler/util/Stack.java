package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Stack has a generic type and 5 methods which are described below:
 * 
 * @author shawngeorge
 *
 * @param <E> the element
 */
public interface Stack<E> {
	/**
	 * Adds the element to the top of the stack If there is no room (capacity has
	 * been reached), an IllegalArgumentException is thrown.
	 * 
	 * @param element	the element to push
	 */
	void push(E element);

	/**
	 * Removes and returns the element at the top of the stack If the stack is
	 * empty, an EmptyStackException() is thrown.
	 * @return the element being popped off the stack
	 */
	E pop();

	/**
	 * Returns true if the stack is empty
	 * 
	 * @return if stack is empty
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the stack
	 * 
	 * @return number of elements in stack
	 */
	int size();

	/**
	 * Sets the stack’s capacity If the actual parameter is negative or if it is
	 * less than the number of elements in the stack, an IllegalArgumentException is
	 * thrown.
	 * 
	 * @param capacity	the capacity of the stack
	 */
	void setCapacity(int capacity);

}
