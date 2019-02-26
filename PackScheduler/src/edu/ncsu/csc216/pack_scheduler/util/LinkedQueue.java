package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue object that is a list of elements that add to the back and are removed from the front.
 * @author Abby Bowyer
 * @author Neil Towne
 * @param <E>	the element
 */
public class LinkedQueue<E> implements Queue<E> {
	
	/** Initialization of ArrayList of elements in the queue */
	LinkedAbstractList<E> list;
	
	/** The Capacity of the list */
	private int capacity;
	
	/**
	 * Constructor for LinkedQueue, initializes ArrayList of objects in queue.
	 * @param capacity - capacity of queue
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	
	/**
	 * Adds the element to the back of the queue. Throws an exception if there is no
	 * room (reached capacity)
	 * 
	 * @param element the element to add
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
	 */
	public E dequeue() {
		if(list.size() <= 0) {
			throw new NoSuchElementException();
		}
		E temp = list.get(0);
		list.remove(list.get(0));
		return temp;
	}
	
	/**
	 * Returns true if the queue is empty
	 * 
	 * @return true or false for if the queue is empty or not
	 */
	public boolean isEmpty() {
		if (list.size() == 0) {
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
	 * @param capacity	the capacity of the course
	 * @throws IllegalArgumentException when the capacity is less than 0 or less than the size of the list
	 */
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
}
