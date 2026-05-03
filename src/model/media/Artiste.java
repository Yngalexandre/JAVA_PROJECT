package model.media;

import model.common.Identifiable;
import java.util.ArrayList;
import java.util.List;

public class Artiste implements Identifiable {
    private String id;
    private String nom;
    private List<Morceau> morceaux = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();

    public Artiste(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Override
    public String getId() { return id; }
    public String getNom() { return nom; }
    public List<Morceau> getMorceaux() { return morceaux; }
    public List<Album> getAlbums() { return albums; }

    public void ajouterMorceau(Morceau m) { morceaux.add(m); }
    public void ajouterAlbum(Album a) { albums.add(a); }

    @Override
    public String toString() { return "Artiste : " + nom; }
}