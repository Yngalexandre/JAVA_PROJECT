package service;

import model.media.Morceau;
import model.playlist.Playlist;
import model.repository.CatalogueRepository;
import model.repository.SessionRepository;
import model.repository.UtilisateurRepository;
import model.user.Abonne;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    private final UtilisateurRepository utilisateurRepository;
    private final SessionRepository sessionRepository;
    private CatalogueRepository catalogueRepository;

    public PlaylistService(UtilisateurRepository utilisateurRepository, SessionRepository sessionRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.sessionRepository = sessionRepository;
    }

    public void setCatalogueRepository(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public List<String> getNomsPlaylistsUtilisateurConnecte() {
        Abonne abonne = getAbonneConnecte();

        List<String> noms = new ArrayList<>();
        for (Playlist playlist : abonne.getPlaylists()) {
            noms.add(playlist.getNom()); // Retourne le vrai nom, pas toString()
        }

        return noms;
    }

    public void creerPlaylist(String nom) {
        Abonne abonne = getAbonneConnecte();

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom de playlist est obligatoire.");
        }

        if (abonne.trouverPlaylistParNom(nom) != null) {
            throw new IllegalArgumentException("Une playlist avec ce nom existe déjà.");
        }

        abonne.ajouterPlaylist(new Playlist(nom));
    }

    public void renommerPlaylist(String ancienNom, String nouveauNom) {
        Abonne abonne = getAbonneConnecte();
        Playlist playlist = abonne.trouverPlaylistParNom(ancienNom);

        if (playlist == null) {
            throw new IllegalArgumentException("Playlist introuvable.");
        }

        if (nouveauNom == null || nouveauNom.isBlank()) {
            throw new IllegalArgumentException("Le nouveau nom est invalide.");
        }

        if (abonne.trouverPlaylistParNom(nouveauNom) != null) {
            throw new IllegalArgumentException("Une playlist avec ce nom existe déjà.");
        }

        playlist.renommer(nouveauNom);
    }

    public void supprimerPlaylist(String nom) {
        Abonne abonne = getAbonneConnecte();
        Playlist playlist = abonne.trouverPlaylistParNom(nom);

        if (playlist == null) {
            throw new IllegalArgumentException("Playlist introuvable.");
        }

        abonne.supprimerPlaylist(playlist);
    }

    public void ajouterMorceau(String nomPlaylist, String morceauIdOuTitre) {
        if (catalogueRepository == null) {
            throw new IllegalStateException("CatalogueRepository non configuré dans PlaylistService.");
        }

        Abonne abonne = getAbonneConnecte();
        Playlist playlist = abonne.trouverPlaylistParNom(nomPlaylist);

        if (playlist == null) {
            throw new IllegalArgumentException("Playlist introuvable.");
        }

        Morceau morceau = catalogueRepository.findMorceauByIdOrTitre(morceauIdOuTitre);
        if (morceau == null) {
            throw new IllegalArgumentException("Morceau introuvable.");
        }

        if (playlist.contientMorceau(morceau)) {
            throw new IllegalArgumentException("Le morceau est déjà présent dans la playlist.");
        }

        playlist.ajouterMorceau(morceau);
    }

    public void retirerMorceau(String nomPlaylist, String morceauIdOuTitre) {
        if (catalogueRepository == null) {
            throw new IllegalStateException("CatalogueRepository non configuré dans PlaylistService.");
        }

        Abonne abonne = getAbonneConnecte();
        Playlist playlist = abonne.trouverPlaylistParNom(nomPlaylist);

        if (playlist == null) {
            throw new IllegalArgumentException("Playlist introuvable.");
        }

        Morceau morceau = catalogueRepository.findMorceauByIdOrTitre(morceauIdOuTitre);
        if (morceau == null) {
            throw new IllegalArgumentException("Morceau introuvable.");
        }

        if (!playlist.contientMorceau(morceau)) {
            throw new IllegalArgumentException("Le morceau n'est pas dans la playlist.");
        }

        playlist.retirerMorceau(morceau);
    }

    private Abonne getAbonneConnecte() {
        Abonne abonne = sessionRepository.getAbonneConnecte();

        if (abonne == null) {
            throw new IllegalStateException("Aucun abonné connecté.");
        }

        return abonne;
    }
}