package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T> {

	private static class Node<T> {
		T obj;
		Node<T> next;
		Node<T> prev;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private int size;
	private Node<T> head; // reference to the first element
	private Node<T> tail; // reference to the last element

	@Override
	public void add(T element) {
		Node<T> newNode = new Node<>(element);
		if (head == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;

	}
	private Node<T> getNode(int index) {
		Node<T> res = null;
		if(isValidIndex(index)) {
			res = index <= size / 2 ? getNodefromLeft(index) : getNodeFromRight(index);
		}
		return res;
	}

	private Node<T> getNodeFromRight(int index) {
		Node<T> current = tail;
		int ind = size - 1;
		while(ind != index) {
			ind--;
			current = current.prev;
		}
		return current;
	}
	private Node<T> getNodefromLeft(int index) {
		Node<T> current = head;
		int ind = 0;
		while(ind != index) {
			ind++;
			current = current.next;
		}
		return current;
	}
	private boolean isValidIndex(int index) {
		
		return index >=0 && index < size;
	}
	@Override
	public boolean add(int index, T element) {
		boolean res = false;
		if (index == size) {
			add(element);
			res = true;
		} else if (isValidIndex(index)) {
			res = true;
			Node<T> newNode = new Node<>(element);
			if (index == 0) {
				addHead(newNode);
			} else {
				addMiddle(newNode, index);
			}
			size++;
		}
		return res;
	}

	private void addMiddle(Node<T> newNode, int index) {
		Node<T> nodeAfter = getNode(index);
		newNode.next = nodeAfter;
		newNode.prev = nodeAfter.prev;
		//nodeAfter.prev => reference to the old previous element
		nodeAfter.prev.next = newNode;
		nodeAfter.prev = newNode;
		
	}
	private void addHead(Node<T> newNode) {
		newNode.next = head;
		head.prev = newNode;
		head = newNode;
		
	}
	@Override
	public int size() {

		return size;
	}

	@Override
	public T get(int index) {
		T res = null;
		Node<T> resNode = getNode(index);
		if (resNode != null) {
			res = resNode.obj;
		}
		return res; 
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		T result = null;
		if (isValidIndex(index)) {
			if(index==0) {
				result = removeHead();
			} else if(index==(size-1)) {
				result = removeTail();
			} else {
				result = removeMiddle(index);
			}
			size--;
		}
		return result; 
		// Done
	}
	private T removeHead() {
		T removedElement = head.obj;
		head = head.next;
		if (head != null) {
			head.prev = null;
		}
		return removedElement;
	}
	private T removeTail() {
		T removedElement = tail.obj;
		tail = tail.prev;
		if (tail != null) {
			tail.next = null;
		}
		return removedElement;
	}
	private T removeMiddle(int index) {
		Node<T> nodeToDelete = getNode(index);
		Node<T> prevNode = nodeToDelete.prev;
		Node<T> nextNode = nodeToDelete.next;
		prevNode.next = nodeToDelete.next;
		nextNode.prev = nodeToDelete.prev;
		return nodeToDelete.obj;
	}
	@Override
	public int indexOf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		int result = -1;
		Node<T> currentNode = head;
		for (int ind =0; ind <size; ind++) {
			if(predicate.test(currentNode.obj)) {
				result = ind;
				break;
			}
			currentNode = currentNode.next;	
		}
		return result;
		// Done
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		int result =-1;
		Node<T> currentNode = tail;
		for (int ind =size-1; ind >=0; ind--) {
			if(predicate.test(currentNode.obj)) {
				result = ind;
				break;
			} 
			currentNode = currentNode.prev;	
		}		
		return  result;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		int sizeBefore = size;
		Node<T> currentNode = tail;
		
		for (int ind= size-1; ind>=0; ind--) {
			if(predicate.test(currentNode.obj)) {
				remove(ind);
			} 
			currentNode=currentNode.prev;
		}
		return sizeBefore > size;
	}
 
	@Override
	public void sort(Comparator<T> comp) {
		T[] array = listToArray();
		Arrays.sort(array, comp);
		fillListFromArray(array);
		

	}
	private T[] listToArray() {
		//TODO
		//creates array of T objects
		//passes over whole list and fills the array
		//sorting filled array
		T[] array = (T[]) new Object[size];
		Node<T> currentNode = head;
		for (int ind = 0; ind < array.length; ind++) {
			array[ind] = currentNode.obj;
			currentNode = currentNode.next;
		}
		return array;
		// Done
	}
	private void fillListFromArray(T[] array) {
		//TODO
		//passes over whole list and fills elements from index=0 to index=size - 1
		Node<T> currentNode = head;
		for (int ind = 0; ind < array.length; ind++) {
			currentNode.obj = array[ind];
			currentNode = currentNode.next;
		}
		// Done
	}

}
