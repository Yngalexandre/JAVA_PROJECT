package service;

import model.common.EtatCompte;
import model.common.GenreMusical;
import model.media.Album;
import model.media.Artiste;
import model.media.Groupe;
import model.media.Morceau;
import model.repository.CatalogueRepository;
import model.repository.UtilisateurRepository;
import model.user.Abonne;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AdminService : Gère les fonctionnalités administratives complètes.
 */
public class AdminService {

    private final CatalogueRepository catalogueRepository;
    private final UtilisateurRepository utilisateurRepository;

    public AdminService(CatalogueRepository catalogueRepository, UtilisateurRepository utilisateurRepository) {
        this.catalogueRepository = catalogueRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // -- MORCEAUX --
    public void ajouterMorceau(String titre, int duree, String artisteOuGroupe) {
        if (titre == null || titre.isBlank()) throw new IllegalArgumentException("Titre requis.");
        Morceau morceau = new Morceau(UUID.randomUUID().toString(), titre, duree, artisteOuGroupe, GenreMusical.AUTRE);
        catalogueRepository.getCatalogue().ajouterMorceau(morceau);
    }

    public void supprimerMorceau(String idOrTitre) {
        Morceau m = catalogueRepository.findMorceauByIdOrTitre(idOrTitre);
        if (m != null) catalogueRepository.getCatalogue().getMorceaux().remove(m);
    }

    // -- ALBUMS --
    public void ajouterAlbum(String titre, String artisteOuGroupe) {
        if (titre == null || titre.isBlank()) throw new IllegalArgumentException("Titre requis.");
        Album album = new Album(UUID.randomUUID().toString(), titre, artisteOuGroupe);
        catalogueRepository.getCatalogue().ajouterAlbum(album);
    }

    public void supprimerAlbum(String idOrTitre) {
        Album a = catalogueRepository.findAlbumByIdOrTitre(idOrTitre);
        if (a != null) catalogueRepository.getCatalogue().getAlbums().remove(a);
    }

    // -- ARTISTES / GROUPES --
    public void ajouterArtiste(String nom) {
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("Nom requis.");
        catalogueRepository.getCatalogue().ajouterArtiste(new Artiste(UUID.randomUUID().toString(), nom));
    }

    public void supprimerArtiste(String nom) {
        Artiste a = catalogueRepository.findArtisteByNom(nom);
        if (a != null) catalogueRepository.getCatalogue().getArtistes().remove(a);
    }

    public void ajouterGroupe(String nom) {
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("Nom requis.");
        catalogueRepository.getCatalogue().ajouterGroupe(new Groupe(UUID.randomUUID().toString(), nom));
    }

    public void supprimerGroupe(String nom) {
        Groupe g = catalogueRepository.findGroupeByNom(nom);
        if (g != null) catalogueRepository.getCatalogue().getGroupes().remove(g);
    }

    // -- ABONNÉS --
    public void suspendreCompteAbonne(String login) {
        Abonne abonne = utilisateurRepository.findAbonneByLogin(login);
        if (abonne != null) abonne.setEtatCompte(EtatCompte.SUSPENDU);
    }

    public void activerCompteAbonne(String login) {
        Abonne abonne = utilisateurRepository.findAbonneByLogin(login);
        if (abonne != null) abonne.setEtatCompte(EtatCompte.ACTIF);
    }

    public void supprimerCompteAbonne(String login) {
        Abonne abonne = utilisateurRepository.findAbonneByLogin(login);
        if (abonne != null) abonne.setEtatCompte(EtatCompte.SUPPRIME);
    }

    // -- STATISTIQUES --
    public String getTopMorceauxStats(int n) {
        return catalogueRepository.getCatalogue().getMorceaux().stream()
                .sorted((m1, m2) -> Integer.compare(m2.getNombreEcoutes(), m1.getNombreEcoutes()))
                .limit(n)
                .map(m -> m.getTitre() + " (" + m.getNombreEcoutes() + " écoutes)")
                .collect(Collectors.joining("\n"));
    }

    public String getStatistiquesSimples() {
        int nbUtilisateurs = utilisateurRepository.getNombreUtilisateurs();
        int nbAbonnes = utilisateurRepository.getAbonnes().size();
        int nbMorceaux = catalogueRepository.getCatalogue().getMorceaux().size();
        long totalEcoutes = catalogueRepository.getCatalogue().getMorceaux().stream()
                .mapToLong(Morceau::getNombreEcoutes).sum();

        StringBuilder sbUsers = new StringBuilder("\n--- LISTE DES ABONNÉS ---\n");
        for (Abonne a : utilisateurRepository.getAbonnes()) {
            sbUsers.append("- ").append(a.getLogin()).append(" [").append(a.getEtatCompte()).append("]\n");
        }

        return """
                ===== STATISTIQUES =====
                Nombre d'utilisateurs : %d
                Nombre d'abonnés : %d
                Nombre de morceaux : %d
                Nombre total d'écoutes : %d
                
                %s
                
                --- TOP 3 DES MORCEAUX ---
                %s
                """.formatted(
                nbUtilisateurs, nbAbonnes, nbMorceaux, totalEcoutes, sbUsers.toString(), getTopMorceauxStats(3)
        );
    }
}