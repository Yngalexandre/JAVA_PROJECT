package service;

import model.media.Morceau;
import model.playlist.LimiteurEcouteVisiteur;
import model.repository.CatalogueRepository;
import model.repository.SessionRepository;
import model.repository.UtilisateurRepository;
import model.user.Abonne;

/**
 * LectureService : C'est ici qu'on gère qui a le droit d'écouter quoi.
 * Il vérifie la limite de 5 écoutes pour les visiteurs et enregistre
 * l'historique pour les abonnés connectés.
 */
public class LectureService {

    private final CatalogueRepository catalogueRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final SessionRepository sessionRepository;

    public LectureService(
            CatalogueRepository catalogueRepository,
            UtilisateurRepository utilisateurRepository,
            SessionRepository sessionRepository
    ) {
        this.catalogueRepository = catalogueRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.sessionRepository = sessionRepository;
    }

    public String lireMorceau(String morceauIdOuTitre) {
        Morceau morceau = catalogueRepository.findMorceauByIdOrTitre(morceauIdOuTitre);

        if (morceau == null) {
            throw new IllegalArgumentException("Morceau introuvable.");
        }

        if (sessionRepository.isVisiteurConnecte()) {
            gererLectureVisiteur();
        } else if (sessionRepository.isAbonneConnecte()) {
            gererLectureAbonne(morceau);
        } else if (!sessionRepository.isAdministrateurConnecte()) {
            throw new IllegalStateException("Aucune session active.");
        }

        morceau.incrementerEcoutes();

        return """
                Lecture en cours...
                Morceau : %s
                Artiste : %s
                Durée simulée : %d secondes
                """.formatted(
                morceau.getTitre(),
                morceau.getArtiste(),
                morceau.getDuree()
        );
    }

    private void gererLectureVisiteur() {
        LimiteurEcouteVisiteur limiteur = sessionRepository.getLimiteurEcouteVisiteur();

        if (!limiteur.peutEncoreEcouter()) {
            throw new IllegalStateException("Limite d'écoutes atteinte pour cette session visiteur.");
        }

        limiteur.incrementerEcoutes();
    }

    private void gererLectureAbonne(Morceau morceau) {
        Abonne abonne = sessionRepository.getAbonneConnecte();

        if (abonne == null) {
            throw new IllegalStateException("Aucun abonné connecté.");
        }

        abonne.getHistoriqueEcoute().ajouterEcoute(morceau);
    }
}