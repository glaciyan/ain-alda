package de.htwg.telnetz;

import org.junit.jupiter.api.Test;

public class TelNetTest {
    @Test
    void net() {
        TelNet telNet = new TelNet(7);
        telNet.addTelKnoten(1,1);
        telNet.addTelKnoten(1,3);
        telNet.addTelKnoten(2,4);
        telNet.addTelKnoten(4,3);
        telNet.addTelKnoten(6,2);
        telNet.addTelKnoten(7,4);
        telNet.addTelKnoten(6,7);
    }
}
