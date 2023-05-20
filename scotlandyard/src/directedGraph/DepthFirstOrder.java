package directedGraph;// O. Bittel;
// 26.09.2022

import java.util.*;

/**
 * Klasse für Tiefensuche.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 22.02.2017
 */
public class DepthFirstOrder<V> {

    private final List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private int numberOfDFTrees = 0;
    // ...

    void visitAllDFNodes() {
        Set<V> visited = new HashSet<>();

        myGraph.getVertexSet().forEach(v -> {
            if (!visited.contains(v)) {
                numberOfDFTrees++;
                visitDF(v, myGraph, visited);
            }
        });
    }

    private void visitDF(V v, DirectedGraph<V> myGraph, Set<V> visited) {
        visited.add(v);

        preOrder.add(v);

        myGraph.getSuccessorVertexSet(v).forEach(s -> {
            if (!visited.contains(s)) {
                visitDF(s, myGraph, visited);
            }
        });

        postOrder.add(v);
    }

    /**
     * Führt eine Tiefensuche für g durch.
     *
     * @param g gerichteter Graph.
     */
    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;
        visitAllDFNodes();
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {
        return Collections.unmodifiableList(preOrder);
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Post-Order-Reihenfolge zurück.
     *
     * @return Post-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> postOrder() {
        return Collections.unmodifiableList(postOrder);
    }

    /**
     * @return Anzahl der Bäume des Tiefensuchwalds.
     */
    public int numberOfDFTrees() {
        return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        //g.addEdge(7,3);
        g.addEdge(7, 4);

        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);
        System.out.println(dfs.numberOfDFTrees());    // 2
        System.out.println(dfs.preOrder());        // [1, 2, 5, 6, 3, 7, 4]
        System.out.println(dfs.postOrder());        // [5, 6, 2, 1, 4, 7, 3]

    }
}
