package model.stats;

import model.common.GenreMusical;
import model.media.Morceau;
import model.repository.CatalogueRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour le calcul de statistiques avancées sur le catalogue.
 */
public class StatistiquesService {

    private final CatalogueRepository catalogueRepository;

    public StatistiquesService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * Retourne les N morceaux les plus écoutés.
     */
    public List<Morceau> getTopMorceaux(int n) {
        return catalogueRepository.getCatalogue().getMorceaux().stream()
                .sorted(Comparator.comparingInt(Morceau::getNombreEcoutes).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Calcule la répartition des morceaux par genre musical.
     */
    public Map<GenreMusical, Long> getRepartitionParGenre() {
        return catalogueRepository.getCatalogue().getMorceaux().stream()
                .collect(Collectors.groupingBy(Morceau::getGenre, Collectors.counting()));
    }

    /**
     * Identifie l'artiste ou le groupe le plus écouté (somme des écoutes de ses morceaux).
     */
    public String getArtisteLePlusPopulaire() {
        Map<String, Integer> ecoutesParArtiste = new HashMap<>();

        for (Morceau morceau : catalogueRepository.getCatalogue().getMorceaux()) {
            String artiste = morceau.getArtiste();
            ecoutesParArtiste.put(artiste, ecoutesParArtiste.getOrDefault(artiste, 0) + morceau.getNombreEcoutes());
        }

        return ecoutesParArtiste.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucun artiste");
    }
}
