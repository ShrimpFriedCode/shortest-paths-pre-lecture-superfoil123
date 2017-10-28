import graphs.AdjacencyList;
import graphs.Graph;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static graphs.Algorithms.dijkstra_shortest_paths;
import static org.junit.Assert.assertTrue;

public class StudentTest {

    @Test(timeout=1000)
    public void testDijkstraSix() {
        /*
         0-1->1-1->2
         |    |^   |
         8    4|   1
         |    |2   |
         V    V|   V
         3<-1-4<-3-5
        */
        int n = 6;
        AdjacencyList G = new AdjacencyList(n);
        G.addEdge(0, 1); G.addEdge(0, 3);
        G.addEdge(1, 2); G.addEdge(1, 4);
        G.addEdge(2, 5);
        G.addEdge(4, 3); G.addEdge(4, 1);
        G.addEdge(5, 4);

        HashMap<Integer,Integer> parent = new HashMap<Integer,Integer>();
        HashMap<Integer,Double> distance = new HashMap<Integer,Double>();
        HashMap<Integer,Map<Integer, Double>> length = new HashMap<Integer,Map<Integer, Double>>();

        for (int i = 0; i != n; ++i) {
            length.put(i, new HashMap<Integer,Double>());
        }
        length.get(0).put(1, 1.0); length.get(0).put(3, 8.0);
        length.get(1).put(2, 1.0); length.get(1).put(4, 4.0);
        length.get(2).put(5, 1.0);
        length.get(4).put(3, 1.0); length.get(4).put(1, 2.0);
        length.get(5).put(4, 3.0);

        Integer source = 0;
        dijkstra_shortest_paths(G, source, length, distance, parent);
        assertTrue(distance.get(0).equals(0.0));
        assertTrue(distance.get(1).equals(1.0));
        assertTrue(distance.get(2).equals(2.0));
        assertTrue(distance.get(3).equals(6.0));
        assertTrue(distance.get(4).equals(5.0));
        assertTrue(distance.get(5).equals(3.0));
        assertTrue(checkPathDistances(G, source, length, distance, parent));
    }

    @Test(timeout=1000)
    public void testDijkstraSeven() {
        /*
         Figure 9.20 from Weiss Third Edition
         */
        int n = 7;
        AdjacencyList G = new AdjacencyList(n);
        G.addEdge(0, 1); G.addEdge(0, 3);
        G.addEdge(1, 3); G.addEdge(1, 4);
        G.addEdge(2, 0); G.addEdge(2, 5);
        G.addEdge(3, 2); G.addEdge(3, 4); G.addEdge(3, 5); G.addEdge(3,6);
        G.addEdge(4, 6);
        G.addEdge(6, 5);

        HashMap<Integer,Integer> parent = new HashMap<Integer,Integer>();
        HashMap<Integer,Double> distance = new HashMap<Integer,Double>();
        HashMap<Integer,Map<Integer, Double>> length = new HashMap<Integer,Map<Integer, Double>>();

        for (int i = 0; i != n; ++i) {
            length.put(i, new HashMap<Integer,Double>());
        }
        length.get(0).put(1, 2.0); length.get(0).put(3, 1.0);
        length.get(1).put(3, 3.0); length.get(1).put(4, 10.0);
        length.get(2).put(0, 4.0); length.get(2).put(5, 5.0);
        length.get(3).put(2, 2.0); length.get(3).put(4, 2.0); length.get(3).put(5, 8.0); length.get(3).put(6, 4.0);
        length.get(4).put(6, 6.0);
        length.get(6).put(5, 1.0);

        Integer source = 0;
        dijkstra_shortest_paths(G, source, length, distance, parent);
        assertTrue(distance.get(0).equals(0.0));
        assertTrue(distance.get(1).equals(2.0));
        assertTrue(distance.get(2).equals(3.0));
        assertTrue(distance.get(3).equals(1.0));
        assertTrue(distance.get(4).equals(3.0));
        assertTrue(distance.get(5).equals(6.0));
        assertTrue(distance.get(6).equals(5.0));
        assertTrue(checkPathDistances(G, source, length, distance, parent));
    }

    @Test(timeout=1000)
    public void testDijkstraBig() {
        int n = 500;
        AdjacencyList G = new AdjacencyList(n);
        HashMap<Integer,Integer> parent = new HashMap<Integer,Integer>();
        HashMap<Integer,Double> distance = new HashMap<Integer,Double>();
        HashMap<Integer,Map<Integer, Double>> length = new HashMap<Integer,Map<Integer, Double>>();
        for (int i = 0; i != n; ++i) {
            length.put(i, new HashMap<Integer,Double>());
        }
        // add random edges and lengths
        Random gen = new Random();
        for (int i = 0; i != n*10; ++i) {
            int u = gen.nextInt(n), v = gen.nextInt(n);
            G.addEdge(u, v);
            length.get(u).put(v, new Double(gen.nextInt(10)));
        }
        Integer source = 0;
        dijkstra_shortest_paths(G, source, length, distance, parent);
        assertTrue(checkPathDistances(G, source, length, distance, parent));
    }

    static <V> boolean checkPathDistances(Graph<V> G, V source, Map<V,Map<V,Double>> length,
                                          Map<V,Double> distance, Map<V,V> parent) {
        boolean good = true;
        good &= distance.get(source).equals(0.0);
        for (V u : G.vertices()) {
            Double dist = 0.0;
            V curr = u;
            V p = parent.get(curr);
            while (! p.equals(curr)) {
                dist += length.get(p).get(curr);
                curr = p;
                p = parent.get(curr);
            }
            if (curr.equals(source))
                good &= dist.equals(distance.get(u));
        }
        return good;
    }


}