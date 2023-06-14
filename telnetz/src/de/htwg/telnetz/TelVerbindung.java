package de.htwg.telnetz;

public class TelVerbindung {
    int c;
    TelKnoten u;
    TelKnoten v;

    public TelVerbindung(int c, TelKnoten u, TelKnoten v) {
        this.c = c;
        this.u = u;
        this.v = v;
    }
}
