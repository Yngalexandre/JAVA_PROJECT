package service;

import model.common.GenreMusical;
import model.media.Album;
import model.media.Artiste;
import model.media.Catalogue;
import model.media.Groupe;
import model.media.Morceau;
import model.repository.CatalogueRepository;

public class ImportDonneesService {

    private final CatalogueRepository catalogueRepository;

    public ImportDonneesService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * Remplit le catalogue avec quelques données de démonstration
     * si le catalogue est vide.
     */
    public void chargerDonneesParDefautSiNecessaire() {
        Catalogue catalogue = catalogueRepository.getCatalogue();

        if (!catalogue.getMorceaux().isEmpty()
                || !catalogue.getAlbums().isEmpty()
                || !catalogue.getArtistes().isEmpty()
                || !catalogue.getGroupes().isEmpty()) {
            return;
        }

        Artiste daftPunkMember = new Artiste("art-1", "Thomas Bangalter");
        Groupe daftPunk = new Groupe("grp-1", "Daft Punk");
        daftPunk.ajouterMembre(daftPunkMember);

        Artiste stromae = new Artiste("art-2", "Stromae");

        Album randomAccessMemories = new Album("alb-1", "Random Access Memories", "Daft Punk");
        Album racineCarree = new Album("alb-2", "Racine Carrée", "Stromae");

        Morceau getLucky = new Morceau("mor-1", "Get Lucky", 248, "Daft Punk", GenreMusical.ELECTRO);
        Morceau instantCrush = new Morceau("mor-2", "Instant Crush", 337, "Daft Punk", GenreMusical.ELECTRO);
        Morceau formidable = new Morceau("mor-3", "Formidable", 214, "Stromae", GenreMusical.POP);
        Morceau papaoutai = new Morceau("mor-4", "Papaoutai", 232, "Stromae", GenreMusical.POP);

        randomAccessMemories.ajouterMorceau(getLucky);
        randomAccessMemories.ajouterMorceau(instantCrush);

        racineCarree.ajouterMorceau(formidable);
        racineCarree.ajouterMorceau(papaoutai);

        daftPunk.ajouterAlbum(randomAccessMemories);

        stromae.ajouterAlbum(racineCarree);
        stromae.ajouterMorceau(formidable);
        stromae.ajouterMorceau(papaoutai);

        catalogue.ajouterGroupe(daftPunk);
        catalogue.ajouterArtiste(daftPunkMember);
        catalogue.ajouterArtiste(stromae);
        catalogue.ajouterAlbum(randomAccessMemories);
        catalogue.ajouterAlbum(racineCarree);
        catalogue.ajouterMorceau(getLucky);
        catalogue.ajouterMorceau(instantCrush);
        catalogue.ajouterMorceau(formidable);
        catalogue.ajouterMorceau(papaoutai);
    }
}