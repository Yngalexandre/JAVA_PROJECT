package model.user;

import model.common.Role;

public class Administrateur extends Utilisateur {

    public Administrateur(String id, String login, String motDePasse, String nomAffichage) {
        super(id, login, motDePasse, nomAffichage, Role.ADMINISTRATEUR);
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", nomAffichage='" + nomAffichage + '\'' +
                '}';
    }
}