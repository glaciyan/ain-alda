package de.htwg.telnetz;

import java.util.Arrays;

import static de.htwg.telnetz.MyArrays.inRange;

public class UnionFind {
    private final int[] parents;
    private int size;

    /**
     * Legt eine neue Union-Find-Struktur mit der Partitionierung <pre>{{0}, {1}, ..., {n-1}}</pre> an.
     *
     * @param n Größe der Grundmenge.
     */
    public UnionFind(int n) {
        parents = new int[n];
        Arrays.fill(parents, -1);
        size = n;
    }

    /**
     * Vereinigt die beiden Menge s1 und s2. s1 und s2 müssen Repräsentanten der jeweiligen Menge sein.
     * Die Vereinigung wird nur durchgeführt, falls s1 und s2 unterschiedlich sind.
     * Es wird union-by-height durchgeführt.
     *
     * @param a Element, das eine Menge repräsentiert.
     * @param rep Element, das eine Menge repräsentiert.
     * @throws java.lang.IllegalArgumentException falls s1 oder s2 nicht zur Grundmenge gehören
     */
    public void union(int a, int rep) {
        // Schau, ob a und b sich in der grundmenge befinden
        if (!(inRange(a, parents) && parents[a] == -1) || !(inRange(rep, parents) && parents[rep] == -1)) {
            throw new IllegalArgumentException();
        }

        parents[a] = rep;
        size--;
    }

    /**
     * Liefert den Repräsentanten der Menge zurück, zu der e gehört.
     * @param a Element
     * @return Repräsentant der Menge, zu der e gehört.
     */
    public int find(int a) {
        int out = a;
        while (a != -1) {
            out = a;
            a = parents[a];
        }
        return out;
    }

    /**
     * Liefert die Anzahl der Mengen in der Partitionierung zurück.
     * @return Anzahl der Mengen.
     */
    public int size() {
        return size;
    }
}
