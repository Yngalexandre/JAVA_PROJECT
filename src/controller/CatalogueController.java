package controller;

import service.CatalogueService;
import view.console.ConsoleView;

import java.util.List;

/**
 * Gère les interactions liées au catalogue musical.
 */
public class CatalogueController {

    private final CatalogueService catalogueService;
    private final ConsoleView consoleView;

    public CatalogueController(CatalogueService catalogueService, ConsoleView consoleView) {
        this.catalogueService = catalogueService;
        this.consoleView = consoleView;
    }

    public void rechercherDansCatalogue() {
        consoleView.showMessage("""
                Recherche dans le catalogue :
                1. Morceau
                2. Album
                3. Artiste
                4. Groupe
                """);

        int choix = consoleView.askInt("Votre choix : ");
        String motCle = consoleView.askString("Mot-clé : ");

        try {
            List<String> resultats = switch (choix) {
                case 1 -> catalogueService.rechercherMorceaux(motCle);
                case 2 -> catalogueService.rechercherAlbums(motCle);
                case 3 -> catalogueService.rechercherArtistes(motCle);
                case 4 -> catalogueService.rechercherGroupes(motCle);
                default -> throw new IllegalArgumentException("Type de recherche invalide.");
            };

            consoleView.showList("Résultats de recherche", resultats);
        } catch (Exception e) {
            consoleView.showError("Erreur de recherche : " + e.getMessage());
        }
    }

    public void afficherDetailsElementCatalogue() {
        consoleView.showMessage("""
                Afficher les détails de :
                1. Morceau
                2. Album
                3. Artiste
                4. Groupe
                """);

        int choix = consoleView.askInt("Votre choix : ");
        String identifiant = consoleView.askString("Nom ou identifiant : ");

        try {
            String details = switch (choix) {
                case 1 -> catalogueService.getDetailsMorceau(identifiant);
                case 2 -> catalogueService.getDetailsAlbum(identifiant);
                case 3 -> catalogueService.getDetailsArtiste(identifiant);
                case 4 -> catalogueService.getDetailsGroupe(identifiant);
                default -> throw new IllegalArgumentException("Type de détail invalide.");
            };

            consoleView.showMessage(details);
        } catch (Exception e) {
            consoleView.showError("Impossible d'afficher les détails : " + e.getMessage());
        }
    }
}