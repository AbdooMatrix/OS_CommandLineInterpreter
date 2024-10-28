package org.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommand {
    // Lists the contents of the current directory, showing only names
    public static List<String> ls() {
        String currentDirectory = System.getProperty("user.dir");
        return listDirectoryContents(Paths.get(currentDirectory));
    }

    // Lists the contents of a specified directory, showing only names
    public static List<String> ls(String directoryPath) {
        Path path = Paths.get(directoryPath);
        return listDirectoryContents(path);
    }

    // Helper method to list contents, returning only names
    private static List<String> listDirectoryContents(Path path) {
        try {
            return Files.list(path)
                    .sorted((path1, path2) -> path1.getFileName().toString().compareToIgnoreCase(path2.getFileName().toString()))
                    .map(p -> p.getFileName().toString())  // Map to file name only
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error listing directory contents: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
