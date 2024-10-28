package org.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class LsCommand {
    // Lists the contents of the current directory, returning as a single String
    public static String ls() {
        String currentDirectory = System.getProperty("user.dir");
        return listDirectoryContentsAsString(Paths.get(currentDirectory));
    }

    // Lists the contents of a specified directory, returning as a single String
    public static String ls(String directoryPath) {
        Path path = Paths.get(directoryPath);
        return listDirectoryContentsAsString(path);
    }

    // Helper method to list contents and return as a single String
    private static String listDirectoryContentsAsString(Path path) {
        try {
            return Files.list(path)
                    .filter(p -> {
                        try {
                            return !Files.isHidden(p); // Exclude hidden files using Files.isHidden
                        } catch (IOException e) {
                            System.out.println("Error checking hidden status: " + e.getMessage());
                            return true; // Include file if hidden status cannot be determined
                        }
                    })
                    .sorted((path1, path2) -> path1.getFileName().toString().compareToIgnoreCase(path2.getFileName().toString()))
                    .map(p -> p.getFileName().toString())  // Map to file name only
                    .collect(Collectors.joining("\n")); // Join as a single string with new lines
        } catch (IOException e) {
            System.out.println("Error listing directory contents: " + e.getMessage());
            return "";
        }
    }
}
