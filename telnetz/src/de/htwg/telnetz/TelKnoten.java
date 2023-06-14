package de.htwg.telnetz;

public class TelKnoten {
    int x;
    int y;

    public TelKnoten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int distanceTo(TelKnoten that) {
        return Math.abs(this.x - that.x) + Math.abs(this.y - that.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelKnoten telKnoten = (TelKnoten) o;

        if (x != telKnoten.x) return false;
        return y == telKnoten.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
