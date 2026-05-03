package model.media;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Catalogue : Centralise l'ensemble des titres musicaux.
 */
public class Catalogue implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Morceau> morceaux = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<Artiste> artistes = new ArrayList<>();
    private List<Groupe> groupes = new ArrayList<>();

    public Catalogue() {}

    public void ajouterMorceau(Morceau m) { morceaux.add(m); }
    public void ajouterAlbum(Album a) { albums.add(a); }
    public void ajouterArtiste(Artiste a) { artistes.add(a); }
    public void ajouterGroupe(Groupe g) { groupes.add(g); }

    public List<Morceau> getMorceaux() { return morceaux; }
    public List<Album> getAlbums() { return albums; }
    public List<Artiste> getArtistes() { return artistes; }
    public List<Groupe> getGroupes() { return groupes; }

    public List<Morceau> rechercherMorceaux(String motCle) {
        return morceaux.stream()
                .filter(m -> m.getTitre().toLowerCase().contains(motCle.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Album> rechercherAlbums(String motCle) {
        return albums.stream()
                .filter(a -> a.getTitre().toLowerCase().contains(motCle.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Artiste> rechercherArtistes(String motCle) {
        return artistes.stream()
                .filter(a -> a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Groupe> rechercherGroupes(String motCle) {
        return groupes.stream()
                .filter(g -> g.getNom().toLowerCase().contains(motCle.toLowerCase()))
                .collect(Collectors.toList());
    }
}