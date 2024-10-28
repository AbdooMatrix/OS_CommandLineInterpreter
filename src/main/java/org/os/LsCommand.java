package org.os;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommand {
    public void ls() {
        // Get the current working directory
        String currentDirectory = System.getProperty("user.dir");

        // Use Paths to get the path of the current directory
        Path path = Paths.get(currentDirectory);

        try {
            // List the files and directories in the current directory
            List<Path> files = Files.list(path)
                    .sorted((path1, path2) -> path1.getFileName().toString().compareToIgnoreCase(path2.getFileName().toString()))
                    .collect(Collectors.toList());

            // Print the sorted file and directory names
            files.forEach(file -> System.out.println(file.getFileName()));
        } catch (IOException e) {
            System.out.println("Error listing directory contents: " + e.getMessage());
        }
    }
}