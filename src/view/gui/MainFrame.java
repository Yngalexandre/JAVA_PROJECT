package view.gui;

import controller.GuiController;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame : C'est la fenêtre principale de JAVAZIK.
 * Elle organise l'affichage grâce au CardLayout (système d'onglets)
 * et assure la présence constante du lecteur audio en bas.
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainContainer;
    private final NavigationBar navigationBar;
    private final GuiController guiController;
    
    private final HomePanel homePanel;
    private final CataloguePanel cataloguePanel;
    private final PlaylistPanel playlistPanel;
    private final AdminPanel adminPanel;
    private final HistoriquePanel historiquePanel;
    private final PlayerBar playerBar;

    public MainFrame(GuiController guiController) {
        this.guiController = guiController;
        setTitle("JAVAZIC - Plateforme Musicale");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG_DARK);

        // Retirer les bordures par défaut si possible
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignorer, on garde notre propre style
        }

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.setBackground(Theme.BG_PANEL); // Séparation visuelle de la nav
        
        // Navigation latérale
        navigationBar = new NavigationBar(this, guiController);

        // --- Création des sous-panels ---
        homePanel = new HomePanel(guiController);
        cataloguePanel = new CataloguePanel(guiController);
        playlistPanel = new PlaylistPanel(guiController);
        adminPanel = new AdminPanel(guiController);
        historiquePanel = new HistoriquePanel(guiController);
        playerBar = new PlayerBar();

        // --- Ajout des Panels au CardLayout ---
        mainContainer.add(homePanel, "HOME");
        mainContainer.add(new LoginPanel(guiController), "LOGIN");
        mainContainer.add(cataloguePanel, "CATALOGUE");
        mainContainer.add(playlistPanel, "PLAYLISTS");
        mainContainer.add(adminPanel, "ADMIN");
        mainContainer.add(historiquePanel, "HISTORIQUE");

        // Assemblage
        setLayout(new BorderLayout());
        add(navigationBar, BorderLayout.WEST);
        add(mainContainer, BorderLayout.CENTER);
        add(playerBar, BorderLayout.SOUTH);
        
        // Afficher l'accueil par défaut
        showPanel("HOME");
    }

    /**
     * Permet à la barre de navigation de changer l'écran affiché.
     */
    public void showPanel(String name) {
        if ("HOME".equals(name)) homePanel.refresh();
        if ("CATALOGUE".equals(name)) cataloguePanel.refresh();
        if ("PLAYLISTS".equals(name)) playlistPanel.refresh();
        if ("ADMIN".equals(name)) adminPanel.refresh();
        if ("HISTORIQUE".equals(name)) historiquePanel.refresh();
        cardLayout.show(mainContainer, name);
    }
    
    public void refreshNavigation() {
        navigationBar.refresh();
        playlistPanel.refresh(); // Pour rafraîchir les playlists après connexion
    }

    public void updatePlayer(String track, String artist, int duree) {
        playerBar.setTrackInfo(track, artist, duree);
    }

    public void lancer() {
        setVisible(true);
    }
}
