
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * The class that represents the stack functionality
 * @author Shawn
 * @param <E> Generic type element
 */
public class ArrayStack<E> implements Stack<E> {
	/** the array to use as the stack */
	private ArrayList<E> array;
	/** the size of the stack */
//	private int size;
	/** the capacity for the course/stack */
	private int capacity;

	/**
	 * Constructs the stack/array list
	 * @param capacity	capacity of the list/course
	 * @throws IllegalArgumentException	when capcity is less than 0
	 */
	public ArrayStack(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		array = new ArrayList<E>();
		this.capacity = capacity;
		setCapacity(capacity);
	}

	/**
	 * pushes the element to the top of the stack
	 * @param element	the element to push on top
	 * @throws IllegalArgumentException	when the element is null or the size of the stack is already at capacity
	 */
	@Override
	public void push(E element) {
		if (element == null) {
			throw new IllegalArgumentException();
		}
		if (capacity == this.size()) {
			throw new IllegalArgumentException("Cannot add to stack");
		}
		array.add(0, element);

	}

	/**
	 * pops the top element off of the stack
	 * @throws EmptyStackException	when the stack is empty and has nothing to pop
	 * @return element	the element that is popped off the stack
	 */
	@Override
	public E pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		E element = array.remove(0);

		return element;
	}

	/**
	 * Returns true if the list is empty and false if it is not empty
	 * @return true if the list is empty and false if it is not empty
	 */
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * Returns the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return array.size();
	}

	/**
	 * Sets the capacity of the list to the parameter given
	 * @throws IllegalArgumentException when the capacity is less than zero or less than the size of the array
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < array.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}