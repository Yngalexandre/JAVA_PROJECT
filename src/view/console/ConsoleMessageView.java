package view.console;

public class ConsoleMessageView {

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String errorMessage) {
        System.out.println("[ERREUR] " + errorMessage);
    }

    public void showSuccess(String message) {
        System.out.println("[OK] " + message);
    }

    public void showSectionTitle(String title) {
        System.out.println();
        System.out.println("====================================");
        System.out.println(title);
        System.out.println("====================================");
    }
}