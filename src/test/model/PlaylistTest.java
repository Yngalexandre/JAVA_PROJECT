package test.model;

import model.common.GenreMusical;
import model.media.Morceau;
import model.playlist.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    private Playlist playlist;
    private Morceau morceau1;
    private Morceau morceau2;

    @BeforeEach
    void setUp() {
        playlist = new Playlist("Ma Super Playlist");
        morceau1 = new Morceau("1", "Titre 1", 180, "Artiste 1", GenreMusical.ROCK);
        morceau2 = new Morceau("2", "Titre 2", 200, "Artiste 2", GenreMusical.POP);
    }

    @Test
    void testCreationPlaylist() {
        assertEquals("Ma Super Playlist", playlist.getNom());
        assertTrue(playlist.estVide());
        assertEquals(0, playlist.getMorceaux().size());
    }

    @Test
    void testAjouterMorceau_Normal() {
        playlist.ajouterMorceau(morceau1);
        
        assertFalse(playlist.estVide());
        assertEquals(1, playlist.getMorceaux().size());
        assertTrue(playlist.contientMorceau(morceau1));
    }

    @Test
    void testAjouterMorceau_Doublon() {
        playlist.ajouterMorceau(morceau1);
        playlist.ajouterMorceau(morceau1); // Tentative d'ajout en double
        
        assertEquals(1, playlist.getMorceaux().size(), "La playlist ne devrait pas ajouter un morceau en double.");
    }

    @Test
    void testRetirerMorceau() {
        playlist.ajouterMorceau(morceau1);
        playlist.ajouterMorceau(morceau2);
        
        playlist.retirerMorceau(morceau1);
        
        assertEquals(1, playlist.getMorceaux().size());
        assertFalse(playlist.contientMorceau(morceau1));
        assertTrue(playlist.contientMorceau(morceau2));
    }

    @Test
    void testRenommer() {
        playlist.renommer("Nouveau Nom");
        assertEquals("Nouveau Nom", playlist.getNom());
    }
}
