package model.playlist;

import model.media.Morceau;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class HistoriqueEcoute implements Serializable {

    private List<Ecoute> ecoutes;

    public HistoriqueEcoute() {
        this.ecoutes = new ArrayList<>();
    }

    public void ajouterEcoute(Morceau morceau) {
        ecoutes.add(new Ecoute(morceau));
    }

    public List<Ecoute> getEcoutes() {
        return ecoutes;
    }

    public List<String> getResumeEcoutes() {
        List<String> resume = new ArrayList<>();
        for (Ecoute ecoute : ecoutes) {
            resume.add(ecoute.toString());
        }
        return resume;
    }

    public boolean estVide() {
        return ecoutes.isEmpty();
    }

    @Override
    public String toString() {
        if (ecoutes.isEmpty()) {
            return "Aucune écoute enregistrée.";
        }

        StringBuilder sb = new StringBuilder();
        for (Ecoute ecoute : ecoutes) {
            sb.append("- ").append(ecoute).append("\n");
        }
        return sb.toString();
    }
}