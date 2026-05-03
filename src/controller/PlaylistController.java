package controller;

import view.console.ConsoleView;
import service.PlaylistService;

import java.util.List;

/**
 * Gère les interactions liées aux playlists des abonnés.
 */
public class PlaylistController {

    private final PlaylistService playlistService;
    private final ConsoleView consoleView;

    public PlaylistController(PlaylistService playlistService, ConsoleView consoleView) {
        this.playlistService = playlistService;
        this.consoleView = consoleView;
    }

    public void afficherMenuPlaylists() {
        boolean actif = true;

        while (actif) {
            consoleView.showMessage("""
                    Gestion des playlists :
                    1. Voir mes playlists
                    2. Créer une playlist
                    3. Renommer une playlist
                    4. Supprimer une playlist
                    5. Ajouter un morceau à une playlist
                    6. Retirer un morceau d'une playlist
                    7. Retour
                    """);

            int choix = consoleView.askInt("Votre choix : ");

            switch (choix) {
                case 1 -> afficherPlaylists();
                case 2 -> creerPlaylist();
                case 3 -> renommerPlaylist();
                case 4 -> supprimerPlaylist();
                case 5 -> ajouterMorceauAPlaylist();
                case 6 -> retirerMorceauDePlaylist();
                case 7 -> actif = false;
                default -> consoleView.showError("Choix invalide.");
            }
        }
    }

    public void afficherPlaylists() {
        try {
            List<String> playlists = playlistService.getNomsPlaylistsUtilisateurConnecte();
            consoleView.showList("Mes playlists", playlists);
        } catch (Exception e) {
            consoleView.showError("Erreur lors de l'affichage des playlists : " + e.getMessage());
        }
    }

    public void creerPlaylist() {
        String nom = consoleView.askString("Nom de la nouvelle playlist : ");

        try {
            playlistService.creerPlaylist(nom);
            consoleView.showMessage("Playlist créée avec succès.");
        } catch (Exception e) {
            consoleView.showError("Impossible de créer la playlist : " + e.getMessage());
        }
    }

    public void renommerPlaylist() {
        String ancienNom = consoleView.askString("Nom actuel de la playlist : ");
        String nouveauNom = consoleView.askString("Nouveau nom : ");

        try {
            playlistService.renommerPlaylist(ancienNom, nouveauNom);
            consoleView.showMessage("Playlist renommée avec succès.");
        } catch (Exception e) {
            consoleView.showError("Impossible de renommer la playlist : " + e.getMessage());
        }
    }

    public void supprimerPlaylist() {
        String nom = consoleView.askString("Nom de la playlist à supprimer : ");

        try {
            playlistService.supprimerPlaylist(nom);
            consoleView.showMessage("Playlist supprimée avec succès.");
        } catch (Exception e) {
            consoleView.showError("Impossible de supprimer la playlist : " + e.getMessage());
        }
    }

    public void ajouterMorceauAPlaylist() {
        String nomPlaylist = consoleView.askString("Nom de la playlist : ");
        String morceauIdOuTitre = consoleView.askString("Titre ou identifiant du morceau : ");

        try {
            playlistService.ajouterMorceau(nomPlaylist, morceauIdOuTitre);
            consoleView.showMessage("Morceau ajouté à la playlist.");
        } catch (Exception e) {
            consoleView.showError("Impossible d'ajouter le morceau : " + e.getMessage());
        }
    }

    public void retirerMorceauDePlaylist() {
        String nomPlaylist = consoleView.askString("Nom de la playlist : ");
        String morceauIdOuTitre = consoleView.askString("Titre ou identifiant du morceau à retirer : ");

        try {
            playlistService.retirerMorceau(nomPlaylist, morceauIdOuTitre);
            consoleView.showMessage("Morceau retiré de la playlist.");
        } catch (Exception e) {
            consoleView.showError("Impossible de retirer le morceau : " + e.getMessage());
        }
    }
}