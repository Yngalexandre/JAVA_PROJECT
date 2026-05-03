package controller;

import model.media.Morceau;
import model.repository.SessionRepository;
import model.user.Abonne;
import service.RecommandationService;
import view.console.ConsoleView;

import java.util.List;

/**
 * Gère les interactions liées aux recommandations musicales.
 */
public class RecommandationController {

    private final RecommandationService recommandationService;
    private final ConsoleView consoleView;
    private final SessionRepository sessionRepository;

    public RecommandationController(
            RecommandationService recommandationService,
            ConsoleView consoleView,
            SessionRepository sessionRepository
    ) {
        this.recommandationService = recommandationService;
        this.consoleView = consoleView;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Affiche les recommandations pour l'abonné connecté.
     */
    public void afficherRecommandations() {
        Abonne abonne = sessionRepository.getAbonneConnecte();

        if (abonne == null) {
            consoleView.showError("Vous devez être connecté en tant qu'abonné pour voir vos recommandations.");
            return;
        }

        consoleView.showMessage("Analyse de votre historique d'écoute pour générer des recommandations...");

        try {
            List<Morceau> recommandations = recommandationService.recommanderPour(abonne, 5);

            if (recommandations.isEmpty()) {
                consoleView.showMessage("Nous n'avons pas encore assez de données dans votre historique pour vous recommander des titres spécifiques.");
            } else {
                consoleView.showMessage("===== VOS RECOMMANDATIONS PERSONNALISÉES =====");
                for (int i = 0; i < recommandations.size(); i++) {
                    consoleView.showMessage((i + 1) + ". " + recommandations.get(i).toString());
                }
            }
        } catch (Exception e) {
            consoleView.showError("Erreur lors de la génération des recommandations : " + e.getMessage());
        }
    }
}
