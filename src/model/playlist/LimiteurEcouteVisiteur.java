package model.playlist;

public class LimiteurEcouteVisiteur {

    private final int limiteMax;
    private int nombreEcoutesCourantes;

    public LimiteurEcouteVisiteur(int limiteMax) {
        this.limiteMax = limiteMax;
        this.nombreEcoutesCourantes = 0;
    }

    public boolean peutEncoreEcouter() {
        return nombreEcoutesCourantes < limiteMax;
    }

    public void incrementerEcoutes() {
        if (peutEncoreEcouter()) {
            nombreEcoutesCourantes++;
        }
    }

    public void reinitialiserSession() {
        nombreEcoutesCourantes = 0;
    }

    public int getLimiteMax() {
        return limiteMax;
    }

    public int getNombreEcoutesCourantes() {
        return nombreEcoutesCourantes;
    }

    public int getNombreEcoutesRestantes() {
        return limiteMax - nombreEcoutesCourantes;
    }

    @Override
    public String toString() {
        return "Écoutes visiteur : " + nombreEcoutesCourantes + "/" + limiteMax;
    }
}