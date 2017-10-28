package graphs;

import sequences.SLinkedList;
import sequences.Queue;
import sequences.UpdatingHeap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class Algorithms {

    static <V> void
    initialize_single_source(Graph<V> G, V source, Map<V,Double> distance,
                             Map<V,V> parent, Map<V,Boolean> known) {
        for (V v : G.vertices()) {
            distance.put(v, Double.MAX_VALUE);
            parent.put(v, v);
            known.put(v, false);
        }
        distance.put(source, 0.0);
        known.put(source, true);
    }

    static <V> void
    relax_edge(V source, V target, Double edge_length,
               Map<V,Double> distance, Map<V,V> parent, UpdatingHeap<V> Q) {
        if (distance.get(target) > distance.get(source) + edge_length) {
            distance.put(target, distance.get(source) + edge_length);
            parent.put(target, source);
            Q.increase_key(target);
        }
    }

    /**
     * TODO
     *
     * This function computes the shortest paths from the source to all other reachable vertices in
     * the graph, using Dijkstra's single-source shortest paths algorithm.
     *
     * @param G         The graph.
     * @param source    The source vertex.
     * @param length    The length of each edge, i.e., a mapping from two vertices (source,target) to a number.
     * @param distance  The computed distance to each vertex. (This is an output of the algorithm.)
     * @param parent    The shortest paths encoded in reverse, from each vertex to its parent along the shortest path.
     * @param <V>       The vertex type.
     */
    public static <V> void
    dijkstra_shortest_paths(Graph<V> G, V source, Map<V,Map<V,Double>> length,
                            Map<V,Double> distance, Map<V,V> parent) {
        throw new UnsupportedOperationException();
    }

    public static <V> void
    topo_sort(Graph<V> G, Consumer<V> output, Map<V,Integer> num_pred) {
        // initialize the in-degrees to zero
        for (V u : G.vertices()) {
            num_pred.put(u, 0);
        }
        // compute the in-degree of each vertex
        for (V u : G.vertices())
            for (V v : G.adjacent(u))
                num_pred.put(v, num_pred.get(v) + 1);

        // collect the vertices with zero in-degree
        Queue<V> zeroes = new SLinkedList<V>();
        for (V v : G.vertices())
            if (num_pred.get(v) == 0)
                zeroes.push(v);

        // The main loop outputs a vertex with zero in-degree and subtracts
        // one from the in-degree of each of its successors, adding them to
        // the zeroes bag when they reach zero.
        while (! zeroes.empty()) {
            V u = zeroes.pop();
            output.accept(u);
            for (V v : G.adjacent(u)) {
                num_pred.put(v, num_pred.get(v) - 1);
                if (num_pred.get(v) == 0)
                    zeroes.push(v);
            }
        }
    }

}
