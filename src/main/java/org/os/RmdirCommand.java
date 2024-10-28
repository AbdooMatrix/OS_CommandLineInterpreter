package org.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RmdirCommand {
    public String rmdir(String dirPathStr) {
        Path dirPath = Paths.get(dirPathStr); 

        // Check if the specified directory exists
        if (!Files.exists(dirPath)) {
            return "Directory does not exist: " + dirPathStr;
        }

        // Verify that the specified path is a directory
        if (!Files.isDirectory(dirPath)) {
            return dirPathStr + " is not a directory.";
        }

        try {
            // Check if the directory is empty
            if (Files.newDirectoryStream(dirPath).iterator().hasNext()) {
                return dirPathStr + " is not empty.";
            } else {
                // Delete the directory if it's empty
                Files.delete(dirPath);
                return "Removed directory: " + dirPathStr;
            }
        } catch (IOException e) {
            return "Error removing directory: " + e.getMessage();
        }
    }
}
