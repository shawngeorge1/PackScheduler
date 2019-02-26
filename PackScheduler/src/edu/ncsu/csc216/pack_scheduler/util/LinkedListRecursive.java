package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Represents the LinkedListRecursive
 * 
 * @author Abby Bowyer
 * @author Shawn George
 * @param <E>
 *            the element used
 */
public class LinkedListRecursive<E> {

	/** Represents the front of the list */
	private ListNode front;

	/** The size of the list */
	private int size;

	/**
	 * Constructs the list by setting fields to null and 0
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}

	/**
	 * Checks if the list is empty
	 * 
	 * @return true or false if the list is empty
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return size the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds the given element to the end of the list and throws an exception if it
	 * already exists
	 * 
	 * @param element
	 *            the element to add
	 * @return true or false if the element was added
	 * @throws IllegalArgumentException
	 *             if the element is already in the list
	 */
	public boolean add(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (isEmpty()) {
			size++;
			front = new ListNode(element, null);
			return true;
		} else {
			size++;
			return front.add(element);
		}

	}

	/**
	 * Adds the given element to the list at the given index
	 * 
	 * @param idx
	 *            the index to add the element at
	 * @param element
	 *            the element to add to the list
	 */
	public void add(int idx, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (idx == 0) {
			front = new ListNode(element, front);
			size++;
		} else {

			front.add(idx - 1, element);
			size++;
		}
	}

	/**
	 * Gets and returns the element at the index given
	 * 
	 * @param idx
	 *            the index to get the element at
	 * @return the element to get
	 * @throws IndexOutOfBoundsException
	 *             when the index is out of bounds
	 */
	public E get(int idx) {
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (idx == 0) {
			return front.data;
		}
		return front.get(idx);
	}

	/**
	 * Removes the element at the given index from the front and calls the private
	 * method to remove from the middle and front of the list
	 * 
	 * @param idx
	 *            the index to remove at
	 * @return the element removed
	 * @throws IndexOutOfBoundsException
	 *             when the index is out of bounds
	 */
	public E remove(int idx) {
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (front == null) {
			throw new IllegalArgumentException("List is empty, nothing to remove");
		}
		if (idx == 0) {
			E temp = front.data;
			front = front.next;
			size--;
			return temp;
		} else {
			size--;
			return front.remove(idx - 1);
		}
	}

	/**
	 * Removes the given element from the list from the front and calls the private
	 * method if removing from the middle or end
	 * 
	 * @param element
	 *            the element to remove
	 * @return true or false if the element is removed
	 * @throws NullPointerException
	 *             if the element is null
	 * @throws IllegalArgumentException
	 *             if the list is empty and there is nothing to remove
	 */
	public boolean remove(E element) {
		if (!this.contains(element))
			return false;
		if (front == null) {
			return false;
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		} else {
			size--;
			return front.remove(element);
		}
	}

	/**
	 * Sets the element at the given index by calling the private method
	 * 
	 * @param idx
	 *            the index to set the element at
	 * @param element
	 *            the element to set
	 * @return the element being replaced (previously in the list node)
	 */
	public E set(int idx, E element) {
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}

		return front.set(idx - 1, element);
	}

	/**
	 * Calls the private contains method and returns the boolean from the inner
	 * class method
	 * 
	 * @param element
	 *            the element to check in the list
	 * @return ture or false for if the list contains the element
	 */
	public boolean contains(E element) {
		if (this.isEmpty()) {
			return false;
		}
		return front.contains(element);
	}

	/**
	 * Inner Class of the linked list that represents a list node
	 * 
	 * @author Shawn George
	 */
	private class ListNode {

		/** the data inside the node */
		E data;
		/** the next node */
		ListNode next;

		/**
		 * sets the data, next node, and previous node to the correct things
		 * 
		 * @param data
		 *            the data E holds
		 * @param prev
		 *            the previous node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Element to be added
		 * 
		 * @param element
		 *            to be added
		 * @return if element is added
		 */
		private boolean add(E element) {
			if (this.next == null) {
				this.next = new ListNode(element, null);
				return true;
			}
			return this.next.add(element);
		}

		/**
		 * Recursive add method
		 * 
		 * @param idx
		 *            to add at
		 * @param element
		 *            element to add
		 */
		private void add(int idx, E element) {
			if (idx == 0) {
				this.next = new ListNode(element, this.next);
			} else {
				idx--;
				this.next.add(idx, element);
			}
		}

		/**
		 * Recursive get method
		 * 
		 * @param idx
		 *            to get at
		 * @return element at index
		 */
		private E get(int idx) {
			if (idx == 1) {
				return this.next.data;
			}
			idx--;
			return this.next.get(idx);
		}

		/**
		 * Recursive remove method
		 * 
		 * @param idx
		 *            index to remove at
		 * @return removed element
		 */
		private E remove(int idx) {
			if (next != null) {
				if (idx == 0) {
					E element = next.data;
					next = next.next;
					return element;
				} else {
					idx--;
					next.remove(idx);
				}
			}
			return null;
		}

		/**
		 * Recursive remove method
		 * 
		 * @param element
		 *            to remove
		 * @return element removed
		 */
		private boolean remove(E element) {
			if (next != null) {
				if (this.next.data.equals(element)) {
					this.next = this.next.next;
					return true;
				}
				return this.next.remove(element);
			}
			return false;
		}

		/**
		 * Recursive set method
		 * 
		 * @param idx
		 *            index to set at
		 * @param element
		 *            to set
		 * @return element set
		 */
		private E set(int idx, E element) {
			if (idx == 0) {
				E temp = this.next.data;
				next.data = element;
				return temp;
			}
			idx--;
			return this.next.set(idx, element);
		}

		/**
		 * Recursive contains method
		 * 
		 * @param element
		 *            element to see if contained
		 * @return if contained
		 */
		private boolean contains(E element) {
			if (this.data.equals(element)) {
				return true;
			}
			if (this.next == null) {
				return false;
			}
			return this.next.contains(element);
		}
	}
}
