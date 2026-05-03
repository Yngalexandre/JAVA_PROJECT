package test.model;

import model.common.GenreMusical;
import model.media.Album;
import model.media.Morceau;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumTest {

    @Test
    void testCreationAlbum() {
        Album album = new Album("a-1", "Mon Album", "Artiste Sympa");
        
        assertEquals("a-1", album.getId());
        assertEquals("Mon Album", album.getTitre());
        assertEquals("Artiste Sympa", album.getArtiste());
        assertTrue(album.getMorceaux().isEmpty());
    }

    @Test
    void testAjouterMorceau() {
        Album album = new Album("a-1", "Mon Album", "Artiste Sympa");
        Morceau morceau = new Morceau("m-1", "Titre", 180, "Artiste Sympa", GenreMusical.JAZZ);
        
        album.ajouterMorceau(morceau);
        
        assertEquals(1, album.getMorceaux().size());
        assertEquals(morceau, album.getMorceaux().get(0));
    }
}
