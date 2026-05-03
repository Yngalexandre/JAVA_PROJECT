package view.console;

import java.util.List;

public class ConsoleCompteView {

    public void afficherHistorique(List<String> historique) {
        System.out.println("===== HISTORIQUE D'ÉCOUTE =====");

        if (historique == null || historique.isEmpty()) {
            System.out.println("Aucune écoute enregistrée.");
            return;
        }

        for (int i = 0; i < historique.size(); i++) {
            System.out.println((i + 1) + ". " + historique.get(i));
        }
    }

    public void afficherInfosCompte(String login, String nomAffichage, String role) {
        System.out.println("===== INFORMATIONS DU COMPTE =====");
        System.out.println("Login : " + login);
        System.out.println("Nom affiché : " + nomAffichage);
        System.out.println("Rôle : " + role);
    }
}