package service;

import model.media.Album;
import model.media.Artiste;
import model.media.Groupe;
import model.media.Morceau;
import model.repository.CatalogueRepository;

import java.util.ArrayList;
import java.util.List;

public class CatalogueService {

    private final CatalogueRepository catalogueRepository;

    public CatalogueService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    // ===== RECUPERATION =====
    
    public List<Morceau> getTousLesMorceaux() {
        return catalogueRepository.getCatalogue().getMorceaux();
    }

    // ===== RECHERCHE =====

    public List<String> rechercherMorceaux(String motCle) {
        List<Morceau> morceaux = catalogueRepository.getCatalogue().rechercherMorceaux(motCle);
        return convertirMorceauxEnTexte(morceaux);
    }

    public List<String> rechercherAlbums(String motCle) {
        List<Album> albums = catalogueRepository.getCatalogue().rechercherAlbums(motCle);
        return convertirAlbumsEnTexte(albums);
    }

    public List<String> rechercherArtistes(String motCle) {
        List<Artiste> artistes = catalogueRepository.getCatalogue().rechercherArtistes(motCle);
        return convertirArtistesEnTexte(artistes);
    }

    public List<String> rechercherGroupes(String motCle) {
        List<Groupe> groupes = catalogueRepository.getCatalogue().rechercherGroupes(motCle);
        return convertirGroupesEnTexte(groupes);
    }

    // ===== DETAILS =====

    public String getDetailsMorceau(String idOuNom) {
        Morceau morceau = catalogueRepository.findMorceauByIdOrTitre(idOuNom);

        if (morceau == null) {
            throw new IllegalArgumentException("Morceau introuvable.");
        }

        return """
                ===== DÉTAIL MORCEAU =====
                ID : %s
                Titre : %s
                Artiste : %s
                Genre : %s
                Durée : %d secondes
                Nombre d'écoutes : %d
                """.formatted(
                morceau.getId(),
                morceau.getTitre(),
                morceau.getArtiste(),
                morceau.getGenre(),
                morceau.getDuree(),
                morceau.getNombreEcoutes()
        );
    }

    public String getDetailsAlbum(String idOuNom) {
        Album album = catalogueRepository.findAlbumByIdOrTitre(idOuNom);

        if (album == null) {
            throw new IllegalArgumentException("Album introuvable.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== DÉTAIL ALBUM =====\n");
        sb.append("ID : ").append(album.getId()).append("\n");
        sb.append("Titre : ").append(album.getTitre()).append("\n");
        sb.append("Artiste : ").append(album.getArtiste()).append("\n");
        sb.append("Morceaux :\n");

        if (album.getMorceaux().isEmpty()) {
            sb.append("- Aucun morceau\n");
        } else {
            for (Morceau morceau : album.getMorceaux()) {
                sb.append("- ").append(morceau).append("\n");
            }
        }

        return sb.toString();
    }

    public String getDetailsArtiste(String idOuNom) {
        Artiste artiste = catalogueRepository.findArtisteByIdOrNom(idOuNom);

        if (artiste == null) {
            throw new IllegalArgumentException("Artiste introuvable.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== DÉTAIL ARTISTE =====\n");
        sb.append("ID : ").append(artiste.getId()).append("\n");
        sb.append("Nom : ").append(artiste.getNom()).append("\n");
        sb.append("Albums :\n");

        if (artiste.getAlbums().isEmpty()) {
            sb.append("- Aucun album\n");
        } else {
            for (Album album : artiste.getAlbums()) {
                sb.append("- ").append(album.getTitre()).append("\n");
            }
        }

        sb.append("Morceaux :\n");
        if (artiste.getMorceaux().isEmpty()) {
            sb.append("- Aucun morceau\n");
        } else {
            for (Morceau morceau : artiste.getMorceaux()) {
                sb.append("- ").append(morceau.getTitre()).append("\n");
            }
        }

        return sb.toString();
    }

    public String getDetailsGroupe(String idOuNom) {
        Groupe groupe = catalogueRepository.findGroupeByIdOrNom(idOuNom);

        if (groupe == null) {
            throw new IllegalArgumentException("Groupe introuvable.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== DÉTAIL GROUPE =====\n");
        sb.append("ID : ").append(groupe.getId()).append("\n");
        sb.append("Nom : ").append(groupe.getNom()).append("\n");
        sb.append("Membres :\n");

        if (groupe.getMembres().isEmpty()) {
            sb.append("- Aucun membre\n");
        } else {
            for (Artiste artiste : groupe.getMembres()) {
                sb.append("- ").append(artiste.getNom()).append("\n");
            }
        }

        sb.append("Albums :\n");
        if (groupe.getAlbums().isEmpty()) {
            sb.append("- Aucun album\n");
        } else {
            for (Album album : groupe.getAlbums()) {
                sb.append("- ").append(album.getTitre()).append("\n");
            }
        }

        return sb.toString();
    }

    // ===== CONVERSION =====

    private List<String> convertirMorceauxEnTexte(List<Morceau> morceaux) {
        List<String> resultats = new ArrayList<>();
        for (Morceau morceau : morceaux) {
            resultats.add(morceau.toString());
        }
        return resultats;
    }

    private List<String> convertirAlbumsEnTexte(List<Album> albums) {
        List<String> resultats = new ArrayList<>();
        for (Album album : albums) {
            resultats.add(album.toString());
        }
        return resultats;
    }

    private List<String> convertirArtistesEnTexte(List<Artiste> artistes) {
        List<String> resultats = new ArrayList<>();
        for (Artiste artiste : artistes) {
            resultats.add(artiste.toString());
        }
        return resultats;
    }

    private List<String> convertirGroupesEnTexte(List<Groupe> groupes) {
        List<String> resultats = new ArrayList<>();
        for (Groupe groupe : groupes) {
            resultats.add(groupe.toString());
        }
        return resultats;
    }
}