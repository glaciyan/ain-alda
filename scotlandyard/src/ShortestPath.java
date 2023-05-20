// O. Bittel;
// 26.09.2022

import directedGraph.*;
import sim.SYSimulation;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ShortestPath<V> {

    SYSimulation sim = null;

    Map<V, Double> dist;        // Distanz für jeden Knoten
    Map<V, V> pred;                // Vorgänger für jeden Knoten
    IndexMinPQ<V, Double> cand;    // Kandidaten als PriorityQueue PQ
    Heuristic<V> h;
    DirectedGraph<V> g;

    double runHeuristic(V u, V v) {
        return h == null ? 0 : h.estimatedCost(u, v);
    }

    /**
     * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege
     * nach dem A*-Verfahren berechnen kann.
     * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
     * Wird h = null gewählt, dann ist das Verfahren identisch
     * mit dem Dijkstra-Verfahren.
     *
     * @param g Gerichteter Graph
     * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
     *          dem Dijkstra-Verfahren gesucht.
     */
    public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
        dist = new HashMap<>();
        pred = new HashMap<>();
        cand = new IndexMinPQ<>();

        g.getVertexSet().forEach(v -> {
            dist.put(v, Double.POSITIVE_INFINITY);
            pred.put(v, null);
        });

        this.h = h;
        this.g = g;
    }

    /**
     * Diese Methode sollte nur verwendet werden,
     * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
     * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
     * <p>
     * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
     * <p><blockquote><pre>
     *    if (sim != null)
     *       sim.visitStation((Integer) v, Color.blue);
     * </pre></blockquote>
     *
     * @param sim SYSimulation-Objekt.
     */
    public void setSimulator(SYSimulation sim) {
        this.sim = sim;
    }

    /**
     * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
     * <p>
     * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
     * der als nächstes aus der Kandidatenliste besucht wird, animiert.
     *
     * @param s Startknoten
     * @param g Zielknoten
     */
    public void searchShortestPath(V s, V g) {
        dist.put(s, 0.0);
        cand.add(s, 0.0 + runHeuristic(s, g));

        while (!cand.isEmpty()) {
            V v = cand.removeMin();
            if (h != null && v == g) return; // Ziel

            System.out.printf("Besuche Knoten %d mit d = %f", v, cand.get(v));

            this.g.getSuccessorVertexSet(v).forEach(w -> {
                if (dist.get(w) == Double.POSITIVE_INFINITY) {
                    System.out.print(" got better");
                    pred.put(w, v);
                    dist.put(w, dist.get(v) + this.g.getWeight(v, w));
                    cand.add(w, dist.get(w) + runHeuristic(w, g));
                } else if (dist.get(v) + this.g.getWeight(v, w) < dist.get(w)) {
                    pred.put(w, v);
                    dist.put(w, dist.get(v) + this.g.getWeight(v, w));
                    cand.change(w, dist.get(w) + runHeuristic(w, g));
                }
            });

            System.out.println();
        }
    }

    /**
     * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return kürzester Weg als Liste von Knoten.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public List<V> getShortestPath() {
        if (cand.size() > 0) {
            List<V> l = new ArrayList<>();
        }
        return null;
    }

    /**
     * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return Länge eines kürzesten Weges.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public double getDistance() {
        // ...
        return 0.0;
    }

}
