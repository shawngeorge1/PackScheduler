package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Represents the Stack of the list of students in a course
 * @author Shawn
 * @param <E> the element
 */
public class LinkedStack<E> implements Stack<E> {

	/** the linked abstract list to represent the list of elements */
	private LinkedAbstractList<E> list;
	
	/** the capacity of the course */
	private int capacity;
	
	/** the size of the list for the course */
	int size;

	/**
	 * Constructs the stack using the linked abstract list and sets the capacity
	 * @param capacity	the capacity of students for the course
	 */
	public LinkedStack(int capacity) {
		this.list = new LinkedAbstractList<E>(capacity);
		this.capacity = capacity;
		setCapacity(capacity);
	}

	@Override
	public void push(E element) {
	    if(size() >= capacity) {
	        throw new IllegalArgumentException();
	    }
		list.add(0, element);
	}

	@Override
	public E pop() {
		if (list.isEmpty())
			throw new EmptyStackException();

		E element = list.remove(0);
		return element;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}
