package de.htwg.telnetz;

import java.util.*;

public class TelNet {
    private final int lbg;
    private final Map<TelKnoten, Integer> knoten = new HashMap<>();
    private final PriorityQueue<TelVerbindung> verbindungen = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));

    public TelNet(int lbg) {
        this.lbg = lbg;
    }

    private int cost(TelKnoten a, TelKnoten b) {
        int dist = a.distanceTo(b);
        return dist <= lbg ? dist : Integer.MAX_VALUE;
    }

    private int counter = 0;

    public boolean addTelKnoten(int x, int y) {
        TelKnoten newOne = new TelKnoten(x, y);
        boolean isNew = !knoten.containsKey(newOne);
        if (isNew) {
            knoten.forEach((k, v) -> verbindungen.add(new TelVerbindung(cost(k, newOne), k, newOne)));
            knoten.put(newOne, counter++);
            return true;
        } else {
            return false;
        }
    }

    private final static Random rand = new Random();

    public void generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i <= n; i++) {
            addTelKnoten(rand.nextInt(xMax), rand.nextInt(yMax));
        }
    }
}
