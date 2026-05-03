package model.playlist;

import model.media.Morceau;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.Serializable;

public class Ecoute implements Serializable {

    private Morceau morceau;
    private LocalDateTime dateHeure;

    public Ecoute(Morceau morceau) {
        this.morceau = morceau;
        this.dateHeure = LocalDateTime.now();
    }

    public Morceau getMorceau() {
        return morceau;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return morceau.getTitre() + " - " + morceau.getArtiste() + " écouté le " + dateHeure.format(formatter);
    }
}