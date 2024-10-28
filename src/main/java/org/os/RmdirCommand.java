package org.os;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RmdirCommand {
    public void rmdir(String dirName) {
        String currentDirectory = System.getProperty("user.dir");
        // Combine the current directory path with the specified directory name
        Path dirPath = Paths.get(currentDirectory, dirName);
        // Check if the directory exists
        if (!Files.exists(dirPath)) {
            System.out.println("Directory does not exist'" + dirName);
            return;
        }

        // Check if the path is actually a directory
        if (!Files.isDirectory(dirPath)) {
            System.out.println(dirName + ": IS Not a directory");
            return;
        }
        // Check if the directory is empty
        try {
            if (Files.list(dirPath).findAny().isPresent()) {
                System.out.println( dirName + "': Directory not empty");
            } else {
                Files.delete(dirPath);
                System.out.println("Removed directory: " + dirName);
            }
        } catch (IOException e) {
            System.out.println("Error removing directory: " + e.getMessage());
        }
    }
}
