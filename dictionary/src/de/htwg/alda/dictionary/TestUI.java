package de.htwg.alda.dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestUI {
    public static Dictionary<String, String> dict = new SortedArrayDictionary<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();

            try (Scanner stringScanner = new Scanner(input)) {

                String command = stringScanner.next();

                if (command.equals("exit")) {
                    break;
                }

                switch (command) {
                    case "create" -> createCmd(stringScanner);
                    case "r" -> readCmd(stringScanner);
                    case "p" -> printCmd();
                    case "s" -> searchCmd(stringScanner);
                    case "i" -> insertCmd(stringScanner);
                    case "d" -> deleteCmd(stringScanner);
                    default -> unknownCmd();
                }
            } catch (RuntimeException e) {
                System.out.println("Error!");
                System.out.println(e.getClass() + " " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye! Tschüss!");
    }

    private static void unknownCmd() {
        System.out.println("Unknown Command!");
    }

    public static void createCmd(Scanner scanner) {
        String impl = scanner.next();

        dict = switch (impl) {
            case "HashDictionary" -> new HashDictionary<>();
            case "BinaryTree" -> new BinaryTreeDictionary<>();
            default -> new SortedArrayDictionary<>();
        };

        System.out.println("Created " + dict.getClass().getName());
    }

    public static void readCmd(Scanner scanner) {
        boolean hasN = scanner.hasNextInt();
        int n = 0;
        if (hasN) n = scanner.nextInt();
        String fileName = scanner.next();

        try (Scanner reader = new Scanner(new FileReader(fileName))) {
            long startTime = startTimer();
            int i = 0;
            while (reader.hasNextLine()) {
                if ((hasN && i < n) || !reader.hasNext()) break;
                dict.insert(reader.next(), reader.next());
                i++;
            }

            double endTime = endTimer(startTime);

            System.out.printf("Read %d entries in %.2fms%n", i, endTime);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printCmd() {
        for (var e : dict) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }

    private static void searchCmd(Scanner scanner) {
        String deutsch = scanner.next();

        long startTime = startTimer();
        String english = dict.search(deutsch);
        double endTime = endTimer(startTime);

        if (english == null) System.out.printf("Nothing found in %.2fms%n", endTime);
        else System.out.printf(english + " found in %.2fms%n", endTime);
    }

    private static void insertCmd(Scanner scanner) {
        String deutsch = scanner.next();
        String english = scanner.next();

        String replaced = dict.insert(deutsch, english);
        if (replaced == null) System.out.println("Inserted");
        else System.out.println(replaced + " was replaced");
    }

    private static void deleteCmd(Scanner scanner) {
        String deutsch = scanner.next();

        String removed = dict.remove(deutsch);
        if (removed == null) System.out.println("Nothing deleted");
        else System.out.println(removed + " was removed");
    }

    private static long startTimer() {
        return System.nanoTime();
    }

    private static double endTimer(long start) {
        long end = System.nanoTime();
        return (double) (end - start) / 1.0e06;
    }
}
