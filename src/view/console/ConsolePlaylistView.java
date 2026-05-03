package view.console;

import java.util.List;

public class ConsolePlaylistView {

    public void afficherPlaylists(List<String> playlists) {
        System.out.println("===== MES PLAYLISTS =====");

        if (playlists == null || playlists.isEmpty()) {
            System.out.println("Aucune playlist.");
            return;
        }

        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i));
        }
    }

    public void afficherContenuPlaylist(String nomPlaylist, List<String> morceaux) {
        System.out.println("===== PLAYLIST : " + nomPlaylist + " =====");

        if (morceaux == null || morceaux.isEmpty()) {
            System.out.println("Cette playlist est vide.");
            return;
        }

        for (int i = 0; i < morceaux.size(); i++) {
            System.out.println((i + 1) + ". " + morceaux.get(i));
        }
    }
}