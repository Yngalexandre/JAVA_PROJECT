package controller;

import model.common.GenreMusical;
import model.media.Morceau;
import model.stats.StatistiquesService;
import service.AdminService;
import view.console.ConsoleView;

import java.util.List;
import java.util.Map;

/**
 * Gère les opérations réservées aux administrateurs.
 */
public class AdminController {

    private final AdminService adminService;
    private final StatistiquesService statistiquesService;
    private final ConsoleView consoleView;

    public AdminController(AdminService adminService, StatistiquesService statistiquesService, ConsoleView consoleView) {
        this.adminService = adminService;
        this.statistiquesService = statistiquesService;
        this.consoleView = consoleView;
    }

    public void gererCatalogue() {
        boolean actif = true;

        while (actif) {
            consoleView.showMessage("""
                    Gestion du catalogue :
                    1. Ajouter un morceau
                    2. Supprimer un morceau
                    3. Ajouter un album
                    4. Supprimer un album
                    5. Ajouter un artiste
                    6. Supprimer un artiste
                    7. Ajouter un groupe
                    8. Supprimer un groupe
                    9. Retour
                    """);

            int choix = consoleView.askInt("Votre choix : ");

            try {
                switch (choix) {
                    case 1 -> ajouterMorceau();
                    case 2 -> supprimerMorceau();
                    case 3 -> ajouterAlbum();
                    case 4 -> supprimerAlbum();
                    case 5 -> ajouterArtiste();
                    case 6 -> supprimerArtiste();
                    case 7 -> ajouterGroupe();
                    case 8 -> supprimerGroupe();
                    case 9 -> actif = false;
                    default -> consoleView.showError("Choix invalide.");
                }
            } catch (Exception e) {
                consoleView.showError("Erreur d'administration du catalogue : " + e.getMessage());
            }
        }
    }

    public void gererComptesAbonnes() {
        boolean actif = true;

        while (actif) {
            consoleView.showMessage("""
                    Gestion des abonnés :
                    1. Suspendre un compte
                    2. Supprimer un compte
                    3. Voir les statistiques avancées
                    4. Retour
                    """);

            int choix = consoleView.askInt("Votre choix : ");

            switch (choix) {
                case 1 -> suspendreCompte();
                case 2 -> supprimerCompte();
                case 3 -> afficherStatistiquesEvoluees();
                case 4 -> actif = false;
                default -> consoleView.showError("Choix invalide.");
            }
        }
    }

    public void afficherStatistiques() {
        try {
            String stats = adminService.getStatistiquesSimples();
            consoleView.showMessage(stats);
        } catch (Exception e) {
            consoleView.showError("Impossible d'afficher les statistiques : " + e.getMessage());
        }
    }

    private void ajouterMorceau() {
        String titre = consoleView.askString("Titre du morceau : ");
        int duree = consoleView.askInt("Durée (en secondes) : ");
        String artisteOuGroupe = consoleView.askString("Artiste ou groupe : ");

        adminService.ajouterMorceau(titre, duree, artisteOuGroupe);
        consoleView.showMessage("Morceau ajouté.");
    }

    private void supprimerMorceau() {
        String identifiant = consoleView.askString("Titre ou identifiant du morceau à supprimer : ");
        adminService.supprimerMorceau(identifiant);
        consoleView.showMessage("Morceau supprimé.");
    }

    private void ajouterAlbum() {
        String titre = consoleView.askString("Titre de l'album : ");
        String artisteOuGroupe = consoleView.askString("Artiste ou groupe : ");

        adminService.ajouterAlbum(titre, artisteOuGroupe);
        consoleView.showMessage("Album ajouté.");
    }

    private void supprimerAlbum() {
        String identifiant = consoleView.askString("Titre ou identifiant de l'album à supprimer : ");
        adminService.supprimerAlbum(identifiant);
        consoleView.showMessage("Album supprimé.");
    }

    private void ajouterArtiste() {
        String nom = consoleView.askString("Nom de l'artiste : ");
        adminService.ajouterArtiste(nom);
        consoleView.showMessage("Artiste ajouté.");
    }

    private void supprimerArtiste() {
        String nom = consoleView.askString("Nom de l'artiste à supprimer : ");
        adminService.supprimerArtiste(nom);
        consoleView.showMessage("Artiste supprimé.");
    }

    private void ajouterGroupe() {
        String nom = consoleView.askString("Nom du groupe : ");
        adminService.ajouterGroupe(nom);
        consoleView.showMessage("Groupe ajouté.");
    }

    private void supprimerGroupe() {
        String nom = consoleView.askString("Nom du groupe à supprimer : ");
        adminService.supprimerGroupe(nom);
        consoleView.showMessage("Groupe supprimé.");
    }

    private void suspendreCompte() {
        String login = consoleView.askString("Login de l'abonné à suspendre : ");
        try {
            adminService.suspendreCompteAbonne(login);
            consoleView.showMessage("Compte suspendu.");
        } catch (Exception e) {
            consoleView.showError("Impossible de suspendre le compte : " + e.getMessage());
        }
    }

    private void supprimerCompte() {
        String login = consoleView.askString("Login de l'abonné à supprimer : ");
        try {
            adminService.supprimerCompteAbonne(login);
            consoleView.showMessage("Compte supprimé.");
        } catch (Exception e) {
            consoleView.showError("Impossible de supprimer le compte : " + e.getMessage());
        }
    }

    private void afficherStatistiquesEvoluees() {
        consoleView.showMessage("\n===== STATISTIQUES AVANCÉES =====");

        // Top Morceaux
        List<Morceau> top = statistiquesService.getTopMorceaux(5);
        consoleView.showMessage("\n--- Top 5 des morceaux les plus écoutés ---");
        if (top.isEmpty()) {
            consoleView.showMessage("Aucun morceau écouté.");
        } else {
            for (int i = 0; i < top.size(); i++) {
                Morceau m = top.get(i);
                consoleView.showMessage((i + 1) + ". " + m.getTitre() + " (" + m.getNombreEcoutes() + " écoutes)");
            }
        }

        // Artiste populaire
        String artiste = statistiquesService.getArtisteLePlusPopulaire();
        consoleView.showMessage("\n--- Artiste/Groupe le plus populaire ---");
        consoleView.showMessage("📍 " + artiste);

        // Répartition par genre
        Map<GenreMusical, Long> repartition = statistiquesService.getRepartitionParGenre();
        consoleView.showMessage("\n--- Répartition par style musical ---");
        repartition.forEach((genre, count) ->
                consoleView.showMessage("- " + genre + " : " + count + " morceaux")
        );
        consoleView.showMessage("================================\n");
    }
}