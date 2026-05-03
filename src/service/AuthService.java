package service;

import model.common.EtatCompte;
import model.repository.SessionRepository;
import model.repository.UtilisateurRepository;
import model.user.Abonne;
import model.user.Administrateur;
import model.user.Utilisateur;
import model.user.Visiteur;

import java.util.List;
import java.util.UUID;

public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final SessionRepository sessionRepository;

    public AuthService(UtilisateurRepository utilisateurRepository, SessionRepository sessionRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.sessionRepository = sessionRepository;
    }

    public SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    public void loginAdministrateur(String login, String motDePasse) {
        Administrateur administrateur = utilisateurRepository.findAdministrateurByLogin(login);

        if (administrateur == null) {
            throw new IllegalArgumentException("Administrateur introuvable.");
        }

        if (!administrateur.verifierMotDePasse(motDePasse)) {
            throw new IllegalArgumentException("Mot de passe incorrect.");
        }

        sessionRepository.setUtilisateurConnecte(administrateur);
    }

    public void loginAbonne(String login, String motDePasse) {
        Abonne abonne = utilisateurRepository.findAbonneByLogin(login);

        if (abonne == null) {
            throw new IllegalArgumentException("Abonné introuvable.");
        }

        if (!abonne.verifierMotDePasse(motDePasse)) {
            throw new IllegalArgumentException("Mot de passe incorrect.");
        }

        if (abonne.getEtatCompte() == EtatCompte.SUSPENDU) {
            throw new IllegalStateException("Ce compte est suspendu.");
        }

        if (abonne.getEtatCompte() == EtatCompte.SUPPRIME) {
            throw new IllegalStateException("Ce compte a été supprimé.");
        }

        sessionRepository.setUtilisateurConnecte(abonne);
    }

    public void loginVisiteur() {
        sessionRepository.setUtilisateurConnecte(new Visiteur());
    }

    public void logout() {
        sessionRepository.clearSession();
    }

    public void registerAbonne(String login, String motDePasse, String nomAffichage) {
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
    }

    public Utilisateur getUtilisateurConnecte() {
        return sessionRepository.getUtilisateurConnecte();
    }

    public List<String> getHistoriqueUtilisateurConnecte() {
        Abonne abonne = sessionRepository.getAbonneConnecte();

        if (abonne == null) {
            throw new IllegalStateException("Aucun abonné connecté.");
        }

        return abonne.getHistoriqueEcoute().getResumeEcoutes();
    }
}