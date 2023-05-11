// O. Bittel;
// 26.09.2022

import java.util.*;

/**
 * Implementierung von DirectedGraph mit einer doppelten TreeMap
 * für die Nachfolgerknoten und einer einer doppelten TreeMap
 * für die Vorgängerknoten.
 * <p>
 * Beachte: V muss vom Typ Comparable&lt;V&gt; sein.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung
 * mit schnellem Zugriff auf die Knoten.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 19.03.2018
 */
public class AdjacencyListDirectedGraph<V extends Comparable<? super V>> implements DirectedGraph<V> {
    // doppelte Map für die Nachfolgerknoten:
    private final Map<V, Map<V, Double>> succ = new TreeMap<>();

    // doppelte Map für die Vorgängerknoten:
    private final Map<V, Map<V, Double>> pred = new TreeMap<>();

    private int numberEdge = 0;

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        g.addEdge(7, 4);


        System.out.println(g.getNumberOfVertexes());    // 7
        System.out.println(g.getNumberOfEdges());        // 8
        System.out.println(g.getVertexSet());    // 1, 2, ..., 7
        System.out.println(g);
        // 1 --> 2 weight = 1.0
        // 2 --> 5 weight = 1.0
        // 2 --> 6 weight = 1.0
        // 3 --> 7 weight = 1.0
        // ...

        System.out.println("");
        System.out.println(g.getOutDegree(2));                // 2
        System.out.println(g.getSuccessorVertexSet(2));    // 5, 6
        System.out.println(g.getInDegree(6));                // 2
        System.out.println(g.getPredecessorVertexSet(6));    // 2, 4

        System.out.println("");
        System.out.println(g.containsEdge(1, 2));    // true
        System.out.println(g.containsEdge(2, 1));    // false
        System.out.println(g.getWeight(1, 2));    // 1.0
        g.addEdge(1, 2, 5.0);
        System.out.println(g.getWeight(1, 2));    // 5.0

        System.out.println("");
        System.out.println(g.invert());
        // 1 --> 5 weight = 1.0
        // 2 --> 1 weight = 5.0
        // 3 --> 4 weight = 1.0
        // 4 --> 7 weight = 1.0
        // ...

        Set<Integer> s = g.getSuccessorVertexSet(2);
        System.out.println(s);
        s.remove(5);    // Laufzeitfehler! Warum? -- Unmodifiable Set, nur eine View
    }

    @Override
    public boolean addVertex(V v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEdge(V v, V w, double weight) {
        var first = succ.computeIfAbsent(v, k -> new TreeMap<>());
        var edgeWeight = first.get(w);
        first.put(w, weight);

        var pFirst = pred.computeIfAbsent(w, k -> new TreeMap<>());
        pFirst.put(v, weight);

        numberEdge++;
        return edgeWeight == null;
    }

    @Override
    public boolean addEdge(V v, V w) {
        return addEdge(v, w, 1);
    }

    @Override
    public boolean containsVertex(V v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsEdge(V v, V w) {
        return succ.containsKey(v) && succ.get(v).containsKey(w);
    }

    @Override
    public double getWeight(V v, V w) {
        return succ.containsKey(v) && succ.get(v).containsKey(w) ? succ.get(v).get(w) : Double.POSITIVE_INFINITY;
    }

    @Override
    public int getInDegree(V v) {
        if (pred.containsKey(v)) return pred.get(v).size();
        else return 0;
    }

    @Override
    public int getOutDegree(V v) {
        if (succ.containsKey(v))
            return succ.get(v).size();
        else return 0;
    }

    @Override
    public Set<V> getVertexSet() {
        var t = new TreeSet<V>();
        t.addAll(succ.keySet());
        t.addAll(pred.keySet());
        return Collections.unmodifiableSet(t); // nicht modifizierbare Sicht
    }

    @Override
    public Set<V> getPredecessorVertexSet(V v) {
        if (pred.containsKey(v)) return Collections.unmodifiableSet(pred.get(v).keySet());
        else return null;
    }

    @Override
    public Set<V> getSuccessorVertexSet(V v) {
        if (succ.containsKey(v)) return Collections.unmodifiableSet(succ.get(v).keySet());
        else return null;
    }

    @Override
    public int getNumberOfVertexes() {
        return getVertexSet().size();
    }

    @Override
    public int getNumberOfEdges() {
        return numberEdge;
    }

    @Override
    public DirectedGraph<V> invert() {
        AdjacencyListDirectedGraph<V> temp = new AdjacencyListDirectedGraph<>();
        succ.forEach((v, w) -> {
            w.forEach((k, weight) -> {
                temp.addEdge(k, v, weight);
            });
        });
        return temp;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        succ.forEach((v, w) -> {
            w.forEach((k, weight) -> {
                builder.append(v).append(" --> ").append(k).append(" weight: ").append(weight).append('\n');
            });
        });

        return builder.toString();
    }
}
