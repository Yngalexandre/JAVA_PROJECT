package controller;

import service.LectureService;
import view.console.ConsoleView;

/**
 * Gère la lecture simulée des morceaux.
 */
public class LectureController {

    private final LectureService lectureService;
    private final ConsoleView consoleView;

    public LectureController(LectureService lectureService, ConsoleView consoleView) {
        this.lectureService = lectureService;
        this.consoleView = consoleView;
    }

    public void lireMorceau() {
        String morceauIdOuTitre = consoleView.askString("Titre ou identifiant du morceau à lire : ");

        try {
            String resultat = lectureService.lireMorceau(morceauIdOuTitre);
            consoleView.showMessage(resultat);
        } catch (Exception e) {
            consoleView.showError("Impossible de lire le morceau : " + e.getMessage());
        }
    }
}