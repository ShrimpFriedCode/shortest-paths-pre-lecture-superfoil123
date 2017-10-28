package sequences;

import java.util.Iterator;

public class RangeIter<T> implements Iterator<T> {
    Iter<T> curr, end;

    public RangeIter(Iter<T> c, Iter<T> e) { curr = c; end = e; }

    public boolean hasNext() { return !curr.equals(end); }

    public T next() {
        T x = curr.get();
        curr.advance();
        return x;
    }
}
