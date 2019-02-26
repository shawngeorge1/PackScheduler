package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayQueue object that is a list of elements that add to the back and are removed from the front.
 * @author Abby Bowyer
 * @author Neil Towne
 * @param <E> generic type element
 */
public class ArrayQueue<E> implements Queue<E> {

	/** Initialization of ArrayList of elements in the queue */
	public ArrayList<E> list;
	
	/** The Capacity of the list */
	private int capacity;
	
	/**
	 * Constructor for ArrayQueue, initializes ArrayList of elements in arrayQueue
	 * @param capacity capacity of ArrayQueue
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}
	
	/**
	 * Adds the element to the back of the queue. Throws an exception if there is no
	 * room (reached capacity)
	 * 
	 * @param element the element to add
	 * @throws IllegalArgumentException if list has reached capacity.
	 */
	public void enqueue(E element) {
		if(list.size() >= this.capacity) {
			throw new IllegalArgumentException();
		}
		list.add(element);
	}
	
	/**
	 * Removes and returns the element at the front of the queue
	 * 
	 * @return element to remove
	 * @throws NoSuchElementException if list size is 0 or less.
	 */
	public E dequeue() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.remove(0);

	}
	
	/**
	 * Returns true if the queue is empty
	 * 
	 * @return true or false for if the queue is empty or not
	 */
	public boolean isEmpty() {
		if(list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return size the number of elements in the queue
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Sets the Queue's capacity. Throws an exception if the capacity parameter is
	 * less than the number of elements in the Queue.
	 * 
	 * @param capacity	the capacity of the for the course
	 * @throws IllegalArgumentException if capacity is less than 0 or if capacity is less than number of elements in queue.
	 */
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
}
