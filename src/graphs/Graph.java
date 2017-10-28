package graphs;
import sequences.Sequence;

public interface Graph<V> {
    int numVertices();
    void addEdge(V source, V target);
    Sequence<V> adjacent(V source);
    Sequence<V> vertices();
}
