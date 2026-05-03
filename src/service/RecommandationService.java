package service;

import model.common.GenreMusical;
import model.media.Morceau;
import model.playlist.Ecoute;
import model.repository.CatalogueRepository;
import model.user.Abonne;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service bonus simple :
 * recommande des morceaux en fonction du genre
 * le plus écouté par l'abonné.
 */
public class RecommandationService {

    private final CatalogueRepository catalogueRepository;

    public RecommandationService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public List<Morceau> recommanderPour(Abonne abonne, int limite) {
        if (abonne == null) {
            throw new IllegalArgumentException("Abonné invalide.");
        }

        Map<GenreMusical, Integer> compteurGenres = new HashMap<>();

        for (Ecoute ecoute : abonne.getHistoriqueEcoute().getEcoutes()) {
            GenreMusical genre = ecoute.getMorceau().getGenre();
            compteurGenres.put(genre, compteurGenres.getOrDefault(genre, 0) + 1);
        }

        if (compteurGenres.isEmpty()) {
            return catalogueRepository.getCatalogue().getMorceaux()
                    .stream()
                    .limit(limite)
                    .toList();
        }

        GenreMusical genrePrefere = compteurGenres.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(GenreMusical.AUTRE);

        List<Morceau> dejaEcoutes = new ArrayList<>();
        for (Ecoute ecoute : abonne.getHistoriqueEcoute().getEcoutes()) {
            dejaEcoutes.add(ecoute.getMorceau());
        }

        return catalogueRepository.getCatalogue().getMorceaux()
                .stream()
                .filter(m -> m.getGenre() == genrePrefere)
                .filter(m -> !dejaEcoutes.contains(m))
                .sorted(Comparator.comparing(Morceau::getTitre))
                .limit(limite)
                .toList();
    }
}