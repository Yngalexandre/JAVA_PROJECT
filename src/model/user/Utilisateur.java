package model.user;

import model.common.Identifiable;
import model.common.Role;

public abstract class Utilisateur implements Identifiable {

    protected String id;
    protected String login;
    protected String motDePasse;
    protected String nomAffichage;
    protected Role role;

    public Utilisateur(String id, String login, String motDePasse, String nomAffichage, Role role) {
        this.id = id;
        this.login = login;
        this.motDePasse = motDePasse;
        this.nomAffichage = nomAffichage;
        this.role = role;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNomAffichage() {
        return nomAffichage;
    }

    public Role getRole() {
        return role;
    }

    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", nomAffichage='" + nomAffichage + '\'' +
                ", role=" + role +
                '}';
    }
}