package sequences;

import java.util.Iterator;

public interface Sequence<T> extends Iterable<T> {
	// The begin() method returns an iterator whose position is the first element
	// of the sequence, if there is one. Otherwise, it's position is the same as end().
    Iter<T> begin();
    
    // The end() method returns an iterator whose position is one past the last element.
    Iter<T> end();
}