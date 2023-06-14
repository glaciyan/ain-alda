package de.htwg.telnetz;

public class TelNet {
    private final int lbg;
    public TelNet(int lbg) {
        this.lbg = lbg;
    }

    public double cost(TelKnoten a, TelKnoten b) {
        double dist = a.distanceTo(b);
        return dist <= lbg ? dist : Double.POSITIVE_INFINITY;
    }

    public boolean addTelKnoten(int x, int y) {

    }
}
