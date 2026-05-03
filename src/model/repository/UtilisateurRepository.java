package model.repository;

import model.user.Abonne;
import model.user.Administrateur;
import model.user.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepository {

    private List<Utilisateur> utilisateurs;

    public UtilisateurRepository(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs != null ? utilisateurs : new ArrayList<>();
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    public void supprimerUtilisateur(Utilisateur utilisateur) {
        utilisateurs.remove(utilisateur);
    }

    public Utilisateur findByLogin(String login) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getLogin().equalsIgnoreCase(login)) {
                return utilisateur;
            }
        }
        return null;
    }

    public Abonne findAbonneByLogin(String login) {
        Utilisateur utilisateur = findByLogin(login);
        if (utilisateur instanceof Abonne abonne) {
            return abonne;
        }
        return null;
    }

    public Administrateur findAdministrateurByLogin(String login) {
        Utilisateur utilisateur = findByLogin(login);
        if (utilisateur instanceof Administrateur administrateur) {
            return administrateur;
        }
        return null;
    }

    public List<Abonne> getAbonnes() {
        List<Abonne> abonnes = new ArrayList<>();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur instanceof Abonne abonne) {
                abonnes.add(abonne);
            }
        }
        return abonnes;
    }

    public List<Administrateur> getAdministrateurs() {
        List<Administrateur> administrateurs = new ArrayList<>();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur instanceof Administrateur administrateur) {
                administrateurs.add(administrateur);
            }
        }
        return administrateurs;
    }

    public int getNombreUtilisateurs() {
        return utilisateurs.size();
    }

    public boolean loginExisteDeja(String login) {
        return findByLogin(login) != null;
    }
}