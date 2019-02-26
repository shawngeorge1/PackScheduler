package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A replacement for ArrayLists which implements abstract list by holding its
 * elements in an array.
 *
 * @author Matt
 * @param <E>
 *            class of array list objects
 */
@SuppressWarnings("unchecked")
public class ArrayList<E> extends AbstractList<E> {

	/** initial size of all array lists */
	private static final int INIT_SIZE = 10;
	/** List to hold elements in */
	private E[] list;
	/** Current number of items in the array */
	private int size;

	/**
	 * Construct an array list by setting the size to 0, and initializing the
	 * ArrayLists array
	 */
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}

	/**
	 * Add the element at the given index
	 * 
	 * @param index
	 *            index to add element to
	 * @param element
	 *            Element to add to array
	 */
	public void add(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		if (size == list.length) {
			growArray();
		}

		E prevElement = element;
		E swapElement;
		size++;
		for (int i = index; i < size; i++) {
			swapElement = list[i];
			list[i] = prevElement;
			prevElement = swapElement;
		}
	}

	/**
	 * Set the element at the given index to the given element, returning and
	 * replacing the current element at that index
	 * 
	 * @param index
	 *            to set to the element
	 * @param element
	 *            to set in the list
	 * 
	 * @return element that was previously at that index
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		if (element == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		E returnElement = list[index];
		list[index] = element;
		return returnElement;
	}

	/**
	 * Double the size of the array if we are adding an element and need to
	 */
	private void growArray() {
		E[] newArray = (E[]) new Object[list.length * 2];
		for (int i = 0; i < size; i++) {
			newArray[i] = list[i];
		}

		list = newArray;
	}

	/**
	 * Remove the element at the given index
	 * 
	 * @param index
	 *            to remove from
	 * @return element removed
	 */
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E element = list[index];
		if(index == this.size - 1) {
			list[index] = null;
			size--;
		}
		else {
			for (int i = index; i < this.size - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size - 1] = null;
			size--;
		}
		return element;

	}


	/**
	 * Return the element at the given index
	 * 
	 * @param index
	 *            the index to get form
	 * @return the element at the given index
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		return list[index];
	}

	/**
	 * The size of the current list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

}
