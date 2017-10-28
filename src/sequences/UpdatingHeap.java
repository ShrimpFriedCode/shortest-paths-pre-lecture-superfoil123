package sequences;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class UpdatingHeap<E> {
    Object[] data;
    int num_elts;
    BiPredicate<E,E> less;
    Map<E,Integer> position;

    /**
     * Create a new instance of UpdatingHeap.
     *
     * @param lessThan  A predicate that tests whether one element is less than another.
     */
    public UpdatingHeap(BiPredicate<E,E> lessThan) {
        data = new Object[0]; num_elts = 0; less = lessThan; position = new HashMap<E,Integer>();
    }

    /**
     * @return The highest priority element in the heap.
     */
    public E maximum() {
        return (E)data[0];
    }

    /**
     * Signal the heap that the key for element e has increased.
     *
     * @param e   The element whose key has changed.
     */
    public void increase_key(E e) {
        int i = position.get(e);
        while (parent(i) >= 0
                && less.test((E)data[parent(i)], (E)data[i])) {
            swap(data, i, parent(i));
            position.put((E)data[i], i);
            position.put((E)data[parent(i)], parent(i));
            i = parent(i);
        }
    }

    /**
     * Insert an element into the heap.
     *
     * @param k    The element to insert.
     */
    public void push(E k) {
        if (num_elts + 1 >= data.length) {
            Object[] d = new Object[(data.length + 1) * 2];
            for (int i = 0; i != num_elts; ++i) {
                d[i] = data[i];
            }
            data = d;
        }
        data[num_elts] = k;
        position.put(k, num_elts);
        num_elts += 1;
        increase_key((E)data[num_elts - 1]);
    }

    /**
     * Remove the element with the largest key.
     *
     * @return The element with the largest key (highest priority).
     */
    public E pop() {
        E max = (E)data[0];
        data[0] = data[num_elts-1];
        position.put((E)data[0], 0);
        num_elts -= 1;
        max_heapify(0);
        // to do: shrink the data array -Jeremy
        return max;
    }

    /**
     * @return Does the heap contain zero elements?
     */
    public boolean empty() { return num_elts == 0; }

    /**
     * @return A string representation of the heap (as a tree).
     */
    public String toString() {
        if (num_elts > 0)
           return toString(0);
        else
            return "";
    }

    /*************************************************
     *  Helper Functions
     */

    static int left(int i) { return 2*i + 1; }

    static int right(int i) { return 2*(i + 1); }

    static int parent(int i) {
        return (int) Math.floor((double)(i - 1) / 2.0);
    }

    static <E> void swap(E[] A, int i, int j) {
        E tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    void max_heapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest;
        // determine which of i, l, or r is the largest
        if (l < num_elts && less.test((E)data[i], (E)data[l])) {
            largest = l;
        } else {
            largest = i;
        }
        if (r < num_elts
                && less.test((E)data[largest], (E)data[r])) {
            largest = r;
        }
        // if i is not the largest, swap it with the largest,
        // then continuing max_heapify
        if (largest != i) {
            swap(data, i, largest);
            position.put((E)data[i], i);
            position.put((E)data[largest], largest);
            max_heapify(largest);
        }
    }

    void build_max_heap() {
        int last_parent = num_elts / 2 - 1;
        for (int i = last_parent; i != -1; --i) {
            max_heapify(i);
        }
    }

    void heap_sort() {
        build_max_heap();
        for (int i  = num_elts - 1; i != 0; --i) {
            swap(data, 0, i);
            position.put((E)data[0], 0);
            position.put((E)data[i], i);
            num_elts -= 1;
            max_heapify(0);
        }
    }

    boolean is_heap(int i) {
        int l = left(i);
        int r = right(i);
        boolean ret = true;
        if (l < data.length) {
            ret = ret && ! less.test((E)data[i], (E)data[l]) && is_heap(l);
        }
        if (r < data.length) {
            ret = ret && ! less.test((E)data[i], (E)data[r]) && is_heap(r);
        }
        return ret;
    }

    String toString(int i) {
        String left, right;
        if (left(i) < num_elts)
            left = toString(left(i));
        else
            left = "";
        if (right(i) < num_elts)
            right = toString(right(i));
        else
            right = "";
        return "(" + data[i].toString() + " " + left + " " + right + ")";
    }

}
