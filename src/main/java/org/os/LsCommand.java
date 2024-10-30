package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommand {

    // Standard ls command: lists the contents of a directory (excluding hidden files)
    public static String listDirectory(String directoryPath) {
        return listDirectoryContentsAsString(Paths.get(directoryPath), false, false);
    }

    // ls -a command: lists all files and directories including hidden ones
    public static String listAllFiles(String directoryPath) {
        return listDirectoryContentsAsString(Paths.get(directoryPath), true, false);
    }

    // ls -r command: lists files and directories in reverse order
    public static String listFilesReversed(String directoryPath) {
        return listDirectoryContentsAsString(Paths.get(directoryPath), false, true);
    }

    // Core method to list contents based on specified flags for hidden and reverse
    private static String listDirectoryContentsAsString(Path path, boolean includeHidden, boolean reverseOrder) {
        try {
            List<Path> files = Files.list(path)
                    .filter(p -> includeHidden || !isHidden(p)) // Exclude hidden files if includeHidden is false
                    .sorted((path1, path2) -> reverseOrder
                            ? path2.getFileName().toString().compareToIgnoreCase(path1.getFileName().toString())
                            : path1.getFileName().toString().compareToIgnoreCase(path2.getFileName().toString()))
                    .collect(Collectors.toList());

            // Check if the directory is empty
            if (files.isEmpty()) {
                return "No files found in directory: " + path.toString();
            }

            return files.stream()
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.joining("\n")) + "\n";
        } catch (IOException e) {
            System.err.println("Error listing directory contents: " + e.getMessage());
            return "Error: Unable to list directory contents: " + e.getMessage();
        }
    }

    // Helper method to check if a file is hidden
    private static boolean isHidden(Path path) {
        try {
            return Files.isHidden(path);
        } catch (IOException e) {
            System.out.println("Error checking hidden status: " + e.getMessage());
            return false; // Assume not hidden if status cannot be determined
        }
    }
}
