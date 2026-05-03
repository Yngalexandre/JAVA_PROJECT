package controller;

import service.AuthService;
import view.console.ConsoleView;

import java.util.List;

/**
 * Gère les opérations liées au compte utilisateur.
 */
public class CompteController {

    private final AuthService authService;
    private final ConsoleView consoleView;

    public CompteController(AuthService authService, ConsoleView consoleView) {
        this.authService = authService;
        this.consoleView = consoleView;
    }

    public void creerCompteAbonne() {
        String login = consoleView.askString("Choisissez un login : ");
        String motDePasse = consoleView.askString("Choisissez un mot de passe : ");
        String nomAffichage = consoleView.askString("Nom d'affichage : ");

        try {
            authService.registerAbonne(login, motDePasse, nomAffichage);
            consoleView.showMessage("Compte abonné créé avec succès.");
        } catch (Exception e) {
            consoleView.showError("Impossible de créer le compte : " + e.getMessage());
        }
    }

    public void afficherHistorique() {
        try {
            List<String> historique = authService.getHistoriqueUtilisateurConnecte();
            consoleView.showList("Historique d'écoute", historique);
        } catch (Exception e) {
            consoleView.showError("Impossible d'afficher l'historique : " + e.getMessage());
        }
    }
}