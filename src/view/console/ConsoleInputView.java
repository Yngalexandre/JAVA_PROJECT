package view.console;

import java.util.Scanner;

public class ConsoleInputView {

    private final Scanner scanner;

    public ConsoleInputView() {
        this.scanner = new Scanner(System.in);
    }

    public String askString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int askInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (!scanner.hasNextLine()) {
                    return -1; // Sortie sécurisée si le flux est fermé
                }
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }
}