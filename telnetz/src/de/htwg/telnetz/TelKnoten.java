package de.htwg.telnetz;

public class TelKnoten {
    int x;
    int y;

    public TelKnoten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(TelKnoten that) {
        return Math.abs(this.x - that.x) + Math.abs(this.y - that.y);
    }
}
