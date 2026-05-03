package test.model;

import model.common.GenreMusical;
import model.media.Morceau;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MorceauTest {

    @Test
    void testCreationEtGetters() {
        Morceau morceau = new Morceau("id-123", "Super Titre", 210, "Super Artiste", GenreMusical.ROCK);

        assertEquals("id-123", morceau.getId());
        assertEquals("Super Titre", morceau.getTitre());
        assertEquals(210, morceau.getDuree());
        assertEquals("Super Artiste", morceau.getArtiste());
        assertEquals(GenreMusical.ROCK, morceau.getGenre());
        assertEquals(0, morceau.getNombreEcoutes());
    }

    @Test
    void testIncrementerEcoutes() {
        Morceau morceau = new Morceau("id-123", "Super Titre", 210, "Super Artiste", GenreMusical.ROCK);
        
        morceau.incrementerEcoutes();
        assertEquals(1, morceau.getNombreEcoutes());
        
        morceau.incrementerEcoutes();
        assertEquals(2, morceau.getNombreEcoutes());
    }
}
