package de.htwg.telnetz;

public final class MyArrays {
    private MyArrays() {}

    public static boolean inRange(int index, int[] arr) {
        return index >= 0 && index < arr.length;
    }
}
