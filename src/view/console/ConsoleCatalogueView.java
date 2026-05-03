package view.console;

import java.util.List;

public class ConsoleCatalogueView {

    public void afficherResultatsRecherche(List<String> resultats) {
        System.out.println("===== RÉSULTATS =====");

        if (resultats == null || resultats.isEmpty()) {
            System.out.println("Aucun résultat trouvé.");
            return;
        }

        for (int i = 0; i < resultats.size(); i++) {
            System.out.println((i + 1) + ". " + resultats.get(i));
        }
    }

    public void afficherDetails(String details) {
        System.out.println(details);
    }
}