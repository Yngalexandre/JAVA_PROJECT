package view.common;

import view.console.ConsoleView;

/**
 * Fabrique simple pour créer les vues.
 * Pour l'instant, seule la vue console est réellement branchée.
 */
public class ViewFactory {

    private ViewFactory() {
    }

    public static ConsoleView createConsoleView() {
        return new ConsoleView();
    }

    // Plus tard :
    // public static MainFrame createGuiView() {
    //     return new MainFrame();
    // }
}