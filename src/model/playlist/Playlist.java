package model.playlist;

import model.media.Morceau;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class Playlist implements Serializable {

    private String nom;
    private List<Morceau> morceaux;

    public Playlist(String nom) {
        this.nom = nom;
        this.morceaux = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public void renommer(String nouveauNom) {
        this.nom = nouveauNom;
    }

    public void ajouterMorceau(Morceau morceau) {
        if (!contientMorceau(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    public boolean contientMorceau(Morceau morceau) {
        for (Morceau m : morceaux) {
            if (m.getId().equalsIgnoreCase(morceau.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean estVide() {
        return morceaux.isEmpty();
    }

    public List<String> getResumeMorceaux() {
        List<String> resume = new ArrayList<>();
        for (Morceau morceau : morceaux) {
            resume.add(morceau.toString());
        }
        return resume;
    }

    @Override
    public String toString() {
        return "Playlist : " + nom + " (" + morceaux.size() + " morceau(x))";
    }
}