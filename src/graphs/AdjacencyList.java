package graphs;
import sequences.*;

/**
 * TODO
 *
 * Complete the AdjacencyList class, implementing all of the
 * methods needed for the Graph interface and
 * finish the constructor.
 *
 */
public class AdjacencyList implements Graph<Integer> {
    Array<SLinkedList<Integer>> adjacent;

    public AdjacencyList(int num_vertices) {
        adjacent = new Array<SLinkedList<Integer>>(num_vertices, null);
        for (int i = 0; i != num_vertices; ++i)
            adjacent.set(i, new SLinkedList<Integer>());
    }
    public int numVertices() {
        return adjacent.size();
    }
    public void addEdge(Integer u, Integer v) {
        adjacent.get(u).insert_front(v);
    }

    public Range<Integer> adjacent(Integer u) {
        return new Range<Integer>(adjacent.get(u).begin(), adjacent.get(u).end());
    }

    public Range<Integer> vertices() {
        return new Range<Integer>(new IntIter(0), new IntIter(adjacent.size()));
    }

}
