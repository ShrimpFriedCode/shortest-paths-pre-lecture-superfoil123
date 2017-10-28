package sequences;

import java.util.Iterator;

public class Array<T> implements Sequence<T> {
	private T[] array;

    public Array(int n, T init) {
        array = (T[])new Object[n];
        for (int i = 0; i != n; ++i)
            array[i] = init;
    }
	public Array(T[] a) { array = a; }
	public ArrayIter<T> begin() { return new ArrayIter<T>(array,0); }
	public ArrayIter<T> end() { return new ArrayIter<T>(array, array.length); }

	public T get(int i) { return array[i]; }
	public T set(int i, T x) { T y = array[i]; array[i] = x; return y; }
    public int size() { return array.length; }
    public Iterator<T> iterator() {
        return new RangeIter<T>(begin(), end());
    }

    class ArrayIter<T> implements Iter<T> {
		private T[] array;
		private int pos;
		public ArrayIter(T[] a, int p) { array = a; pos = p; }
		public T get() { return array[pos]; }
		public void set(T x) { array[pos] = x; }
		public void advance() { ++pos; }
		public ArrayIter<T> clone() { return new ArrayIter<T>(array,pos); }
		public boolean equals(Iter<T> other) {
			ArrayIter<T> i = (ArrayIter<T>) other;
			return pos == i.pos;
		}
	}

}

