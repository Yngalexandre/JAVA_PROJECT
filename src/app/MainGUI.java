package app;

import app.AppInitializer;
import controller.GuiController;
import view.gui.MainFrame;

import javax.swing.*;

/**
 * Bienvenue dans JAVAZIK ! 
 * C'est le point de départ de l'application avec interface graphique.
 */
public class MainGUI {
    public static void main(String[] args) {
        // Initialisation du modèle sans lancer la console
        AppInitializer initializer = new AppInitializer();
        initializer.initForGui();

        SwingUtilities.invokeLater(() -> {
            // Création du contrôleur GUI (avec les 6 services requis)
            GuiController guiController = new GuiController(
                    initializer.getAuthService(),
                    initializer.getCatalogueService(),
                    initializer.getPlaylistService(),
                    initializer.getAdminService(),
                    initializer.getLectureService(),
                    initializer.getRecommandationService()
            );

            // Interface GUI + Injection
            MainFrame mainFrame = new MainFrame(guiController);
            guiController.setMainFrame(mainFrame);
            
            mainFrame.lancer();
        });
    }
}
