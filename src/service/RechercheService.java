package service;

import model.media.Album;
import model.media.Artiste;
import model.media.Groupe;
import model.media.Morceau;
import model.repository.CatalogueRepository;

import java.util.List;

public class RechercheService {

    private final CatalogueRepository catalogueRepository;

    public RechercheService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public List<Morceau> rechercherMorceaux(String motCle) {
        return catalogueRepository.getCatalogue().rechercherMorceaux(motCle);
    }

    public List<Album> rechercherAlbums(String motCle) {
        return catalogueRepository.getCatalogue().rechercherAlbums(motCle);
    }

    public List<Artiste> rechercherArtistes(String motCle) {
        return catalogueRepository.getCatalogue().rechercherArtistes(motCle);
    }

    public List<Groupe> rechercherGroupes(String motCle) {
        return catalogueRepository.getCatalogue().rechercherGroupes(motCle);
    }
}