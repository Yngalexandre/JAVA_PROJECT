package util;

import model.common.GenreMusical;
import model.media.*;

public class SampleDataFactory {

    public static Catalogue createSampleCatalogue() {
        Catalogue catalogue = new Catalogue();

        Artiste stromae = new Artiste("art1", "Stromae");
        Album racine = new Album("alb1", "Racine Carrée", "Stromae");

        Morceau papaoutai = new Morceau("m1", "Papaoutai", 230, "Stromae", GenreMusical.POP);
        Morceau formidable = new Morceau("m2", "Formidable", 220, "Stromae", GenreMusical.POP);

        racine.ajouterMorceau(papaoutai);
        racine.ajouterMorceau(formidable);

        stromae.ajouterAlbum(racine);
        stromae.ajouterMorceau(papaoutai);
        stromae.ajouterMorceau(formidable);

        catalogue.ajouterArtiste(stromae);
        catalogue.ajouterAlbum(racine);
        catalogue.ajouterMorceau(papaoutai);
        catalogue.ajouterMorceau(formidable);

        return catalogue;
    }
}