package controller;

import view.console.ConsoleView;

/**
 * Gère l'affichage des menus de navigation
 * et la lecture des choix utilisateur.
 */
public class NavigationController {

    private final ConsoleView consoleView;

    public NavigationController(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public int afficherMenuPrincipalEtLireChoix() {
        consoleView.showMessage("""
                =========================
                       JAVAZIC
                =========================
                1. Se connecter en tant qu'administrateur
                2. Se connecter en tant que client
                3. Créer un compte client
                4. Continuer en tant que simple visiteur
                5. Quitter
                """);

        return consoleView.askInt("Votre choix : ");
    }

    public int afficherMenuVisiteurEtLireChoix() {
        consoleView.showMessage("""
                ===== MENU VISITEUR =====
                1. Rechercher dans le catalogue
                2. Consulter les détails d'un élément
                3. Écouter un morceau
                4. Retour au menu principal
                """);

        return consoleView.askInt("Votre choix : ");
    }

    public int afficherMenuAbonneEtLireChoix() {
        consoleView.showMessage("""
                ===== MENU ABONNÉ =====
                1. Rechercher dans le catalogue
                2. Consulter les détails d'un élément
                3. Écouter un morceau
                4. Gérer mes playlists
                5. Consulter mon historique
                6. Mes recommandations
                7. Déconnexion
                """);

        return consoleView.askInt("Votre choix : ");
    }

    public int afficherMenuAdminEtLireChoix() {
        consoleView.showMessage("""
                ===== MENU ADMIN =====
                1. Gérer le catalogue musical
                2. Gérer les comptes abonnés
                3. Consulter les statistiques
                4. Déconnexion
                """);

        return consoleView.askInt("Votre choix : ");
    }
}