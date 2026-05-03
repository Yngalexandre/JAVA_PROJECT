package model.media;

import model.common.GenreMusical;
import model.common.Identifiable;

/**
 * Morceau : La classe de base pour représenter un titre musical.
 * Elle contient toutes les informations nécessaires (titre, artiste, durée) 
 * et gère le compteur d'écoutes pour les statistiques.
 */
public class Morceau implements Identifiable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String titre;
    private int duree; // en secondes
    private String artiste; // simplifié (nom)
    private GenreMusical genre;
    private int nombreEcoutes;

    public Morceau(String id, String titre, int duree, String artiste, GenreMusical genre) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.artiste = artiste;
        this.genre = genre;
        this.nombreEcoutes = 0;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getDuree() {
        return duree;
    }

    public String getArtiste() {
        return artiste;
    }

    public GenreMusical getGenre() {
        return genre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public int getNombreEcoutes() {
        return nombreEcoutes;
    }

    public void incrementerEcoutes() {
        this.nombreEcoutes++;
    }

    @Override
    public String toString() {
        return titre + " - " + artiste + " (" + duree + "s)";
    }
}