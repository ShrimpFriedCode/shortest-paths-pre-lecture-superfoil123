package sequences;

import java.util.Iterator;

class Node<T> {
	Node(T d, Node<T> n) { data = d; next = n; }
	T data;
	Node<T> next;
}

public class SLinkedList<T> implements Sequence<T>, Queue<T> {

	private Node<T> front;
	private int numElts;
	
	// Create a linked list with no elements.
	public SLinkedList() { front = null; numElts = 0; }
	
	public ListIter begin() { return new ListIter(front); }
	
	public ListIter end() { return new ListIter(null); }
	
	// Add element x to the front of the list.
	public void insert_front(T x) {
		Node<T> n = new Node<T>(x, front);
		front = n;
		++numElts;
	}
	public void push(T x) { insert_front(x);}

	public T remove_front() {
	    if (front == null) {
	        throw new IllegalArgumentException();
        } else {
            T x = front.data;
            front = front.next;
            return x;
        }
    }
    public T pop() { return remove_front(); }

	public int size() {
	    return numElts;
    }
    public boolean empty() { return numElts == 0; }
	
	// Insert element x so that it appears one position after the position pos.
	// precondition: pos is the position of an element in the sequence.
	public void insert_after(ListIter pos, T x) { // student implement
		Node<T> n = new Node<T>(x, pos.node.next);
		pos.node.next = n;
		++numElts;
	}

	public class ListIter implements Iter<T> {

		Node<T> node;

		ListIter(Node<T> p) { node = p; }

		public T get() { return node.data; }

		public void set(T x) { node.data = x; }

		public void advance() { node = node.next; }

		public boolean equals(Iter<T> other) {
			return node == ((ListIter)other).node;
		}

		public ListIter clone() {
			return new ListIter(node);
		}

	}

	public Iterator<T> iterator() { return new RangeIter<T>(begin(), end()); }
}
