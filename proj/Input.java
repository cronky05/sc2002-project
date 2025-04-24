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
            scanner.next();
            System.out.print(prompt);
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    public int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not an integer. Try again.");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        while (isNumeric(line.trim())) {
            System.out.println("That’s not a sentence. Try again.");
            System.out.print(prompt);
            line = scanner.nextLine();
        }
        return line;
    }

    public String readLine() {
        String line = scanner.nextLine();
        while (isNumeric(line.trim())) {
            System.out.println("That’s not a sentence. Try again.");
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
            scanner.nextLine();
        }
        return word;
    }

    public String readWord() {
        String word = scanner.next();
        while (isNumeric(word)) {
            System.out.println("That's a number, not a word. Try again.");
            word = scanner.next();
            scanner.nextLine();
        }
        return word;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void close() {
        scanner.close();
    }
}
