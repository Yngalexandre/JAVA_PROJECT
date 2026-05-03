package controller;

import model.repository.SessionRepository;
import service.AdminService;
import service.AuthService;
import service.CatalogueService;
import service.LectureService;
import service.PersistanceService;
import service.PlaylistService;
import view.console.ConsoleView;

/**
 * Contrôleur principal de l'application.
 * Il gère la boucle principale et délègue les actions
 * aux autres contrôleurs spécialisés.
 */
public class ApplicationController {

    private final ConsoleView consoleView;

    private final AuthController authController;
    private final CatalogueController catalogueController;
    private final PlaylistController playlistController;
    private final LectureController lectureController;
    private final AdminController adminController;
    private final CompteController compteController;
    private final NavigationController navigationController;
    private final RecommandationController recommandationController;

    private final PersistanceService persistanceService;
    private final SessionRepository sessionRepository;

    public ApplicationController(
            ConsoleView consoleView,
            AuthService authService,
            CatalogueService catalogueService,
            PlaylistService playlistService,
            LectureService lectureService,
            AdminController adminController,
            PersistanceService persistanceService,
            SessionRepository sessionRepository,
            RecommandationController recommandationController
    ) {
        this.consoleView = consoleView;
        this.persistanceService = persistanceService;
        this.sessionRepository = sessionRepository;
        this.recommandationController = recommandationController;

        this.authController = new AuthController(authService, consoleView);
        this.catalogueController = new CatalogueController(catalogueService, consoleView);
        this.playlistController = new PlaylistController(playlistService, consoleView);
        this.lectureController = new LectureController(lectureService, consoleView);
        this.adminController = adminController;
        this.compteController = new CompteController(authService, consoleView);
        this.navigationController = new NavigationController(consoleView);
    }

    /**
     * Point d'entrée de la boucle applicative console.
     */
    public void startApplication() {
        boolean running = true;

        while (running) {
            int choix = navigationController.afficherMenuPrincipalEtLireChoix();

            switch (choix) {
                case 1 -> {
                    authController.connecterAdministrateur();
                    if (sessionRepository.isAdministrateurConnecte()) {
                        lancerBoucleAdmin();
                    }
                }
                case 2 -> {
                    authController.connecterAbonne();
                    if (sessionRepository.isAbonneConnecte()) {
                        lancerBoucleAbonne();
                    }
                }
                case 3 -> compteController.creerCompteAbonne();
                case 4 -> lancerModeVisiteur();
                case 5 -> running = quitterApplication();
                default -> consoleView.showError("Choix invalide.");
            }
        }
    }

    private void lancerModeVisiteur() {
        authController.continuerCommeVisiteur();
        lancerBoucleVisiteur();
    }

    private void lancerBoucleVisiteur() {
        boolean actif = true;

        while (actif) {
            int choix = navigationController.afficherMenuVisiteurEtLireChoix();

            switch (choix) {
                case 1 -> catalogueController.rechercherDansCatalogue();
                case 2 -> catalogueController.afficherDetailsElementCatalogue();
                case 3 -> lectureController.lireMorceau();
                case 4 -> {
                    authController.deconnecter();
                    actif = false;
                }
                default -> consoleView.showError("Choix invalide.");
            }
        }
    }

    public void lancerBoucleAbonne() {
        boolean actif = true;

        while (actif) {
            int choix = navigationController.afficherMenuAbonneEtLireChoix();

            switch (choix) {
                case 1 -> catalogueController.rechercherDansCatalogue();
                case 2 -> catalogueController.afficherDetailsElementCatalogue();
                case 3 -> lectureController.lireMorceau();
                case 4 -> playlistController.afficherMenuPlaylists();
                case 5 -> compteController.afficherHistorique();
                case 6 -> recommandationController.afficherRecommandations();
                case 7 -> {
                    authController.deconnecter();
                    actif = false;
                }
                default -> consoleView.showError("Choix invalide.");
            }
        }
    }

    public void lancerBoucleAdmin() {
        boolean actif = true;

        while (actif) {
            int choix = navigationController.afficherMenuAdminEtLireChoix();

            switch (choix) {
                case 1 -> adminController.gererCatalogue();
                case 2 -> adminController.gererComptesAbonnes();
                case 3 -> adminController.afficherStatistiques();
                case 4 -> {
                    authController.deconnecter();
                    actif = false;
                }
                default -> consoleView.showError("Choix invalide.");
            }
        }
    }

    private boolean quitterApplication() {
        try {
            persistanceService.saveAll();
            consoleView.showMessage("Données sauvegardées. Fermeture de l'application.");
        } catch (Exception e) {
            consoleView.showError("Erreur lors de la sauvegarde : " + e.getMessage());
        }
        return false;
    }
}