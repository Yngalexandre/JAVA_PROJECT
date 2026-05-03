package model.media;

import model.common.Identifiable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Identifiable {
    private String id;
    private String titre;
    private String artiste;
    private List<Morceau> morceaux = new ArrayList<>();

    public Album(String id, String titre, String artiste) {
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
    }

    @Override
    public String getId() { return id; }
    public String getTitre() { return titre; }
    public String getArtiste() { return artiste; }
    public List<Morceau> getMorceaux() { return morceaux; }

    public void ajouterMorceau(Morceau m) { morceaux.add(m); }

    @Override
    public String toString() { return "Album : " + titre + " - " + artiste; }
}