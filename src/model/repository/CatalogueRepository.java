package model.repository;

import model.media.Album;
import model.media.Artiste;
import model.media.Catalogue;
import model.media.Groupe;
import model.media.Morceau;

public class CatalogueRepository {

    private Catalogue catalogue;

    public CatalogueRepository(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Morceau findMorceauById(String id) {
        for (Morceau morceau : catalogue.getMorceaux()) {
            if (morceau.getId().equalsIgnoreCase(id)) {
                return morceau;
            }
        }
        return null;
    }

    public Morceau findMorceauByTitre(String titre) {
        for (Morceau morceau : catalogue.getMorceaux()) {
            if (morceau.getTitre().equalsIgnoreCase(titre)) {
                return morceau;
            }
        }
        return null;
    }

    public Morceau findMorceauByIdOrTitre(String valeur) {
        Morceau morceau = findMorceauById(valeur);
        if (morceau != null) {
            return morceau;
        }
        return findMorceauByTitre(valeur);
    }

    public Album findAlbumById(String id) {
        for (Album album : catalogue.getAlbums()) {
            if (album.getId().equalsIgnoreCase(id)) {
                return album;
            }
        }
        return null;
    }

    public Album findAlbumByTitre(String titre) {
        for (Album album : catalogue.getAlbums()) {
            if (album.getTitre().equalsIgnoreCase(titre)) {
                return album;
            }
        }
        return null;
    }

    public Album findAlbumByIdOrTitre(String valeur) {
        Album album = findAlbumById(valeur);
        if (album != null) {
            return album;
        }
        return findAlbumByTitre(valeur);
    }

    public Artiste findArtisteById(String id) {
        for (Artiste artiste : catalogue.getArtistes()) {
            if (artiste.getId().equalsIgnoreCase(id)) {
                return artiste;
            }
        }
        return null;
    }

    public Artiste findArtisteByNom(String nom) {
        for (Artiste artiste : catalogue.getArtistes()) {
            if (artiste.getNom().equalsIgnoreCase(nom)) {
                return artiste;
            }
        }
        return null;
    }

    public Artiste findArtisteByIdOrNom(String valeur) {
        Artiste artiste = findArtisteById(valeur);
        if (artiste != null) {
            return artiste;
        }
        return findArtisteByNom(valeur);
    }

    public Groupe findGroupeById(String id) {
        for (Groupe groupe : catalogue.getGroupes()) {
            if (groupe.getId().equalsIgnoreCase(id)) {
                return groupe;
            }
        }
        return null;
    }

    public Groupe findGroupeByNom(String nom) {
        for (Groupe groupe : catalogue.getGroupes()) {
            if (groupe.getNom().equalsIgnoreCase(nom)) {
                return groupe;
            }
        }
        return null;
    }

    public Groupe findGroupeByIdOrNom(String valeur) {
        Groupe groupe = findGroupeById(valeur);
        if (groupe != null) {
            return groupe;
        }
        return findGroupeByNom(valeur);
    }
}