package model.repository;

import model.playlist.LimiteurEcouteVisiteur;
import model.user.Abonne;
import model.user.Administrateur;
import model.user.Utilisateur;
import model.user.Visiteur;

public class SessionRepository {

    private Utilisateur utilisateurConnecte;
    private LimiteurEcouteVisiteur limiteurEcouteVisiteur;

    public SessionRepository() {
        this.utilisateurConnecte = new Visiteur();
        this.limiteurEcouteVisiteur = new LimiteurEcouteVisiteur(5);
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;

        if (utilisateurConnecte instanceof Visiteur) {
            limiteurEcouteVisiteur.reinitialiserSession();
        }
    }

    public void clearSession() {
        this.utilisateurConnecte = new Visiteur();
        this.limiteurEcouteVisiteur.reinitialiserSession();
    }

    public boolean isUtilisateurConnecte() {
        return utilisateurConnecte != null;
    }

    public boolean isVisiteurConnecte() {
        return utilisateurConnecte instanceof Visiteur;
    }

    public boolean isAbonneConnecte() {
        return utilisateurConnecte instanceof Abonne;
    }

    public boolean isAdministrateurConnecte() {
        return utilisateurConnecte instanceof Administrateur;
    }

    public Abonne getAbonneConnecte() {
        if (utilisateurConnecte instanceof Abonne abonne) {
            return abonne;
        }
        return null;
    }

    public Administrateur getAdministrateurConnecte() {
        if (utilisateurConnecte instanceof Administrateur administrateur) {
            return administrateur;
        }
        return null;
    }

    public LimiteurEcouteVisiteur getLimiteurEcouteVisiteur() {
        return limiteurEcouteVisiteur;
    }
}