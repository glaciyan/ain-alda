package directedGraph;// O. Bittel;
// 26.09.22

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 02.03.2020
 */
public class StrongComponents<V> {
    // comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

    private final Map<Integer, Set<V>> comp = new TreeMap<>();

    /**
     * Ermittelt alle strengen Komponenten mit
     * dem Kosaraju-Sharir Algorithmus.
     *
     * @param g gerichteter Graph.
     */
    public StrongComponents(DirectedGraph<V> g) {
        List<V> postOrder = new DepthFirstOrder<>(g).postOrder();
        List<V> reversePostOrder = new ArrayList<>(postOrder.size());
        for (int i = postOrder.size() - 1; i >= 0; i--) {
            reversePostOrder.add(postOrder.get(i));
        }

        DirectedGraph<V> rg = g.invert();

        visitAllDFNodes(reversePostOrder, rg);
    }

    void visitAllDFNodes(List<V> vertexes, DirectedGraph<V> myGraph) {
        Set<V> visited = new HashSet<>();

        int c = 0;

        for (V v : vertexes) {
            if (!visited.contains(v)) {
                Set<V> cSet = new HashSet<>();
                comp.put(c++, cSet);
                visitDF(v, myGraph, visited, cSet);
            }
        }
    }

    private void visitDF(V v, DirectedGraph<V> myGraph, Set<V> visited, Set<V> cSet) {
        visited.add(v);

        myGraph.getSuccessorVertexSet(v).forEach(s -> {
            cSet.add(v);
            if (!visited.contains(s)) {
                visitDF(s, myGraph, visited, cSet);
            }
        });
    }

    /**
     * @return Anzahl der strengen Komponeneten.
     */
    public int numberOfComp() {
        return comp.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        comp.forEach((n, set) -> {
            stringBuilder.append("Component ").append(n).append(": ");
            set.forEach(v -> stringBuilder.append(v).append(", "));
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    /**
     * Liest einen gerichteten Graphen von einer Datei ein.
     *
     * @param fn Dateiname.
     * @return gerichteter Graph.
     * @throws FileNotFoundException
     */
    public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        Scanner sc = new Scanner(fn);
        sc.nextLine();
        sc.nextLine();
        while (sc.hasNextInt()) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(v, w);
        }
        return g;
    }

    private static void test1() {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        g.addEdge(1, 4);
        g.addEdge(5, 4);

        g.addEdge(5, 7);
        g.addEdge(6, 5);
        g.addEdge(7, 6);

        g.addEdge(7, 8);
        g.addEdge(8, 2);

        StrongComponents<Integer> sc = new StrongComponents<>(g);

        System.out.println(sc.numberOfComp());  // 4

        System.out.println(sc);
        // Component 0: 5, 6, 7,
        // Component 1: 8,
        // Component 2: 1, 2, 3,
        // Component 3: 4,
    }

    private static void test2() throws FileNotFoundException {
        DirectedGraph<Integer> g = readDirectedGraph(new File("mediumDG.txt"));
        System.out.println(g.getNumberOfVertexes());
        System.out.println(g.getNumberOfEdges());
        System.out.println(g);

        System.out.println("");

        StrongComponents<Integer> sc = new StrongComponents<>(g);
        System.out.println(sc.numberOfComp());  // 10
        System.out.println(sc);

    }

    public static void main(String[] args) throws FileNotFoundException {
        test1();
        test2();
    }
}
