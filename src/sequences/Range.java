package sequences;

import java.util.Iterator;

public class Range<T> implements Sequence<T> {
    private Iter<T> begin, end;

    public Range(Iter<T> b, Iter<T> e) { begin = b; end = e; }
    public Iter<T> begin() { return begin; }
    public Iter<T> end() { return end; }
    public Iterator<T> iterator() {
        return new RangeIter<T>(begin, end);
    }
}
