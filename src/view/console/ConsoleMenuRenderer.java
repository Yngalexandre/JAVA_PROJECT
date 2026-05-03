package view.console;

public class ConsoleMenuRenderer {

    public void afficherMenuPrincipal() {
        System.out.println("""
                =========================
                       JAVAZIC
                =========================
                1. Se connecter en tant qu'administrateur
                2. Se connecter en tant que client
                3. Créer un compte client
                4. Continuer en tant que simple visiteur
                5. Quitter
                """);
    }

    public void afficherMenuVisiteur() {
        System.out.println("""
                ===== MENU VISITEUR =====
                1. Rechercher dans le catalogue
                2. Consulter les détails d'un élément
                3. Écouter un morceau
                4. Retour au menu principal
                """);
    }

    public void afficherMenuAbonne() {
        System.out.println("""
                ===== MENU ABONNÉ =====
                1. Rechercher dans le catalogue
                2. Consulter les détails d'un élément
                3. Écouter un morceau
                4. Gérer mes playlists
                5. Consulter mon historique
                6. Déconnexion
                """);
    }

    public void afficherMenuAdmin() {
        System.out.println("""
                ===== MENU ADMIN =====
                1. Gérer le catalogue musical
                2. Gérer les comptes abonnés
                3. Consulter les statistiques
                4. Déconnexion
                """);
    }

    public void afficherMenuPlaylists() {
        System.out.println("""
                ===== GESTION DES PLAYLISTS =====
                1. Voir mes playlists
                2. Créer une playlist
                3. Renommer une playlist
                4. Supprimer une playlist
                5. Ajouter un morceau à une playlist
                6. Retirer un morceau d'une playlist
                7. Retour
                """);
    }

    public void afficherMenuGestionCatalogue() {
        System.out.println("""
                ===== GESTION DU CATALOGUE =====
                1. Ajouter un morceau
                2. Supprimer un morceau
                3. Ajouter un album
                4. Supprimer un album
                5. Ajouter un artiste
                6. Supprimer un artiste
                7. Ajouter un groupe
                8. Supprimer un groupe
                9. Retour
                """);
    }

    public void afficherMenuGestionAbonnes() {
        System.out.println("""
                ===== GESTION DES ABONNÉS =====
                1. Suspendre un compte
                2. Supprimer un compte
                3. Retour
                """);
    }

    public void afficherMenuRechercheCatalogue() {
        System.out.println("""
                ===== RECHERCHE CATALOGUE =====
                1. Morceau
                2. Album
                3. Artiste
                4. Groupe
                """);
    }

    public void afficherMenuDetailsCatalogue() {
        System.out.println("""
                ===== DÉTAILS CATALOGUE =====
                1. Morceau
                2. Album
                3. Artiste
                4. Groupe
                """);
    }
}