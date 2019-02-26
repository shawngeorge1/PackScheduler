package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Represents the linked list functionality
 * 
 * @author Shawn George
 * @param <E>
 *            the element
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** the front node of the linked list */
	ListNode front;
	/** the back node of the linked list */
	ListNode back;
	/** the size of the linked list */
	int size;

	/**
	 * Constructs the linked list nodes and size
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null, front, null);
		front.next = back;
		size = 0;
	}

	/**
	 * Represents the list iterator
	 * 
	 * @param index
	 *            the index of the iterator
	 * @return iterator the iterator
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		LinkedListIterator iterator = new LinkedListIterator(index);
		return iterator;
	}

	/**
	 * Adds the element parameter to the node at the index parameter
	 * 
	 * @param index
	 *            the index to add the element at
	 * @param element
	 *            the element to add at the index
	 * @throws IllegalArgumentException
	 *             when the element is already in the list
	 */
	@Override
	public void add(int index, E element) {
		if(this.contains(element))
		{
			throw new IllegalArgumentException();
		}

		listIterator(index).add(element);
	}

	/**
	 * Sets the element parameter to the node at the index parameter
	 * 
	 * @param index
	 *            the index to set the element at
	 * @param element
	 *            the element to set at the index
	 * @throws IllegalArgumentException
	 *             when the element is already in the list
	 */
	@Override
	public E set(int index, E element) {
		if(this.contains(element))
		{
			throw new IllegalArgumentException();
		}
		

		E returnedElement = get(index);
		super.set(index, element);
		return returnedElement;
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return size the size of the linked list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Inner Class of the linked list that represents a list node
	 * 
	 * @author Shawn George
	 */
	private class ListNode {
		/** the next node */
		ListNode next;
		/** the previous node */
		ListNode prev;
		/** the data inside the node */
		E data;

		/**
		 * sets the data, next node, and previous node to the correct things
		 * 
		 * @param data
		 *            the data E holds
		 */
		public ListNode(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}

		/**
		 * sets the data, next node, and previous node to the correct things
		 * 
		 * @param data
		 *            the data E holds
		 * @param prev
		 *            the previous node
		 * @param next
		 *            the next node
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

	}

	/**
	 * Inner class of the linked list class that represents the iterator
	 * 
	 * @author Shawn George
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** the previous list node */
		ListNode previous;
		/** the next list node */
		ListNode next;
		/** the previous index in the linked list */
		int previousIndex;
		/** the next index in the linked list */
		int nextIndex;
		/** the last retrieved node in the linked list */
		ListNode lastRetrieved;

		/**
		 * Constructs the iterator by setting all of the fields
		 * 
		 * @param index
		 *            the current index
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			ListNode nextNode = front.next;
			for (int i = 0; i < index; i++) {
				nextNode = nextNode.next;
			}

			this.nextIndex = index;
			this.previousIndex = index - 1;

			this.next = nextNode;
			this.previous = nextNode.prev;

			lastRetrieved = null;

		}

		/**
		 * Returns true if the next is not null and false if it is null meaning it is
		 * the end of the list
		 * 
		 * @return true or false if the next node is not null or is null
		 */
		@Override
		public boolean hasNext() {
			return (next.data != null);
		}

		/**
		 * Next method to change the next node
		 * 
		 * @throws NoSuchElementException
		 *             when the next node is null
		 * @return the next node's data
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			this.lastRetrieved = next;
			next = next.next;
			previousIndex++;
			nextIndex++;
			previous = previous.next;
			return lastRetrieved.data;
		}

		/**
		 * Returns true if the previous node is not null meaning it is not the end node
		 * 
		 * @return true or false if the previous node is not null or is null
		 */
		@Override
		public boolean hasPrevious() {
			return (previous.data != null);
		}

		/**
		 * Previous method to change the previous node
		 * 
		 * @throws NoSuchElementException
		 *             when the previous node is null
		 * @return the previous node's data
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			next = next.prev;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			return lastRetrieved.data;
		}

		/**
		 * Returns the next index
		 * 
		 * @return the next index
		 */
		@Override
		public int nextIndex() {
			return this.nextIndex;
		}

		/**
		 * Returns the previous index
		 * 
		 * @return the previous index
		 */
		@Override
		public int previousIndex() {
			if (previous == null) {
				return -1;
			}
			return this.previousIndex;
		}

		/**
		 * Removes the last retrieved index but setting it to null
		 * 
		 * @throws IllegalStateException
		 *             when the last retrieved is already null meaning it is removed
		 *             already
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException("last retrieved was null");
			}
			lastRetrieved.next.prev = lastRetrieved.prev;
			lastRetrieved.prev.next = lastRetrieved.next;
			size--;
		}

		/**
		 * sets the element to the last retrieved node's data
		 * 
		 * @throws IllegalStateException
		 *             when the last retrieved is null
		 * @throws NullPointerException
		 *             when the element parameter is null
		 */
		@Override
		public void set(E e) {
			if (this.lastRetrieved == null) {
				throw new IllegalStateException("last retrieved was null");
			}
			if (e == null) {
				throw new NullPointerException();
			}
			ListNode newNode = new ListNode(e, lastRetrieved.prev, lastRetrieved.next);
			lastRetrieved.prev.next = newNode;
			lastRetrieved.next.prev = newNode;
		}

		/**
		 * Adds the element parameter to the list
		 * 
		 * @throws NullPointerException
		 *             when the element parameter is null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}

			ListNode add = new ListNode(e, previous, next);
			previous.next = add;
			next.prev = add;

			size++;
			previousIndex++;
			nextIndex++;

			this.lastRetrieved = null;

		}

	}

}
