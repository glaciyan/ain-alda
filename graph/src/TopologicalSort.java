// O. Bittel;
// 26.09.22

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	// ...

	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
		Map<V, Integer> inDegree = new HashMap<>();
		Queue<V> q = new LinkedList<>();

		g.getVertexSet().forEach(v -> {
			inDegree.put(v, g.getInDegree(v));
			if (inDegree.get(v) == 0) {
				q.add(v);
			}
		});

		while (!q.isEmpty()) {
			V v = q.remove();
			ts.add(v);

			g.getSuccessorVertexSet(v).forEach(w -> {
				int degree = inDegree.get(w);
				degree--;
				inDegree.put(w, degree);

				if (degree == 0) {
					q.add(w);
				}
			});
		}

		if (ts.size() != g.getVertexSet().size()) {
			ts = null;
		}
    }
    
	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}
	}
}
