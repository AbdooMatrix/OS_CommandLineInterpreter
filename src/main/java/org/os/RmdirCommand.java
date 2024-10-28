package org.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class RmdirCommand {
    public void rmdir() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the directory name
        System.out.print("Enter the directory you want to remove: ");
        String dirName = scanner.nextLine();
        // Get the current working directory
        String currentDirectory = System.getProperty("user.dir");
        Path dirPath = Paths.get(currentDirectory, dirName);
        // Check if the specified directory exists
        if (!Files.exists(dirPath)) {
            System.out.println("Directory does not exist: " + dirName);
            return;
        }
        // Verify that the specified path is a directory (not a file)
        if (!Files.isDirectory(dirPath)) {
            System.out.println(dirName + " is not a directory.");
            return;
        }

        try {
            // Check if the directory is empty
            if (Files.newDirectoryStream(dirPath).iterator().hasNext()) {
                System.out.println(dirName + " is not empty.");
            } else {
                // Delete the directory if it's empty
                Files.delete(dirPath);
                System.out.println("Removed directory: " + dirName);
            }
        } catch (IOException e) {
            System.out.println("Error removing directory: " + e.getMessage());
        }
    }
}
