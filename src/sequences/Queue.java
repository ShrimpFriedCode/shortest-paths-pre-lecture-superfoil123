package sequences;

public interface Queue<T> {
    void push(T elt);
    T pop();
    boolean empty();
}
