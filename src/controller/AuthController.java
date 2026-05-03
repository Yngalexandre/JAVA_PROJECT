package controller;

import service.AuthService;
import view.console.ConsoleView;

/**
 * Gère l'authentification et la gestion de session.
 */
public class AuthController {

    private final AuthService authService;
    private final ConsoleView consoleView;

    public AuthController(AuthService authService, ConsoleView consoleView) {
        this.authService = authService;
        this.consoleView = consoleView;
    }

    public void connecterAdministrateur() {
        String login = consoleView.askString("Login administrateur : ");
        String motDePasse = consoleView.askString("Mot de passe : ");

        try {
            authService.loginAdministrateur(login, motDePasse);
            consoleView.showMessage("Connexion administrateur réussie.");
        } catch (Exception e) {
            consoleView.showError("Échec de connexion administrateur : " + e.getMessage());
        }
    }

    public void connecterAbonne() {
        String login = consoleView.askString("Login abonné : ");
        String motDePasse = consoleView.askString("Mot de passe : ");

        try {
            authService.loginAbonne(login, motDePasse);
            consoleView.showMessage("Connexion abonné réussie.");
        } catch (Exception e) {
            consoleView.showError("Échec de connexion abonné : " + e.getMessage());
        }
    }

    public void continuerCommeVisiteur() {
        authService.loginVisiteur();
        consoleView.showMessage("Session visiteur démarrée.");
    }

    public void deconnecter() {
        authService.logout();
        consoleView.showMessage("Déconnexion effectuée.");
    }
}