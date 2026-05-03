package model.user;

import model.common.EtatCompte;
import model.common.Role;
import model.playlist.HistoriqueEcoute;
import model.playlist.Playlist;

import java.util.ArrayList;
import java.util.List;

public class Abonne extends Utilisateur {

    private EtatCompte etatCompte;
    private List<Playlist> playlists;
    private HistoriqueEcoute historiqueEcoute;

    public Abonne(String id, String login, String motDePasse, String nomAffichage) {
        super(id, login, motDePasse, nomAffichage, Role.ABONNE);
        this.etatCompte = EtatCompte.ACTIF;
        this.playlists = new ArrayList<>();
        this.historiqueEcoute = new HistoriqueEcoute();
    }

    public EtatCompte getEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(EtatCompte etatCompte) {
        this.etatCompte = etatCompte;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public HistoriqueEcoute getHistoriqueEcoute() {
        return historiqueEcoute;
    }

    public void ajouterPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public void supprimerPlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    public Playlist trouverPlaylistParNom(String nom) {
        for (Playlist playlist : playlists) {
            if (playlist.getNom().equalsIgnoreCase(nom)) {
                return playlist;
            }
        }
        return null;
    }

    public boolean estActif() {
        return etatCompte == EtatCompte.ACTIF;
    }

    @Override
    public String toString() {
        return "Abonne{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", nomAffichage='" + nomAffichage + '\'' +
                ", etatCompte=" + etatCompte +
                ", nombrePlaylists=" + playlists.size() +
                '}';
    }
}