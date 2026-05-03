package model.user;

import model.common.Role;

public class Visiteur extends Utilisateur {

    public Visiteur() {
        super("VISITEUR", "visiteur", "", "Visiteur", Role.VISITEUR);
    }
}