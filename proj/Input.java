import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input() {
        scanner = new Scanner(System.in);
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("That's not an integer. Try again.");
            scanner.next(); // consume bad input
            System.out.print(prompt);
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        while (isNumeric(line.trim())) {
            System.out.println("That’s not a valid sentence, that’s just a sad little number. Try again.");
            System.out.print(prompt);
            line = scanner.nextLine();
        }
        return line;
    }

    public String readWord(String prompt) {
        System.out.print(prompt);
        String word = scanner.next();
        while (isNumeric(word)) {
            System.out.println("That's a number, not a word. Try again.");
            System.out.print(prompt);
            word = scanner.next();
            scanner.nextLine(); // consume newline again
        }
        return word;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // catches ints, decimals, and your laziness
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void close() {
        scanner.close();
    }
}
