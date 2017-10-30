package graphs;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import sequences.*;
import sequences.Queue;

import javax.print.DocFlavor;
import java.util.*;
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
        //known.put(source, true);
    }

    static <V> void
    relax_edge(V source, V target, Double edge_length,
               Map<V,Double> distance, Map<V,V> parent) { //UpdatingHeap<V> Q
        if (distance.get(target) > distance.get(source) + edge_length) {
            distance.put(target, distance.get(source) + edge_length);
            parent.put(target, source);
            //Q.increase_key(target);

        }
    }

    static class Node<T>{
        T vert;
        double dist;

        public Node(T ve, double v) {
            vert = ve;
            dist = v;
        }

        @Override
        public String toString() {
            return vert + " : " + dist;
        }
    }

    public static <T> Node<T> nextMin(Node prev, ArrayList<Node<T>> ar){

        if(prev == null){
            return Min(ar);
        }
        else{
            ar.remove(prev);
            return Min(ar);
        }
    }

    public static <T> Node<T> Min(ArrayList<Node<T>> ar){

        int s = ar.size();
        double dist = ar.get(0).dist;
        int ret = 0;

        for(int i  = 0; i < s; i++){
            if(ar.get(i).dist < dist){
                dist = ar.get(i).dist;
                ret = i;
            }
        }
        return ar.get(ret);
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

        HashMap<V,Boolean> known = new HashMap<V,Boolean>();

        //start with origin; known and distance is now set, move to adj
        initialize_single_source(G, source, distance, parent, known);

        ArrayList<Node<V>> verts = new ArrayList<>();

        Iter<V> vert = G.vertices().begin();

        for(int i = 0; i < G.numVertices(); i++){
            verts.add(new Node<>(vert.get(), distance.get(vert.get())));
            vert.advance();
        }


        Node<V> next = null;
        //Pick a vertex V which is not 'known' and has minimum distance value.
        for(int i = 0; i < G.numVertices(); i++){

                Node<V> n = nextMin(next, verts);
                V curr = (V) n.vert;

                //Include V to 'known'.
                known.put(curr, true);
                //Update distance value of all adjacent vertices of V.

                //get adj verts to V
                Iterator<V> adj = G.adjacent(curr).iterator();
                ArrayList<V> adjV = new ArrayList<>();

                Iter<V> test = G.adjacent(curr).begin();
                Iter<V> teste = G.adjacent(curr).end();
                while(!test.equals(teste)){
                    adjV.add(test.get());
                    test.advance();
               }

                //update dist

                for (V v : adjV) {
                    double l = length.get(curr).get(v);
                    relax_edge(curr, v, l, distance, parent);

                }
                next = n;
            }

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
