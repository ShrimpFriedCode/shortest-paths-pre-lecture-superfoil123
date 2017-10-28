package sequences;

public class IntIter implements Iter<Integer> {
    private int i;

    public IntIter(int n) { i = n; }

    @Override
    public Integer get() {
        return i;
    }

    @Override
    public void set(Integer e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void advance() {
        ++i;
    }

    @Override
    public boolean equals(Iter<Integer> other) {
        return i == ((IntIter)other).i;
    }

    @Override
    public Iter<Integer> clone() {
        return new IntIter(i);
    }
}
