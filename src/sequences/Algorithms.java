package sequences;

public class Algorithms {

    /**
     * Find the location in the sequence S where there is an element equal to x.
     *
     * @param S   The sequence.
     * @param x   The element to find.
     * @param <T> The element type.
     * @return The position of the element in S that is equal to x, or returns S.end()
     */
    public static <T>
    Iter<T> find(Sequence<T> S, T x) {
        for (Iter<T> i = S.begin(); ! i.equals(S.end()); i.advance()) {
            if (i.get().equals(x)) {
                return i;
            }
        }
        return S.end();
    }

    public static <T>
    boolean contains(Sequence<T> S, T x) { return ! find(S, x).equals(S.end()); }

        /**
         * Determine whether the two sequences s1 and s2 consist of equal elements.
         *
         * @param s1 The first sequence.
         * @param s2 The second sequence.
         * @param <T> The type of the elements.
         * @return True/false regarding whether the two sequences have equal elements.
         */
    public static <T>
    boolean equals(Sequence<T> s1, Sequence<T> s2) {
        Iter<T> i = s1.begin();
        Iter<T> j = s2.begin();
        while ((! i.equals(s1.end())) && (! j.equals(s2.end()))) {
            if (! i.get().equals(j.get()))
                return false;
            i.advance(); j.advance();
        }
        return i.equals(s1.end()) && j.equals(s2.end());
    }

    /**
     * Returns the largest element in the sequence.
     *
     * @param s     The sequence.
     * @param zero  The appropriate zero value for elements of type T.
     * @param <T>   The element type.
     * @return      The largest element in the sequence (or zero).
     */
    public static <T extends Comparable<T>>
    T max(Sequence<T> s, T zero) {
        T m = zero;
        for (Iter<T> i = s.begin(); ! i.equals(s.end()); i.advance()) {
            T c = i.get();
            if (m.compareTo(c) < 0)
                m = c;
        }
        return m;
    }

    /**
     * Swap the elements at positions i and j.
     *
     * @param i   The position of one element.
     * @param j   The position of another element.
     * @param <E> The element type.
     */
    public static <E> void iter_swap(Iter<E> i, Iter<E> j) {
        E tmp = i.get();
        i.set(j.get());
        j.set(tmp);
    }

    /**
     * Return the position of the last element in the sequence.
     * (This is immediately before the `end` position.)
     * The time complexity of this algorithm is O(n) because
     * it starts at the beginning and advances the iterator.
     * (`Iter` does not have a `retreat()` method.)
     *
     * @param begin The position of the first element in the sequence.
     * @param end   The position one-past the last element.
     * @param <E>   The element type.
     * @return      The position of the last element.
     */
    public static <E> Iter<E> last(Iter<E> begin, Iter<E>end) {
        Iter<E> i = begin.clone();
        Iter<E> j = begin.clone();
        j.advance();
        while (! j.equals(end)) {
            i.advance();
            j.advance();
        }
        return i;
    }

    public static <E> void advance_iter(Iter<E> iter, int n) {
        for (int i = 0; i != n; ++i)
            iter.advance();
    }
}
