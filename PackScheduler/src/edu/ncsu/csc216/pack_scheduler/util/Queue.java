package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface that represents the Queue of elements and can add or remove
 * elements for the queue.
 * 
 * @author Abby Bowyer
 * @param <E> the element
 */
public interface Queue<E> {

	/**
	 * Adds the element to the back of the queue. Throws an exception if there is no
	 * room (reached capacity)
	 * 
	 * @param element the element to add
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the queue
	 * 
	 * @return element to remove
	 */
	E dequeue();

	/**
	 * Returns true if the queue is empty
	 * 
	 * @return true or false for if the queue is empty or not
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return size the number of elements in the queue
	 */
	int size();

	/**
	 * Sets the Queue's capacity. Throws an exception if the capacity parameter is
	 * less than the number of elements in the Queue.
	 * 
	 * @param capacity	the capacity of students for the course
	 */
	void setCapacity(int capacity);
}
