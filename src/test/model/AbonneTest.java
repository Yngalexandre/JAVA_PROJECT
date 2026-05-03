package test.model;

import model.common.EtatCompte;
import model.common.Role;
import model.playlist.Playlist;
import model.user.Abonne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbonneTest {

    private Abonne abonne;

    @BeforeEach
    void setUp() {
        abonne = new Abonne("1", "johndoe", "password123", "John Doe");
    }

    @Test
    void testCreationEtGetters() {
        assertEquals("1", abonne.getId());
        assertEquals("johndoe", abonne.getLogin());
        assertEquals("John Doe", abonne.getNomAffichage());
        assertEquals(Role.ABONNE, abonne.getRole());
        assertEquals(EtatCompte.ACTIF, abonne.getEtatCompte());
        assertTrue(abonne.estActif());
        assertNotNull(abonne.getHistoriqueEcoute());
        assertTrue(abonne.getPlaylists().isEmpty());
    }

    @Test
    void testVerificationMotDePasse() {
        assertTrue(abonne.verifierMotDePasse("password123"));
        assertFalse(abonne.verifierMotDePasse("wrongpassword"));
    }

    @Test
    void testGestionPlaylists() {
        Playlist p = new Playlist("Détente");
        abonne.ajouterPlaylist(p);
        
        assertEquals(1, abonne.getPlaylists().size());
        
        Playlist recherche = abonne.trouverPlaylistParNom("Détente");
        assertNotNull(recherche);
        assertEquals("Détente", recherche.getNom());
        
        Playlist introuvable = abonne.trouverPlaylistParNom("Sport");
        assertNull(introuvable);
        
        abonne.supprimerPlaylist(p);
        assertTrue(abonne.getPlaylists().isEmpty());
    }
}
