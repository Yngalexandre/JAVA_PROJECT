package service;

import model.media.Catalogue;
import model.repository.CatalogueRepository;
import model.repository.UtilisateurRepository;
import model.user.Utilisateur;
import persistence.DataPaths;
import persistence.FileManager;
import persistence.SerializableDataStore;

import java.util.List;

public class PersistanceService {

    private final CatalogueRepository catalogueRepository;
    private final UtilisateurRepository utilisateurRepository;

    private final FileManager fileManager;
    private final SerializableDataStore serializableDataStore;

    public PersistanceService(CatalogueRepository catalogueRepository, UtilisateurRepository utilisateurRepository) {
        this.catalogueRepository = catalogueRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.fileManager = new FileManager();
        this.serializableDataStore = new SerializableDataStore();
    }

    public void saveAll() {
        fileManager.ensureDataDirectoryExists();

        serializableDataStore.saveObject(
                DataPaths.CATALOGUE_FILE,
                catalogueRepository.getCatalogue()
        );

        serializableDataStore.saveObject(
                DataPaths.UTILISATEURS_FILE,
                utilisateurRepository.getUtilisateurs()
        );
    }

    public Catalogue getCatalogueSnapshot() {
        return catalogueRepository.getCatalogue();
    }

    public List<Utilisateur> getUtilisateursSnapshot() {
        return utilisateurRepository.getUtilisateurs();
    }
}