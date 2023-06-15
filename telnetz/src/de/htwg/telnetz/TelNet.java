package de.htwg.telnetz;

import java.awt.*;
import java.util.*;
import java.util.List;

import princetonStdLib.StdDraw;

public class TelNet {
    private final int lbg;
    private final Map<TelKnoten, Integer> knoten = new HashMap<>();

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
            knoten.put(newOne, counter++);
            return true;
        } else {
            return false;
        }
    }

    private final Random rand = new Random();

    public void generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i <= n; i++) {
            addTelKnoten(rand.nextInt(xMax - 1), rand.nextInt(yMax - 1));
        }
    }

    private PriorityQueue<TelVerbindung> getVerbindungen() {
        PriorityQueue<TelVerbindung> verbindungen = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));
        knoten.forEach((k, v) -> knoten.forEach((t, u) -> {
            if (k != t) verbindungen.add(new TelVerbindung(cost(k, t), k, t));
        }));
        return verbindungen;
    }

    private List<TelVerbindung> mb;

    public boolean computeOptTelNet() {
        UnionFind forest = new UnionFind(knoten.size());
        mb = new ArrayList<>();
        PriorityQueue<TelVerbindung> edges = getVerbindungen();

        TelVerbindung c;
        while (forest.size() != 1 && (c = edges.poll()) != null) {
            int first = forest.find(knoten.get(c.from));
            int second = forest.find(knoten.get(c.to));
            if (first != second) {
                forest.union(first, second);
                mb.add(c);
            }
        }

        if (forest.size() != 1) {
            mb = null;
            return false;
        }

        return true;
    }

    public List<TelVerbindung> getOptTelNet() {
        if (mb == null) throw new IllegalStateException();
        return mb;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void drawOptTelNet(int xMax, int yMax) {
        if (mb == null) throw new IllegalStateException();
        StdDraw.setCanvasSize(xMax, yMax);
        int xx = knoten.keySet().stream().mapToInt(k -> k.x).max().getAsInt();
        int yy = knoten.keySet().stream().mapToInt(k -> k.y).max().getAsInt();

        StdDraw.setPenColor(Color.RED);
        mb.forEach(t -> {
//            StdDraw.line((double) t.from.x /xx, (double)t.from.y/yy, (double) t.to.x/xx, (double) t.from.y/yy);
//            StdDraw.line((double) t.to.x/xx, (double) t.from.y/yy, (double) t.to.x/xx, (double) t.to.y/yy);
            StdDraw.line((double) t.from.x /xx, (double)t.from.y/yy, (double) t.to.x/xx, (double) t.to.y/yy);
        });

        StdDraw.setPenColor(Color.BLUE);
        knoten.keySet().forEach(k -> StdDraw.filledCircle((double) k.x /xx, (double) k.y /yy, 0.003));

        StdDraw.show(0);
    }
}
