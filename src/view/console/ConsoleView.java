package view.console;

import java.util.List;

public class ConsoleView {

    private final ConsoleInputView inputView;
    private final ConsoleMessageView messageView;
    private final ConsoleMenuRenderer menuRenderer;
    private final ConsoleCatalogueView catalogueView;
    private final ConsolePlaylistView playlistView;
    private final ConsoleCompteView compteView;
    private final ConsoleAdminView adminView;

    public ConsoleView() {
        this.inputView = new ConsoleInputView();
        this.messageView = new ConsoleMessageView();
        this.menuRenderer = new ConsoleMenuRenderer();
        this.catalogueView = new ConsoleCatalogueView();
        this.playlistView = new ConsolePlaylistView();
        this.compteView = new ConsoleCompteView();
        this.adminView = new ConsoleAdminView();
    }

    public String askString(String prompt) {
        return inputView.askString(prompt);
    }

    public int askInt(String prompt) {
        return inputView.askInt(prompt);
    }

    public void showMessage(String message) {
        messageView.showMessage(message);
    }

    public void showError(String errorMessage) {
        messageView.showError(errorMessage);
    }

    public void showSuccess(String message) {
        messageView.showSuccess(message);
    }

    public void showSectionTitle(String title) {
        messageView.showSectionTitle(title);
    }

    public void showList(String titre, List<String> elements) {
        System.out.println("===== " + titre.toUpperCase() + " =====");

        if (elements == null || elements.isEmpty()) {
            System.out.println("Aucun élément à afficher.");
            return;
        }

        for (int i = 0; i < elements.size(); i++) {
            System.out.println((i + 1) + ". " + elements.get(i));
        }
    }

    public void afficherMenuPrincipal() {
        menuRenderer.afficherMenuPrincipal();
    }

    public void afficherMenuVisiteur() {
        menuRenderer.afficherMenuVisiteur();
    }

    public void afficherMenuAbonne() {
        menuRenderer.afficherMenuAbonne();
    }

    public void afficherMenuAdmin() {
        menuRenderer.afficherMenuAdmin();
    }

    public void afficherMenuPlaylists() {
        menuRenderer.afficherMenuPlaylists();
    }

    public void afficherMenuGestionCatalogue() {
        menuRenderer.afficherMenuGestionCatalogue();
    }

    public void afficherMenuGestionAbonnes() {
        menuRenderer.afficherMenuGestionAbonnes();
    }

    public void afficherMenuRechercheCatalogue() {
        menuRenderer.afficherMenuRechercheCatalogue();
    }

    public void afficherMenuDetailsCatalogue() {
        menuRenderer.afficherMenuDetailsCatalogue();
    }

    public void afficherResultatsCatalogue(List<String> resultats) {
        catalogueView.afficherResultatsRecherche(resultats);
    }

    public void afficherDetailsCatalogue(String details) {
        catalogueView.afficherDetails(details);
    }

    public void afficherPlaylists(List<String> playlists) {
        playlistView.afficherPlaylists(playlists);
    }

    public void afficherContenuPlaylist(String nomPlaylist, List<String> morceaux) {
        playlistView.afficherContenuPlaylist(nomPlaylist, morceaux);
    }

    public void afficherHistorique(List<String> historique) {
        compteView.afficherHistorique(historique);
    }

    public void afficherInfosCompte(String login, String nomAffichage, String role) {
        compteView.afficherInfosCompte(login, nomAffichage, role);
    }

    public void afficherStatistiques(String statistiques) {
        adminView.afficherStatistiques(statistiques);
    }
}