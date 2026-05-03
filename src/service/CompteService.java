package service;

import model.common.EtatCompte;
import model.repository.UtilisateurRepository;
import model.user.Abonne;

import java.util.UUID;

public class CompteService {

    private final UtilisateurRepository utilisateurRepository;

    public CompteService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Abonne creerCompteAbonne(String login, String motDePasse, String nomAffichage) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Le login est obligatoire.");
        }

        if (motDePasse == null || motDePasse.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }

        if (nomAffichage == null || nomAffichage.isBlank()) {
            throw new IllegalArgumentException("Le nom d'affichage est obligatoire.");
        }

        if (utilisateurRepository.loginExisteDeja(login)) {
            throw new IllegalArgumentException("Ce login existe déjà.");
        }

        Abonne abonne = new Abonne(
                UUID.randomUUID().toString(),
                login,
                motDePasse,
                nomAffichage
        );

        utilisateurRepository.ajouterUtilisateur(abonne);
        return abonne;
    }

    public void suspendreCompte(String login) {
        Abonne abonne = utilisateurRepository.findAbonneByLogin(login);

        if (abonne == null) {
            throw new IllegalArgumentException("Abonné introuvable.");
        }

        abonne.setEtatCompte(EtatCompte.SUSPENDU);
    }

    public void reactiverCompte(String login) {
        Abonne abonne = utilisateurRepository.findAbonneByLogin(login);

        if (abonne == null) {
            throw new IllegalArgumentException("Abonné introuvable.");
        }

        abonne.setEtatCompte(EtatCompte.ACTIF);
    }
}