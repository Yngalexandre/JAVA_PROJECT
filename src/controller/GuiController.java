package controller;

import java.util.List;
import model.media.Morceau;
import model.user.Utilisateur;
import service.AdminService;
import service.AuthService;
import service.CatalogueService;
import service.PlaylistService;
import service.LectureService;
import service.RecommandationService;
import model.user.Abonne;
import view.gui.MainFrame;

import javax.swing.JOptionPane;

/**
 * GuiController : C'est le "cerveau" de l'interface graphique.
 * Il fait le lien entre ce que l'utilisateur voit (la Vue) et les données (le Modèle).
 * Toutes les actions importantes (connexion, lecture, playlists) passent par ici.
 */
public class GuiController {

    private final AuthService authService;
    private final CatalogueService catalogueService;
    private final PlaylistService playlistService;
    private final AdminService adminService;
    private final LectureService lectureService;
    private final RecommandationService recommandationService;
    
    private MainFrame mainFrame;

    public GuiController(AuthService authService, CatalogueService catalogueService, 
                         PlaylistService playlistService, AdminService adminService,
                         LectureService lectureService, RecommandationService recommandationService) {
        this.authService = authService;
        this.catalogueService = catalogueService;
        this.playlistService = playlistService;
        this.adminService = adminService;
        this.lectureService = lectureService;
        this.recommandationService = recommandationService;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public boolean connecter(String login, String password) {
        try {
            // On vérifie d'abord si c'est l'admin (identifiant spécial dans le sujet)
            if ("admin".equalsIgnoreCase(login)) {
                authService.loginAdministrateur(login, password);
                showMessage("Bienvenue Administrateur !", false);
                mainFrame.refreshNavigation(); // On débloque l'onglet Administration
                mainFrame.showPanel("ADMIN"); 
                return true;
            } else {
                // Sinon on tente une connexion classique en tant qu'Abonné
                authService.loginAbonne(login, password);
                Utilisateur u = authService.getUtilisateurConnecte();
                showMessage("Bienvenue, " + u.getNomAffichage() + " !", false);
                mainFrame.refreshNavigation();
                mainFrame.showPanel("HOME"); // On redirige vers l'accueil
                return true;
            }
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
            return false;
        }
    }
    
    /**
     * Déconnecte l'utilisateur actuel.
     */
    public void deconnecter() {
        authService.logout();
        mainFrame.refreshNavigation();
        mainFrame.showPanel("LOGIN");
        showMessage("Vous avez été déconnecté.", false);
    }

    /**
     * Affiche une boîte de dialogue stylisée.
     */
    public void showMessage(String message, boolean isError) {
        int messageType = isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(mainFrame, message, isError ? "Erreur" : "Information", messageType);
    }
    
    /**
     * Vérifie si un utilisateur est actuellement connecté.
     */
    public boolean isConnected() {
        Utilisateur u = authService.getUtilisateurConnecte();
        return u != null && !(u instanceof model.user.Visiteur);
    }

    /**
     * Tente de créer un nouveau compte abonné et le connecte directement.
     */
    public boolean inscrire(String login, String password, String nomAffichage) {
        try {
            authService.registerAbonne(login, password, nomAffichage);
            // Si inscription OK, on connecte directement l'utilisateur
            authService.loginAbonne(login, password);
            showMessage("Bienvenue, " + nomAffichage + " ! Votre compte a été créé.", false);
            mainFrame.refreshNavigation();
            mainFrame.showPanel("HOME");
            return true;
        } catch (Exception e) {
            showMessage("Erreur: " + e.getMessage(), true);
            return false;
        }
    }

    /**
     * Méthodes de relais pour le Catalogue
     */
    public List<Morceau> getTousLesMorceaux() {
        return catalogueService.getTousLesMorceaux();
    }

    public List<Morceau> getMorceauxAleatoires(int limite) {
        List<Morceau> tous = getTousLesMorceaux();
        if (tous.isEmpty()) return List.of();
        java.util.List<Morceau> copy = new java.util.ArrayList<>(tous);
        java.util.Collections.shuffle(copy);
        return copy.stream().limit(limite).toList();
    }
    
    public void ecouterMorceau(Morceau morceau) {
        try {
            lectureService.lireMorceau(morceau.getTitre());
            mainFrame.updatePlayer(morceau.getTitre(), morceau.getArtiste(), morceau.getDuree());
        } catch (Exception e) {
            showMessage("Impossible de lire : " + e.getMessage(), true);
        }
    }

    /**
     * Méthodes de relais pour les Playlists
     */
    public List<String> getNomsPlaylists() {
        if (!isConnected() || isAdministrateur()) return List.of(); // L'admin n'a pas de playlists
        return playlistService.getNomsPlaylistsUtilisateurConnecte();
    }

    public List<String> getHistorique() {
        try {
            return authService.getHistoriqueUtilisateurConnecte();
        } catch (Exception e) {
            return List.of(); // Visiteur ou admin : pas d'historique
        }
    }

    public void creerPlaylist(String nom) {
        try {
            playlistService.creerPlaylist(nom);
            showMessage("Playlist '" + nom + "' créée avec succès.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void renommerPlaylist(String ancienNom, String nouveauNom) {
        try {
            playlistService.renommerPlaylist(ancienNom, nouveauNom);
            showMessage("Playlist renommée.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void supprimerPlaylist(String nom) {
        try {
            playlistService.supprimerPlaylist(nom);
            showMessage("Playlist supprimée.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void retirerMorceauDePlaylist(Morceau morceau, String nomPlaylist) {
        try {
            playlistService.retirerMorceau(nomPlaylist, morceau.getTitre());
            showMessage("Morceau retiré de la playlist.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }
    
    public List<Morceau> getRecommandations() {
        try {
            Abonne abonne = authService.getSessionRepository().getAbonneConnecte();
            if (abonne == null) return List.of();
            return recommandationService.recommanderPour(abonne, 3);
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Morceau> getMorceauxDePlaylist(String nomPlaylist) {
        try {
            model.user.Abonne ab = authService.getSessionRepository().getAbonneConnecte();
            if (ab != null) {
                model.playlist.Playlist p = ab.trouverPlaylistParNom(nomPlaylist);
                if (p != null) return p.getMorceaux();
            }
        } catch (Exception ignored) {}
        return List.of(); // Vide si non trouvé ou non connecté
    }

    public void ajouterMorceauAPlaylist(Morceau morceau, String nomPlaylist) {
        try {
            playlistService.ajouterMorceau(nomPlaylist, morceau.getTitre());
            showMessage("Morceau ajouté à la playlist '" + nomPlaylist + "'.", false);
        } catch (Exception e) {
            showMessage("Impossible d'ajouter à la playlist : " + e.getMessage(), true);
        }
    }

    /**
     * Méthodes de relais pour l'Administration
     */
    public boolean isAdministrateur() {
        return authService.getSessionRepository().isAdministrateurConnecte();
    }

    public void ajouterMorceauAdmin(String titre, int duree, String artiste) {
        try {
            adminService.ajouterMorceau(titre, duree, artiste);
            showMessage("Morceau ajouté avec succès.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void supprimerMorceauAdmin(String identifiant) {
        try {
            adminService.supprimerMorceau(identifiant);
            showMessage("Morceau supprimé avec succès.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void suspendreAbonne(String login) {
        try {
            adminService.suspendreCompteAbonne(login);
            showMessage("Abonné suspendu avec succès.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void activerAbonne(String login) {
        try {
            adminService.activerCompteAbonne(login);
            showMessage("Abonné réactivé avec succès.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public void supprimerAbonne(String login) {
        try {
            adminService.supprimerCompteAbonne(login);
            showMessage("Abonné supprimé définitivement.", false);
        } catch (Exception e) {
            showMessage("Erreur : " + e.getMessage(), true);
        }
    }

    public String getStatistiques() {
        try {
            return adminService.getStatistiquesSimples();
        } catch (Exception e) {
            return "Erreur lors du chargement des statistiques.";
        }
    }
}
