package app;

import controller.AdminController;
import controller.ApplicationController;
import model.media.Catalogue;
import model.repository.CatalogueRepository;
import model.repository.SessionRepository;
import model.repository.UtilisateurRepository;
import persistence.DataLoader;
import service.AdminService;
import service.AuthService;
import service.CatalogueService;
import service.PersistanceService;
import service.PlaylistService;
import service.LectureService;
import service.RecommandationService;
import model.stats.StatistiquesService;
import view.console.ConsoleView;
import service.ImportDonneesService;
import controller.RecommandationController;

/**
 * AppInitializer : Prépare l'application au démarrage.
 */
public class AppInitializer {

    private CatalogueRepository catalogueRepository;
    private UtilisateurRepository utilisateurRepository;
    private SessionRepository sessionRepository;

    private CatalogueService catalogueService;
    private AuthService authService;
    private PlaylistService playlistService;
    private LectureService lectureService;
    private AdminService adminService;
    private PersistanceService persistanceService;
    private RecommandationService recommandationService;
    private StatistiquesService statistiquesService;

    private ApplicationController applicationController;
    private ConsoleView consoleView;
    private ImportDonneesService importDonneesService;

    public void start() {
        initializeRepositories();
        initializeServices();
        initializeViewsAndControllers();
        registerShutdownHook();
        applicationController.startApplication();
    }

    public void initForGui() {
        initializeRepositories();
        initializeServices();
        registerShutdownHook();
    }

    public AuthService getAuthService() { return authService; }
    public CatalogueService getCatalogueService() { return catalogueService; }
    public PlaylistService getPlaylistService() { return playlistService; }
    public AdminService getAdminService() { return adminService; }
    public LectureService getLectureService() { return lectureService; }
    public RecommandationService getRecommandationService() { return recommandationService; }
    public SessionRepository getSessionRepository() { return sessionRepository; }

    private void initializeRepositories() {
        DataLoader dataLoader = new DataLoader();
        Catalogue catalogue = dataLoader.loadCatalogue();
        this.catalogueRepository = new CatalogueRepository(catalogue);
        this.utilisateurRepository = new UtilisateurRepository(dataLoader.loadUtilisateurs());
        this.sessionRepository = new SessionRepository();
    }

    private void initializeServices() {
        this.catalogueService = new CatalogueService(catalogueRepository);
        this.authService = new AuthService(utilisateurRepository, sessionRepository);
        this.playlistService = new PlaylistService(utilisateurRepository, sessionRepository);
        this.playlistService.setCatalogueRepository(catalogueRepository);
        this.lectureService = new LectureService(catalogueRepository, utilisateurRepository, sessionRepository);
        this.adminService = new AdminService(catalogueRepository, utilisateurRepository);
        this.persistanceService = new PersistanceService(catalogueRepository, utilisateurRepository);
        this.importDonneesService = new ImportDonneesService(catalogueRepository);
        this.recommandationService = new RecommandationService(catalogueRepository);
        this.statistiquesService = new StatistiquesService(catalogueRepository);
        this.importDonneesService.chargerDonneesParDefautSiNecessaire();
    }

    private void initializeViewsAndControllers() {
        this.consoleView = new ConsoleView();
        this.applicationController = new ApplicationController(
                consoleView, authService, catalogueService, playlistService, lectureService,
                new AdminController(adminService, statistiquesService, consoleView),
                persistanceService, sessionRepository,
                new RecommandationController(recommandationService, consoleView, sessionRepository)
        );
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                persistanceService.saveAll();
            } catch (Exception ignored) {}
        }));
    }
}