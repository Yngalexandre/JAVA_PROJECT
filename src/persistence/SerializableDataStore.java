package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableDataStore {

    public void saveObject(String filePath, Object object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.err.println("ERREUR CRITIQUE : Échec de la sauvegarde (" + filePath + ")");
            System.err.println("Détail : " + e.getMessage());
            if (e.getCause() != null) System.err.println("Cause : " + e.getCause());
        }
    }

    public Object loadObject(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ATTENTION : Échec du chargement (" + filePath + ")");
            System.err.println("Détail : " + e.getMessage());
            return null; // Retourner null permet de créer un objet par défaut dans le DataLoader
        }
    }
}