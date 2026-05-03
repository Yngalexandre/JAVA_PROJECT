package persistence;

import java.io.File;

public class FileManager {

    public void ensureDataDirectoryExists() {
        File directory = new File(DataPaths.DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public boolean fileExists(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }
}