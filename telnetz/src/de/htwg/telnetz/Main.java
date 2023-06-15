package de.htwg.telnetz;

public class Main {
    public static void main(String[] args) {
        TelNet telNet = new TelNet(100);
        telNet.generateRandomTelNet(1000, 1000, 1000);
//        telNet.addTelKnoten(1,1);
//        telNet.addTelKnoten(1,3);
//        telNet.addTelKnoten(2,4);
//        telNet.addTelKnoten(4,3);
//        telNet.addTelKnoten(6,2);
//        telNet.addTelKnoten(7,4);
//        telNet.addTelKnoten(6,7);

        System.out.println(telNet.computeOptTelNet());
        telNet.drawOptTelNet(800, 800);
    }
}