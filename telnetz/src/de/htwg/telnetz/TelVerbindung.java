package de.htwg.telnetz;

public class TelVerbindung {
    int c;
    TelKnoten from;
    TelKnoten to;

    public TelVerbindung(int c, TelKnoten from, TelKnoten to) {
        this.c = c;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "TelVerbindung{" +
                "c=" + c +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
