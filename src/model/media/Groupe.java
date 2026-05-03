package model.media;

import model.common.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Groupe implements Identifiable {

    private String id;
    private String nom;
    private List<Artiste> membres;
    private List<Album> albums;

    public Groupe(String id, String nom) {
        this.id = id;
        this.nom = nom;
        this.membres = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public List<Artiste> getMembres() {
        return membres;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void ajouterMembre(Artiste artiste) {
        membres.add(artiste);
    }

    public void ajouterAlbum(Album album) {
        albums.add(album);
    }

    @Override
    public String toString() {
        return "Groupe : " + nom;
    }
}