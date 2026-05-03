package persistence;

import model.media.Catalogue;
import model.user.Administrateur;
import model.user.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DataLoader : S'occupe de réveiller l'application en chargeant les fichiers .ser.
 * Si les fichiers n'existent pas (premier lancement), il crée les comptes par défaut 
 * (admin, alice, bob) pour que vous puissiez tester immédiatement.
 */
public class DataLoader {

    private final FileManager fileManager;
    private final SerializableDataStore serializableDataStore;

    public DataLoader() {
        this.fileManager = new FileManager();
        this.serializableDataStore = new SerializableDataStore();
    }

    public Catalogue loadCatalogue() {
        fileManager.ensureDataDirectoryExists();

        if (fileManager.fileExists(DataPaths.CATALOGUE_FILE)) {
            try {
                Object loaded = serializableDataStore.loadObject(DataPaths.CATALOGUE_FILE);
                if (loaded instanceof Catalogue catalogue) {
                    return catalogue;
                }
            } catch (Exception e) {
                System.err.println("Chargement du catalogue impossible, création d'un catalogue vide.");
            }
        }

        return new Catalogue();
    }

    @SuppressWarnings("unchecked")
    public List<Utilisateur> loadUtilisateurs() {
        fileManager.ensureDataDirectoryExists();

        if (fileManager.fileExists(DataPaths.UTILISATEURS_FILE)) {
            try {
                Object loaded = serializableDataStore.loadObject(DataPaths.UTILISATEURS_FILE);
                if (loaded instanceof List<?>) {
                    return (List<Utilisateur>) loaded;
                }
            } catch (Exception e) {
                System.err.println("Chargement des utilisateurs impossible, création des utilisateurs par défaut.");
            }
        }

        return createDefaultUsers();
    }

    private List<Utilisateur> createDefaultUsers() {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        // Administrateur
        utilisateurs.add(new Administrateur(
                UUID.randomUUID().toString(),
                "admin",
                "admin",
                "Administrateur principal"
        ));

        // Abonnés de démo
        utilisateurs.add(new model.user.Abonne(
                UUID.randomUUID().toString(),
                "alice",
                "alice123",
                "Alice Martin"
        ));

        utilisateurs.add(new model.user.Abonne(
                UUID.randomUUID().toString(),
                "bob",
                "bob123",
                "Bob Dupont"
        ));

        return utilisateurs;
    }
}