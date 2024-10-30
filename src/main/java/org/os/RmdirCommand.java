package org.os;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RmdirCommand {
    public String rmdir(String dirPathStr) {
        if (dirPathStr == null || dirPathStr.trim().isEmpty()) {
            return "Error: 'rmdir' command requires a directory name or path.\n";
        }

        Path dirPath = Paths.get(dirPathStr);

        // Check if the specified directory exists
        if (!Files.exists(dirPath)) {
            return "Error: Directory does not exist: " + dirPathStr + "\n";
        }

        // Verify that the specified path is a directory
        if (!Files.isDirectory(dirPath)) {
            return "Error: " + dirPathStr + " is not a directory.\n";
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            // Check if the directory is empty
            if (stream.iterator().hasNext()) {
                return "Error: " + dirPathStr + " is not empty.\n";
            } else {
                // Delete the directory if it's empty
                Files.delete(dirPath);
                return "Removed directory: " + dirPathStr + "\n";
            }
        } catch (IOException e) {
            return "Error: An IOException occurred while removing the directory: " + dirPathStr + " - " + e.getMessage() + "\n";
        }
    }
}
