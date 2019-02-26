package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Represents the abstract linked list to use for the the courses
 * 
 * @author msleonar
 * @param <E> generic type element
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** Front of the chain of list nodes this list holds data in */
	private ListNode front;
	/** The size of the list, how many list nodes this list is made of */
	private int size;
	/** The maximum number of items this data should be able to hold */
	private int capacity;
	/** Back of the chain of list nodes */
	private ListNode back;

	/**
	 * Constructs the linked abstract list and sets the fields
	 * 
	 * @param capacity
	 *            the capacity of the course
	 * @throws IllegalArgumentException
	 *             when capacity is less than 0
	 */
	public LinkedAbstractList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be less then 0");
		}

		this.front = null;
		this.back = front;
		this.size = 0;
		this.capacity = capacity;
	}

	/**
	 * Add element to list at given index
	 * 
	 * @param index
	 *            index to add element at
	 * @param element
	 *            element to add to list
	 */
	public void add(int index, E element) {
		if (size == capacity) {
			throw new IllegalArgumentException("Cannot add to list while size is at capacity.");
		}

		if (element == null) {
			throw new NullPointerException();
		}

		if (inList(element)) {
			throw new IllegalArgumentException("Cannot add element, element already in list.");
		}

		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		if (front == null) {
			front = new ListNode(element);
			back = front;
		}

		if (index == 0) {
			front = new ListNode(element, front);
		} else if (front != null && index > 0) {
			ListNode temp = front;
			while (temp != null && index > 1) {
				temp = temp.next;
				index--;
			}
			if (temp != null) {
				temp.next = new ListNode(element, temp.next);
			}
		}

		ListNode temp = front;
		for (int i = 0; i < index - 1; i++) {
			temp = temp.next;
		}

	
		back = front;
		while (back.next != null) {
			back = back.next;
		}

		size++;
	}

	/**
	 * Checks to see if the element is in the list
	 * 
	 * @param element
	 *            the element to check in the list
	 * @return true or false if the element is in the list
	 */
	private boolean inList(E element) {
		for (int i = 0; i < size(); i++) {
			if (get(i).equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove element at given index and return it
	 * 
	 * @param index
	 *            index to remove element from
	 * @return element previously at index
	 * @throws IndexOutOfBoundsException
	 *             if the front is null or index is out of bounds
	 */
	public E remove(int index) {
		if (front == null)
			throw new IndexOutOfBoundsException();

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			E element = front.data;
			front = front.next;
			size--;
			return element;
		}

		ListNode temp = front;
		ListNode previous = null;
		for (int i = 0; i < index; i++) {
			previous = temp;
			temp = temp.next;
		}
		previous.next = temp.next;
		size--;
		return temp.data;

	}

	/**
	 * Get the element at the given index from the index
	 * 
	 * @param index
	 *            index to retrieve element from
	 * @return Element at given index
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds
	 * @throws NullPointerException
	 *             when the front is null
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (front == null) {
			throw new NullPointerException();
		}
		ListNode temp = front;

		if (index == 0) {
			return temp.data;
		}

		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}

		return temp.data;
	}

	/**
	 * Set the element at the given index the the given element, and return the old
	 * element.
	 * 
	 * @param index
	 *            index to change
	 * @param element
	 *            element to set to list
	 * @return element the element removed from list
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds
	 * @throws NullPointerException
	 *             when the element is null
	 */
	public E set(int index, E element) {
		if (front == null) {
			throw new IndexOutOfBoundsException();
		}

		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode temp = front;

		E currentElement;
		if (index == 0) {
			currentElement = temp.data;
			front = new ListNode(element, front.next);

			return currentElement;
		}

		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}

		currentElement = temp.data;
		temp.data = element;

		return currentElement;
	}

	/**
	 * Get the size of the linked list
	 * 
	 * @return size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the capacity to the parameter
	 * 
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Internal class of linked abstract list, which holds data and a reference to
	 * the next list node in the chain
	 * 
	 * @author msleonar
	 */
	private class ListNode {
		/** List node after this node */
		private ListNode next;
		/** Data this node has */
		private E data;

		/**
		 * Construct a list node with the given data.
		 * 
		 * @param data
		 *            data to store in this node
		 */
		public ListNode(E data) {
			this.data = data;
			this.next = null;
		}

		/**
		 * Construct a list node with the given data, which is followed by the next list
		 * node.
		 * 
		 * @param data
		 *            data to store in this list node
		 * @param next
		 *            next list node in this chain
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

}
